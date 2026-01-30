SUMMARY = "Security Drivers Kernel Modules"
DESCRIPTION = "Security drivers, used to communicate with TrustZone"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"



SRCPROJECT  = "git://${OSS_REPO}/clo/le/platform/vendor/qcom-opensource/safelinux-sec-modules.git"
SRCBRANCH  = "sec-safe-kernel.lnx.1.0.r10-rel"
SRCREV  = "47de9d911c9a6cc22322141509cc07a4137fd96f"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/safelinux-sec-modules;"


S = "${WORKDIR}/vendor/qcom/opensource/safelinux-sec-modules/security-modules"

TECHPACK_MODULES = "${@bb.utils.contains_any('PREFERRED_PROVIDER_virtual/kernel', 'linux-qcom-custom linux-qcom-custom-rt', '', 'tz_log.ko qtee_shmbridge.ko qcom_scm_oot.ko', d)}"
TECHPACK_MODULES:append = " scm_user_intf_sec.ko tz_ffi.ko"
inherit qti-techpack

EXTRA_OEMAKE:append = " SECURITY_MODULES_SYSROOT_INC=${STAGING_DIR_TARGET}/usr/include"

do_install:append() {
    install -d ${D}${includedir}/linux
    install -d ${D}${includedir}/safelinux-sec-modules

    install -m 0755 ${S}/modules-load/scm_user_intf_sec.conf -D ${D}${sysconfdir}/modules-load.d/scm_user_intf_sec.conf
    install -m 0755 ${S}/modules-load/tz_ffi.conf -D ${D}${sysconfdir}/modules-load.d/tz_ffi.conf

    if ${@bb.utils.contains_any('PREFERRED_PROVIDER_virtual/kernel', 'linux-qcom-custom linux-qcom-custom-rt', 'false', 'true', d)}; then
        install -m 0755 ${S}/modules-load/qtee_shmbridge.conf -D ${D}${sysconfdir}/modules-load.d/qtee_shmbridge.conf
        install -m 0755 ${S}/modules-load/qcom_scm_oot.conf -D ${D}${sysconfdir}/modules-load.d/qcom_scm_oot.conf
        install -m 0644 ${S}/Module.symvers ${D}${includedir}/safelinux-sec-modules
    fi

    if ${@bb.utils.contains('MACHINE_FEATURES', 'qti-gunyah', 'false', 'true', d)}; then
        install -m 0755 ${S}/modules-load/tz_log.conf -D ${D}${sysconfdir}/modules-load.d/tz_log.conf
    fi

}

RPROVIDES:${PN} += "kernel-module-tz-log-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-qtee-shmbridge-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-qcom-scm-oot-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-scm-user-intf-sec-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-tz-ffi-${KERNEL_VERSION}"

RPROVIDES:${PN}:remove = "${@bb.utils.contains_any('PREFERRED_PROVIDER_virtual/kernel', 'linux-qcom-custom linux-qcom-custom-rt', 'kernel-module-tz-log-${KERNEL_VERSION}', '', d)}"

FILES:${PN} += "${sysconfdir}/modules-load.d/*"
FILES:${PN} += "${nonarch_base_libdir}/modules/*"
