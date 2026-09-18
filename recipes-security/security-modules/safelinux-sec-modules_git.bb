SUMMARY = "Security Drivers Kernel Modules"
DESCRIPTION = "Security drivers, used to communicate with TrustZone"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "${PATH_TO_REPO}/vendor/qcom/opensource/safelinux-sec-modules/.git;protocol=${PROTO};destsuffix=vendor/qcom/opensource/safelinux-sec-modules;usehead=1"
SRC_URI:append = " file://fix-kernel7-api.py"
SRC_URI:append = " file://0001-scm-user-intf-sec-find-scm-by-compatible.patch"
SRC_URI:append = " file://0002-scm-user-intf-sec-handle-legacy-smcinvoke.patch"
SRC_URI:append = " file://0003-scm-user-intf-sec-sync-dmabuf-for-smcinvoke.patch"
SRC_URI:append = " file://0004-qtee-shmbridge-build-on-kernel-7.patch"

SRCREV = "${AUTOREV}"

S = "${UNPACKDIR}/vendor/qcom/opensource/safelinux-sec-modules/security-modules"

TECHPACK_MODULES = "${@bb.utils.contains_any('PREFERRED_PROVIDER_virtual/kernel', 'linux-qcom-custom linux-qcom-custom-rt', '', 'tz_log.ko qtee_shmbridge.ko', d)}"
TECHPACK_MODULES:append = " scm_user_intf_sec.ko tz_ffi.ko dice_vm.ko"
inherit qti-techpack

EXTRA_OEMAKE:append = " SECURITY_MODULES_SYSROOT_INC=${STAGING_DIR_TARGET}/usr/include"
EXTRA_OEMAKE:append = " KCFLAGS=-Wno-error"

python do_apply_kernel7_fixes () {
    import subprocess, os
    s = d.getVar('S')
    unpackdir = d.getVar('UNPACKDIR')
    fixscript = os.path.join(unpackdir, 'fix-kernel7-api.py')
    drvsrc = os.path.join(s, 'drivers')
    driver_names = ['dice_vm', 'tz_ffi', 'scm_user_intf_sec', 'smcinvoke', 'qtee_shmbridge', 'tz_log']
    paths = [os.path.join(drvsrc, n + '.c') for n in driver_names if os.path.isfile(os.path.join(drvsrc, n + '.c'))]
    if paths and os.path.isfile(fixscript):
        subprocess.call(['python3', fixscript] + paths)
}

addtask do_apply_kernel7_fixes after do_unpack before do_patch

do_install:append() {
    install -d ${D}${includedir}/linux
    install -d ${D}${includedir}/uapi/misc
    install -d ${D}${includedir}/safelinux-sec-modules

    install -m 0755 ${S}/modules-load/scm_user_intf_sec.conf -D ${D}${sysconfdir}/modules-load.d/scm_user_intf_sec.conf
    install -m 0755 ${S}/modules-load/tz_ffi.conf -D ${D}${sysconfdir}/modules-load.d/tz_ffi.conf
    install -m 0755 ${S}/modules-load/dice_vm.conf -D ${D}${sysconfdir}/modules-load.d/dice_vm.conf
    install -m 0644 ${S}/include/uapi/misc/dice_vm.h ${D}${includedir}/uapi/misc

    if ${@bb.utils.contains_any('PREFERRED_PROVIDER_virtual/kernel', 'linux-qcom-custom linux-qcom-custom-rt', 'false', 'true', d)}; then
        install -m 0755 ${S}/modules-load/qtee_shmbridge.conf -D ${D}${sysconfdir}/modules-load.d/qtee_shmbridge.conf
        install -m 0644 ${S}/Module.symvers ${D}${includedir}/safelinux-sec-modules
    fi

    if ${@bb.utils.contains('MACHINE_FEATURES', 'qti-gunyah', 'false', 'true', d)}; then
        install -m 0755 ${S}/modules-load/tz_log.conf -D ${D}${sysconfdir}/modules-load.d/tz_log.conf
    fi

}

RPROVIDES:${PN} += "kernel-module-tz-log-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-qtee-shmbridge-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-scm-user-intf-sec-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-tz-ffi-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-dice-vm-${KERNEL_VERSION}"

RPROVIDES:${PN}:remove = "${@bb.utils.contains_any('PREFERRED_PROVIDER_virtual/kernel', 'linux-qcom-custom linux-qcom-custom-rt', 'kernel-module-tz-log-${KERNEL_VERSION}', '', d)}"

FILES:${PN} += "${sysconfdir}/modules-load.d/*"
FILES:${PN} += "${nonarch_base_libdir}/modules/*"
