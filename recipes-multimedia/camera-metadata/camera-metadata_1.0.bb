SUMMARY = "camera-metadata"
DESCRIPTION = "Recipe to provide Camera Metadata library"
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS += "libcutils"

SRC_URI = "\
    ${CLO_LE_GIT}/platform/vendor/qcom-opensource/le-framework.git;branch=lv-frameworks.lnx.1.0.r61-rel;protocol=${OSS_PROTO};destsuffix=frameworks \
"

SRCREV = "8d1eff3b8af56e7753e3deafc141cdb17b4967c3"

S = "${WORKDIR}/frameworks/camera_metadata"

inherit autotools pkgconfig

FILES:${PN}-dbg += "${libdir}/.debug/lib*.*"
FILES:${PN} += "${libdir}/lib*.so.* ${libdir}/pkgconfig/*"
FILES:${PN}-dev += "${libdir}/lib*.so ${libdir}/lib*.la"
