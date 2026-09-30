SUMMARY = "gst video codec test applications"
DESCRIPTION = "gst video codec test applications which used to verify some encoder/decoder features"
HOMEPAGE = "https://git.codelinaro.org"
SECTION = "multimedia"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${QTI_LICENSE_DIR}/${LICENSE};md5=b796c0007db682166a1721da80267bb2"

DEPENDS += "\
    glib-2.0 \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
"

SRC_URI = "${CLO_LA_GIT}/platform/vendor/qcom-opensource/gst-plugins-qti-oss.git;branch=gst-auto-tools-plugins.4.0.r14-rel;protocol=${OSS_PROTO};destsuffix=gstreamer/gst-plugins-qti-oss"
SRCREV = "f783693ef24a51229a567a92f72ec094d15b6f83"
S = "${WORKDIR}/gstreamer/gst-plugins-qti-oss/gst-qv-codec-test"

inherit pkgconfig meson

CFLAGS += "\
    -I${STAGING_INCDIR}/glib-2.0 \
    -I${STAGING_LIBDIR}/glib-2.0/include \
    -I${STAGING_INCDIR}/glib-2.0/glib \
    -I${STAGING_INCDIR}/gstreamer-1.0 \
"
