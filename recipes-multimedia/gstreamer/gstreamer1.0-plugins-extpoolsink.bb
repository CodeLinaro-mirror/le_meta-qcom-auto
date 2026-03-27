SUMMARY = "Video external buffer pool sink plugin for GStreamer"
DESCRIPTION = "Gstreamer video sink plugin to provide external buffer pool"
HOMEPAGE = "https://git.codelinaro.org/"
SECTION = "multimedia"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${QTI_LICENSE_DIR}/${LICENSE};md5=b796c0007db682166a1721da80267bb2"

DEPENDS += "\
    display-commonsys-intf-linux \
    gbm \
    gbm-headers \
    glib-2.0 \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', '', 'videodlkm', d)} \
    virtual/kernel-headers \
"

DEPENDS:append:quin-gvm-lemans = " displaydlkm"
DEPENDS:append:quin-gvm-monaco = " displaydlkm"



SRCPROJECT  = "${CLO_LA_GIT}/platform/vendor/qcom-opensource/gst-plugins-qti-oss.git"
SRCBRANCH  = "gst-auto-tools-plugins.4.0.r9-rel"
SRCREV  = "9834d8bede25ebaf8a1828ae1eff81a58315a5c5"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=gstreamer/gst-plugins-qti-oss;"
S = "${WORKDIR}/gstreamer/gst-plugins-qti-oss/gst-plugin-extpoolsink"

inherit meson pkgconfig

CFLAGS += "-I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel}"

CFLAGS:append:quin-gvm-lemans = " -I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel}/display"
EXTRA_OEMESON:append:quin-gvm-lemans = " \
    -Dmmmcolorfmt=true \
"

CFLAGS:append:quin-gvm-monaco = " -I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel}/display"
EXTRA_OEMESON:append:quin-gvm-monaco = " \
     -Dmmmcolorfmt=true \
"
EXTRA_OEMESON:append = " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', '-Dmmmcolorfmt=true -Duseumd=true', '', d)} \
"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

FILES:${PN} += "${libdir}/gstreamer-1.0/*.so"
