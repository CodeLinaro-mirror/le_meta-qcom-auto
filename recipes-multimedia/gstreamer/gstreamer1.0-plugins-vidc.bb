SUMMARY = "QTI vidc Plugin for GStreamer"
DESCRIPTION = "Gstreamer H/W decoder and encoder plugins based on vidc APIs"
HOMEPAGE = "https://git.codelinaro.org/"
SECTION = "multimedia"
LICENSE = "BSD-3-Clause & BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9 \
                    file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "\
    aosal \
    glib-2.0 \
    gstreamer1.0 \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base \
    gbm \
    gbm-headers \
    libdrm \
    libkiumd \
    libxml2 \
    mm-osal \
    planedef \
    video-driver \
"



SRCPROJECT  = "${CLO_LA_GIT}/platform/vendor/qcom-opensource/gst-plugins-qti-oss.git"
SRCBRANCH  = "gst-auto-tools-plugins.4.0.r9-rel"
SRCREV  = "9834d8bede25ebaf8a1828ae1eff81a58315a5c5"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=gstreamer/gst-plugins-qti-oss;"
S = "${WORKDIR}/gstreamer/gst-plugins-qti-oss/gst-plugin-vidc"

inherit meson pkgconfig

CFLAGS += "\
    -I${STAGING_INCDIR}/c++ \
    -I${STAGING_INCDIR}/c++/${TARGET_SYS} \
    -I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel}/vidc \
    -I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel} \
    -I${STAGING_INCDIR}/mm-osal/include \
"

CXXFLAGS += "\
    -I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel}/vidc \
    -I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel} \
    -I${STAGING_INCDIR}/mm-osal/include \
"

EXTRA_OEMESON = " \
    -Dav1-dec=enabled \
    -Dusedmaheap=true \
"

PACKAGE_ARCH ?= "${MACHINE_ARCH}"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

FILES:${PN} += "${libdir}/gstreamer-1.0/*.so"
