SUMMARY = "Install kiumd uapi headers"
DESCRIPTION = "This contains headers userspace API and DLKM conf files."
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPLv2.0-with-linux-syscall-note"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"



SRCPROJECT  = "git://${OSS_REPO}/clo/le/platform/vendor/qcom-opensource/kiumd.git"
SRCBRANCH  = "safe-services.lnx.1.0.r18-rel"
SRCREV  = "90079a9bd5e3abe24cb234ce62fc84c7754d77b1"

SRC_URI = "\
    ${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/kiumd; \
"

S = "${WORKDIR}/vendor/qcom/opensource/kiumd/kiumd-headers"

do_compile[noexec] = "1"
do_install[depends] += "virtual/kernel:do_shared_workdir"

do_install:append() {
    install -d ${D}${libdir}/modules-load.d/
    install -m 0755 ${S}/kiumd.conf -D ${D}${libdir}/modules-load.d/kiumd.conf
    install -m 0755 ${S}/vfioiommu.conf -D ${D}${libdir}/modules-load.d/vfioiommu.conf
    install -m 0755 ${S}/appspinctrl.conf -D ${D}${libdir}/modules-load.d/appspinctrl.conf

    install -d -p ${D}${includedir}/uapi/misc
    cd ${STAGING_KERNEL_BUILDDIR}
    ${STAGING_KERNEL_DIR}/scripts/headers_install.sh ${S}/scmioctl.h ${D}${includedir}/uapi/misc/scmioctl.h
}

FILES:${PN} += "${libdir}/modules-load.d/*"
