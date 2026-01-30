SUMMARY = "Install virtio-video uapi headers"
DESCRIPTION = "This contains headers userspace API"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"



SRCPROJECT  = "git://${OSS_REPO}/clo/la/platform/vendor/opensource/virtio-video.git"
SRCBRANCH  = "video-hyp.lnx.2.0.r41-rel"
SRCREV  = "bd638ccaa3183012d992912bc58457686d7ad4b0"

SRC_URI = "\
    ${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/virtio-video; \
"

S = "${WORKDIR}/vendor/qcom/opensource/virtio-video"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install[depends] += "virtual/kernel:do_shared_workdir"

do_install:append() {
    install -d -p ${D}${includedir}/
    cd ${STAGING_KERNEL_BUILDDIR}
    ${STAGING_KERNEL_DIR}/scripts/headers_install.sh ${S}/include/virtio_video.h ${D}${includedir}/virtio_video.h
    if [ -f ${S}/include/virtio_video_msm_ext.h ]; then
       ${STAGING_KERNEL_DIR}/scripts/headers_install.sh ${S}/include/virtio_video_msm_ext.h ${D}${includedir}/virtio_video_msm_ext.h
    fi
    if [ -f ${S}/include/virtio_video_hw_virt.h ]; then
       ${STAGING_KERNEL_DIR}/scripts/headers_install.sh ${S}/include/virtio_video_hw_virt.h ${D}${includedir}/virtio_video_hw_virt.h
    fi
}

ALLOW_EMPTY:${PN} = "1"
