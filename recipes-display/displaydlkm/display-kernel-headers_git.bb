SUMMARY = "provide display drivers header"
DESCRIPTION = "provide display driver headers"
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "GPLv2.0-with-linux-syscall-note"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/opensource/display-drivers.git;branch=display-kernel.lnx.12.5.r18-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/display-drivers"
SRC_URI:sa8775-flex = "${CLO_LE_GIT}/platform/vendor/opensource/gen4-5/display-drivers.git;branch=display-kernel_gen4-5.lnx.5.15.1.r2-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/display-drivers"
SRCREV = "9e756ed0a7e89e66c15508d48a1324d7d090977e"

SRCREV:sa8775-flex = "76924a6d16bc6a9d03fdfa0933e9db818b3627e5"
S = "${WORKDIR}/vendor/qcom/opensource/display-drivers/include/uapi"


DRM_UAPI_HEADERS = "\
    drm/msm_drm_pp.h \
    drm/sde_drm.h \
"

DRM_UAPI_HEADERS:append:gen5 = " drm/msm_drm_aiqe.h"

MEDIA_UAPI_HEADERS = "\
    media/mmm_color_fmt.h \
    media/msm_sde_rotator.h \
"

# There's nothing to do here, except install the headers where we can package them
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install[depends] += "virtual/kernel:do_shared_workdir"

do_install() {
   HEADER_INSTALL_TOOL=${STAGING_KERNEL_DIR}/scripts/headers_install.sh

   cd ${STAGING_KERNEL_BUILDDIR}

   install -d ${D}${includedir}/drm
   for h in ${DRM_UAPI_HEADERS}; do
        ${HEADER_INSTALL_TOOL} ${S}/display/$h ${D}${includedir}/$h
   done

   install -d ${D}${includedir}/media
   for h in ${MEDIA_UAPI_HEADERS}; do
        ${HEADER_INSTALL_TOOL} ${S}/display/$h ${D}${includedir}/$h
   done
}

do_install:append:gen5() {
   install -d ${D}${includedir}/display/drm
   for h in ${DRM_UAPI_HEADERS}; do
        ${HEADER_INSTALL_TOOL} ${S}/display/$h ${D}${includedir}/display/$h
   done

   install -d ${D}${includedir}/display/media
   for h in ${MEDIA_UAPI_HEADERS}; do
        ${HEADER_INSTALL_TOOL} ${S}/display/$h ${D}${includedir}/display/$h
   done
}

ALLOW_EMPTY:${PN} = "1"
