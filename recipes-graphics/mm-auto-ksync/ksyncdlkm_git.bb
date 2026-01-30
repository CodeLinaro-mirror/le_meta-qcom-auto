SUMMARY = "KSYNC drivers"
DESCRIPTION = "Build ksync driver to kernel module"
HOMEPAGE = "https://www.codelinaro.org"
LICENSE = "GPLv2.0-with-linux-syscall-note"
LIC_FILES_CHKSUM = "file://${QTI_LICENSE_DIR}/${LICENSE};md5=8afb6abdac9a14cb18a0d6c9c151e9b4"



SRCPROJECT  = "git://${OSS_REPO}/clo/le/platform/vendor/qcom/opensource/mm-auto-ksync.git"
SRCBRANCH  = "mm-auto-ksync.lnx.1.0.r7-rel"
SRCREV  = "c9c98c4f1b6fae73d56e962844b31005f237c3ff"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/mm-auto-ksync;"


S = "${WORKDIR}/vendor/qcom/opensource/mm-auto-ksync"

inherit qti-techpack

TECHPACK_MODULE_OUT = "${WORKDIR}/mm-auto-ksync"
TECHPACK_MODULES = "qcom_ksync.ko"
TECHPACK_HEADERS = "${S}/include/uapi"

do_install:append() {
    install -m 0755 ${S}/qcom_ksync.conf -D ${D}${sysconfdir}/modules-load.d/qcom_ksync.conf
    install -d ${D}${includedir}/linux
    install -m 0644 ${S}/include/uapi/linux/msm_ksync.h ${D}${includedir}/linux
}

RPROVIDES:${PN} += "kernel-module-qcom-ksync-${KERNEL_VERSION}"

FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/*"
FILES:${PN} += "${sysconfdir}/*"
