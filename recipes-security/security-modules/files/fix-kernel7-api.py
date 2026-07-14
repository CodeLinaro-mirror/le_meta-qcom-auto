#!/usr/bin/env python3
# Copyright (c) Qualcomm Technologies, Inc. and/or its subsidiaries.
# SPDX-License-Identifier: GPL-2.0-only

"""Fix safelinux-sec-modules for kernel 7.0 API changes (AU.0.3.0)."""
import sys, re, os

if len(sys.argv) < 2:
    sys.exit(0)

for path in sys.argv[1:]:
    if not os.path.isfile(path):
        continue
    try:
        txt = open(path).read()
    except Exception as e:
        print(f"Warning: could not read {path}: {e}", file=sys.stderr)
        continue

    basename = os.path.basename(path)

    # Kernel 7.0: platform_driver.remove return type int -> void
    txt = re.sub(
        r'(^static int )([a-z_]+_remove\(struct platform_device)',
        r'static void \2', txt, flags=re.MULTILINE
    )
    # Remove "return 0;" in void *_remove functions
    lines = txt.splitlines(keepends=True)
    in_void_remove = entered_body = False
    brace_depth = 0
    result = []
    for line in lines:
        if 'static void ' in line and '_remove(struct platform_device' in line:
            in_void_remove = True; entered_body = False; brace_depth = 0
        if in_void_remove:
            opens = line.count('{'); closes = line.count('}')
            if opens > 0: entered_body = True
            brace_depth += opens - closes
            if entered_body and brace_depth <= 0:
                in_void_remove = entered_body = False
            elif line.strip() == 'return 0;':
                continue
        result.append(line)
    txt = ''.join(result)

    if basename == 'dice_vm.c':
        txt = txt.replace('.llseek = no_llseek', '.llseek = noop_llseek')

    if basename in ('scm_user_intf_sec.c', 'tz_ffi.c'):
        txt = re.sub(r'#include\s*<errno\.h>\n?', '', txt)

    if basename == 'scm_user_intf_sec.c':
        txt = re.sub(r'#include\s*<qcom_scm\.h>\n?', '', txt)
        txt = txt.replace('MAX_QCOM_SCM_RETS', '3')
        txt = txt.replace('MAX_QCOM_SCM_ARGS', '10')
        txt = txt.replace('MODULE_IMPORT_NS(DMA_BUF)', 'MODULE_IMPORT_NS("DMA_BUF")')
        # API renames
        txt = txt.replace('qcom_scm_invoke_smc', 'qcom_scm_qtee_invoke_smc')
        txt = txt.replace('qcom_scm_invoke_callback_response', 'qcom_scm_qtee_callback_response')
        # Fix 7->6 arg call site
        txt = txt.replace(
            'qcom_scm_qtee_invoke_smc(in_buf, in_buf_size, out_buf, out_buf_size, (int32_t *)&res.result[1], &res.result[0], (unsigned int *)&res.result[2])',
            'qcom_scm_qtee_invoke_smc(in_buf, in_buf_size, out_buf, out_buf_size, &res.result[1], &res.result[0])'
        )
        # Fix 5->4 arg call site
        txt = txt.replace(
            'qcom_scm_qtee_callback_response(out_buf, out_buf_size, (int32_t *)&res.result[1], &res.result[0], (unsigned int *)&res.result[2])',
            'qcom_scm_qtee_callback_response(out_buf, out_buf_size, &res.result[1], &res.result[0])'
        )

    if basename in ('scm_user_intf_sec.c', 'tz_ffi.c'):
        txt = re.sub(r'\bret\s*=\s*qcom_tzmem_register\s*\([^;]+\);',
                     'ret = 0; /* qcom_tzmem_register: removed in kernel 7.0 */', txt, flags=re.DOTALL)
        txt = re.sub(r'\bqcom_tzmem_deregister\s*\([^;]+\);',
                     '/* qcom_tzmem_deregister: removed in kernel 7.0 */', txt, flags=re.DOTALL)

    if basename == 'tz_ffi.c':
        txt = txt.replace('qcom_scm_tz_safety_enable_ffi', 'smc_tz_safety_enable_ffi')

    if basename == 'tz_ffi.c':
        # Fix 3->4 arg call: missing 'dev' as first arg
        txt = txt.replace(
            'smc_tz_safety_enable_ffi(mem_info_paddr, size, crc8);',
            'smc_tz_safety_enable_ffi(dev, mem_info_paddr, size, crc8);'
        )

    try:
        open(path, 'w').write(txt)
    except Exception as e:
        print(f"Warning: could not write {path}: {e}", file=sys.stderr)
# (end of safelinux fixes)
