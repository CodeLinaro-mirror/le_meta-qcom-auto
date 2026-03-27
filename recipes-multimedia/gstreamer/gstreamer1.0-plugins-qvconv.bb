SUMMARY = "QVconv Plugin for GStreamer"
DESCRIPTION = "QTI color converter plugin for GStreamer, convert color formats between UYVY, NV12_UBWC, NV12, BGR and RGBA"
HOMEPAGE = "https://git.codelinaro.org/"
SECTION = "multimedia"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${QTI_LICENSE_DIR}/${LICENSE};md5=b796c0007db682166a1721da80267bb2"

DEPENDS += "\
    adreno \
    glib-2.0 \
    gstreamer1.0 \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base \
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', '', 'graphicsdlkm videodlkm displaydlkm', d)} \
    virtual/kernel-headers \
    virtual/libc \
"



SRCPROJECT  = "${CLO_LA_GIT}/platform/vendor/qcom-opensource/gst-plugins-qti-oss.git"
SRCBRANCH  = "gst-auto-tools-plugins.4.0.r9-rel"
SRCREV  = "9834d8bede25ebaf8a1828ae1eff81a58315a5c5"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=gstreamer/gst-plugins-qti-oss;"
S = "${WORKDIR}/gstreamer/gst-plugins-qti-oss/gst-plugin-qvconv"

#inherit python3native to export related STAGING ENVs
inherit meson pkgconfig python3native

CXXFLAGS += "\
    -I${STAGING_INCDIR} \
    -I${STAGING_INCDIR}/glib-2.0 \
    -I${STAGING_LIBDIR}/glib-2.0/include \
    -I${STAGING_INCDIR}/c++ \
    -I${STAGING_INCDIR}/c++/${TARGET_SYS} \
    -I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel} \
    -I${STAGING_INCDIR}/mm-core \
    -I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel}/display \
"

EXTRA_OEMESON += "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', '-Duseumd=true', '', d)} \
"

FILES:${PN} += "${libdir}/gstreamer-1.0/*.so"
