SUMMARY = "Install kiumd uapi headers"
DESCRIPTION = "This contains headers userspace API and DLKM conf files."
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPLv2.0-with-linux-syscall-note"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "\
    ${CLO_LE_GIT}/platform/vendor/qcom-opensource/kiumd.git;branch=safe-services.lnx.1.0.r24-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/kiumd \
"
SRCREV = "f6e8c384a2d0cc0e7b79e1d8615a1ad5af55e860"

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
