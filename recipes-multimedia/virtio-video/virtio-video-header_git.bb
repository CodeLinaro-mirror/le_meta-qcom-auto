SUMMARY = "Install virtio-video uapi headers"
DESCRIPTION = "This contains headers userspace API"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPLv2.0-with-linux-syscall-note"
LIC_FILES_CHKSUM = "file://${QTI_LICENSE_DIR}/${LICENSE};md5=8afb6abdac9a14cb18a0d6c9c151e9b4"

SRC_URI = "\
    ${CLO_LA_GIT}/platform/vendor/opensource/virtio-video.git;branch=video-hyp.lnx.3.0.r22-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/virtio-video \
"
SRC_URI:sa8775-flex = "\
    ${CLO_LA_GIT}/platform/vendor/opensource/gen4-5/virtio-video.git;branch=video-hyp_gen4-5.lnx.2.0.r2-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/virtio-video \
"

SRCREV = "eaa1d7cd1455aa6762f0bfce7e80cc626571284d"

SRCREV:sa8775-flex = "18bbe5158f7dee12a364b1094a66d74c060e1070"
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
