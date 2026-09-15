#!/usr/bin/env python3A
# Copyright (c) Qualcomm Technologies, Inc. and/or its subsidiaries.
# SPDX-License-Identifier: GPL-2.0-only

"""Fix safelinux-cfg-modules drivers for kernel 7.1 API changes (AU.0.3.0)."""
import re, sys, os, glob

src = sys.argv[1]  # S directory

def patch(path, fn):
    if not os.path.exists(path):
        return
    txt = open(path).read()
    new = fn(txt)
    if new != txt:
        open(path, 'w').write(new)
        print(f"Fixed: {os.path.basename(path)}")

# 1. Fix __assign_str 2-arg -> 1-arg and MODULE_IMPORT_NS quoting
for f in glob.glob(f'{src}/**/*.h', recursive=True) + glob.glob(f'{src}/**/*.c', recursive=True):
    txt = open(f).read()
    new = re.sub(r'__assign_str\(([^,)]*),\s*[^)]*\)', r'__assign_str(\1)', txt)
    new = new.replace('MODULE_IMPORT_NS(DMA_BUF)', 'MODULE_IMPORT_NS("DMA_BUF")')
    if new != txt:
        open(f, 'w').write(new)

# 2. apps_pinctrl.c: of_gpio.h removed
patch(f'{src}/drivers/apps_pinctrl.c',
    lambda t: t.replace('#include <linux/of_gpio.h>', '#include <linux/gpio/consumer.h>'))

# 3. kiumd_common.c: API rename + stub via #define (after kernel headers)
def fix_kiumd_common(txt):
    txt = txt.replace('qcom_scm_kgsl_set_smmu_aperture', 'qcom_scm_set_gpu_smmu_aperture')
    # Add stub after includes are done (after last #include line)
    # Skip if stub is already present (e.g. already added by a patch)
    if '__kiumd_set_vmid_stub' in txt:
        return txt
    stub = '\nstatic inline void __kiumd_set_vmid_stub(void *a, int b) {}\n#define qcom_scm_set_vmid_by_word(a,b) __kiumd_set_vmid_stub(a,b)\n'
    # Insert after last #include
    m = list(re.finditer(r'^#include\s+[<"][^>"]+[>"]', txt, re.MULTILINE))
    if m:
        pos = m[-1].end()
        txt = txt[:pos] + stub + txt[pos:]
    return txt

patch(f'{src}/drivers/kiumd_common.c', fix_kiumd_common)

# 4. scm_user_intf.c: comment out removed API calls
def fix_scm(txt):
    # Rename multi_kgsl_init_regs -> gpu_init_regs (1 arg)
    txt = re.sub(r'qcom_scm_multi_kgsl_init_regs\(([^,)]*),\s*[^)]*\)',
                 r'qcom_scm_gpu_init_regs(\1)', txt)
    # kgsl_set_smmu_gos_aperture(a,b) -> set_gpu_smmu_aperture(a) drop 2nd arg
    txt = re.sub(r'qcom_scm_kgsl_set_smmu_gos_aperture\(([^,)]*),\s*[^)]*\)',
                 r'qcom_scm_set_gpu_smmu_aperture(\1)', txt)
    # Comment out removed function calls that span multiple lines
    # qcom_scm_ddrbw_profiler
    txt = re.sub(
        r'(scm_data\.ret\s*=\s*qcom_scm_ddrbw_profiler[^;]*;)',
        r'scm_data.ret = -ENOSYS; /* kernel 7.1: qcom_scm_ddrbw_profiler removed */',
        txt, flags=re.DOTALL)
    # qcom_scm_get_secure_state
    txt = re.sub(
        r'(scm_data\.ret\s*=\s*qcom_scm_get_secure_state[^;]*;)',
        r'scm_data.ret = -ENOSYS; /* kernel 7.1: removed */',
        txt, flags=re.DOTALL)
    # qcom_scm_she_op (multiline call)
    txt = re.sub(
        r'(scm_data\.ret\s*=\s*qcom_scm_she_op[^;]*;)',
        r'scm_data.ret = -ENOSYS; /* kernel 7.1: removed */',
        txt, flags=re.DOTALL)
    return txt

patch(f'{src}/drivers/scm_user_intf.c', fix_scm)

# 5. scm_user_intf.h: avoid CAMNOC redefinition
# The issue: kernel qcom_scm.h defines QCOM_SCM_CAMERA_UPDATE_CAMNOC_QOS 0xA
# Our header also defines it 0x0A — same value, different form. Just undef ours.
def fix_scm_h(txt):
    return txt.replace(
        '#define QCOM_SCM_CAMERA_UPDATE_CAMNOC_QOS       0x0A',
        '/* kernel 7.1: defined in qcom_scm.h — use undef to avoid conflict */\n'
        '#undef QCOM_SCM_CAMERA_UPDATE_CAMNOC_QOS\n'
        '#define QCOM_SCM_CAMERA_UPDATE_CAMNOC_QOS       0x0A\n'
        '#define AVOID_CAMNOC_REDEF 1')

patch(f'{src}/include/uapi/misc/scm_user_intf.h', fix_scm_h)

# 6. qcom_dload_mode.c: comment out qcom_scm_get_dump_mode_caps call
def fix_dload(txt):
    txt = re.sub(
        r'(return\s+qcom_scm_get_dump_mode_caps[^;]*;)',
        r'return -ENOSYS; /* kernel 7.1: qcom_scm_get_dump_mode_caps removed */',
        txt, flags=re.DOTALL)
    # Remove now-unused dload_cookie variable declaration in qcom_dload_query_caps
    txt = re.sub(r'\n\tu32 dload_cookie;\n', '\n', txt)
    return txt

patch(f'{src}/drivers/qcom_dload_mode.c', fix_dload)

# 7. qcom_io_mem.c: gpu_parent_remove int->void
def fix_io_mem(txt):
    txt = re.sub(r'static int (gpu_parent_remove\(struct platform_device \*pdev\))',
                 r'static void \1', txt)
    txt = re.sub(r'(static void gpu_parent_remove[^{]+\{[^}]*)\n\treturn 0;\n(\})',
                 r'\1\n\2', txt, flags=re.DOTALL)
    return txt

patch(f'{src}/drivers/qcom_io_mem.c', fix_io_mem)

# 8. scm_user_intf.c: QCOM_SCM_CAMERA_UPDATE_CAMNOC_QOS is defined in
# qcom_scm_addon.c (not a header), so it's not available via #include.
# Add a local #define after the kernel scm.h inclusion.
def fix_scm_camnoc(txt):
    # Add define right after the <qcom_scm.h> include if not already defined
    define = '#ifndef QCOM_SCM_CAMERA_UPDATE_CAMNOC_QOS\n#define QCOM_SCM_CAMERA_UPDATE_CAMNOC_QOS\t0xA\n#endif\n'
    return re.sub(
        r'(#include <qcom_scm\.h>\n)',
        r'\1' + define,
        txt)

patch(f'{src}/drivers/scm_user_intf.c', fix_scm_camnoc)
