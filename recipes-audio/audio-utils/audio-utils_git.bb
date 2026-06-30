SUMMARY = "Audio Utils"
DESCRIPTION = "This is a library which provides interface for SMMU helper & workloop functionality"
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "audio-log-util audio-headers-export glib-2.0 mm-osal libkiumd virtual/kernel-headers"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/audio-ar-service.git;branch=auto-audio-lrh.lnx.2.0.r5-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/audio-ar-service/audio_driver/utils;subpath=audio_driver/utils"
SRCREV = "448b14e0fae27e93b570a0a073e2d71da32557a6"

S = "${WORKDIR}/vendor/qcom/opensource/audio-ar-service/audio_driver/utils"

inherit cmake pkgconfig

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

SRC_URI:sa8775-flex = "${PROP_REPO}/platform/vendor/qcom-proprietary/ship/gen4-5/audio-service.git;branch=auto-audio-lrh_gen4-5.lnx.1.0.r2-rel;protocol=${PROP_PROTO};destsuffix=vendor/qcom/proprietary/audio-service/audio_driver/utils;subpath=audio_driver/utils"
SRCREV:sa8775-flex = "efdfb3e6efbab47614525fdbd0f739fd2834cc99"

