SUMMARY = "camera-metadata"
DESCRIPTION = "Recipe to provide Camera Metadata library"
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS += "libcutils"



SRCPROJECT  = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/le-framework.git"
SRCBRANCH  = "lv-frameworks.lnx.1.0.r56-rel"
SRCREV  = "1805874baadb4d9e7876d0960864a0942b8d8e2e"

SRC_URI = "\
    ${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=frameworks; \
"


S = "${WORKDIR}/frameworks/camera_metadata"

inherit autotools pkgconfig

FILES:${PN}-dbg += "${libdir}/.debug/lib*.*"
FILES:${PN} += "${libdir}/lib*.so.* ${libdir}/pkgconfig/*"
FILES:${PN}-dev += "${libdir}/lib*.so ${libdir}/lib*.la"
