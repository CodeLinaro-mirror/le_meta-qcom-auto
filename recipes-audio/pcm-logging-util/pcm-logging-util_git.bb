SUMMARY = "PCM Logging Utility"
DESCRIPTION = "This is a test application to exercise SPF PCM logging settings"
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=b796c0007db682166a1721da80267bb2"

DEPENDS += "agm"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/audio-ar-service.git;branch=auto-audio-lrh.lnx.2.0.r5-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/audio-ar-service/audio_driver/pcm_logging_util;subpath=audio_driver/pcm_logging_util"
SRCREV = "80f68da38e60f8cfdb81b2f32a23e09b6ac5d0eb"

S = "${WORKDIR}/vendor/qcom/opensource/audio-ar-service/audio_driver/pcm_logging_util"

inherit cmake pkgconfig

CFLAGS += "-I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel}"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
