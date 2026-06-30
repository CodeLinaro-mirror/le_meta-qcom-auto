SUMMARY = "Audiolite Drivers Kernel Modules"
DESCRIPTION = "This is a test driver to show example communication between GVM / PVM/ DSPs"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

DEPENDS += "virtual/kernel"

SRC_URI = "${CLO_LA_GIT}/platform/vendor/qcom-opensource/audiolite.git;branch=audiolite.lnx.1.0.r38-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/audiolite"

SRCREV = "36c4e3ce1bded8473bc7b5fcf1b3e93924906f6d"

S = "${WORKDIR}/vendor/qcom/opensource/audiolite/test_drivers/pvm"

TECHPACK_MODULES = "\
    ipcc_shmem_test_module.ko \
"
inherit qti-techpack

do_install:append() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -d ${D}${libdir}/modules-load.d/
    install -m 0755 ${S}/audiolite-dlkm.conf -D ${D}${libdir}/modules-load.d/audiolite-dlkm.conf
    install -m 0644 ${S}/msm-audio-node.rules -D ${D}${sysconfdir}/udev/rules.d/msm-audio-node.rules
}

FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/*"
FILES:${PN} += "${libdir}/modules-load.d/*"
FILES:${PN} += "${sysconfdir}/udev/rules.d/*"

RPROVIDES:${PN} += "${@'kernel-module-ipcc-shmem-test-module-${KERNEL_VERSION}'.replace('_', '-')}"
