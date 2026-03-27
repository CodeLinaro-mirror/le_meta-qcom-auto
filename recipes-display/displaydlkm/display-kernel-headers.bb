SUMMARY = "provide display drivers header"
DESCRIPTION = "provide display driver headers"
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "GPLv2.0-with-linux-syscall-note"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"



SRCPROJECT  = "${CLO_LE_GIT}/platform/vendor/opensource/display-drivers.git"
SRCBRANCH  = "display-kernel.lnx.12.5.r11-rel"
SRCREV  = "7eaf534a5cadff172fb35660776a9ae6facb752d"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/display-drivers;"
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
