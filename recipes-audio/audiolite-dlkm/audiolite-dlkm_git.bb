SUMMARY = "Audiolite Drivers Kernel Modules"
DESCRIPTION = "This is a test driver to show example communication between GVM / PVM/ DSPs"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

DEPENDS += "virtual/kernel"



SRCPROJECT  = "git://${OSS_REPO}/clo/la/platform/vendor/qcom-opensource/audiolite.git"
SRCBRANCH  = "audiolite.lnx.1.0.r30-rel"
SRCREV  = "1c963f721d336bcb102a562bcac0ede922f2f35d"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/audiolite;"


S = "${WORKDIR}/vendor/qcom/opensource/audiolite/test_drivers/pvm"

TECHPACK_MODULES = "\
    ipcc_shmem_test_module.ko \
"
inherit qti-techpack

do_install:append() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -d ${D}${libdir}/modules-load.d/
    install -m 0755 ${WORKDIR}/vendor/qcom/opensource/audiolite/test_drivers/pvm/audiolite-dlkm.conf -D ${D}${libdir}/modules-load.d/audiolite-dlkm.conf
}

FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/*"
FILES:${PN} += "${libdir}/modules-load.d/*"

RPROVIDES:${PN} += "${@'kernel-module-ipcc-shmem-test-module-${KERNEL_VERSION}'.replace('_', '-')}"