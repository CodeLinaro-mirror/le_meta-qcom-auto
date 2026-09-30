SUMMARY = "Audio Utils"
DESCRIPTION = "This is a library which provides interface for SMMU helper & workloop functionality"
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "audio-log-util audio-headers-export glib-2.0 mm-osal libkiumd virtual/kernel-headers"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/audio-ar-service.git;branch=auto-audio-lrh.lnx.2.0.r5-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/audio-ar-service/audio_driver/utils;subpath=audio_driver/utils"
SRCREV = "d7661a2df3bfd5432c06bf1add22f708f6557a2d"

S = "${WORKDIR}/vendor/qcom/opensource/audio-ar-service/audio_driver/utils"

inherit cmake pkgconfig

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
