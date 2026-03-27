SUMMARY = "QTI Video Deinterlace plugin for GStreamer"
DESCRIPTION = "Gstreamer video deinterlace plugin based on GPU hardware deinterlace"
HOMEPAGE = "https://git.codelinaro.org/"
SECTION = "multimedia"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${QTI_LICENSE_DIR}/${LICENSE};md5=b796c0007db682166a1721da80267bb2"

DEPENDS += "\
    adreno \
    display-commonsys-intf-linux \
    gbm \
    gbm-headers \
    glib-2.0 \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', '', 'videodlkm displaydlkm', d)} \
    virtual/kernel-headers \
    mm-gfx-auto-prop \
"



SRCPROJECT  = "${CLO_LA_GIT}/platform/vendor/qcom-opensource/gst-plugins-qti-oss.git"
SRCBRANCH  = "gst-auto-tools-plugins.4.0.r9-rel"
SRCREV  = "9834d8bede25ebaf8a1828ae1eff81a58315a5c5"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=gstreamer/gst-plugins-qti-oss;"
S = "${WORKDIR}/gstreamer/gst-plugins-qti-oss/gst-plugin-qvdeinterlace"

inherit meson pkgconfig

CFLAGS += "\
    -I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel} \
    -I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel}/display \
"

EXTRA_OEMESON += "\
    -Dmmmcolorfmt=true \
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', '-Duseumd=true', '', d)} \
"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

FILES:${PN} += "${libdir}/gstreamer-1.0/*.so"
RDEPENDS:${PN} += "mm-gfx-auto-prop"
