SUMMARY = "AR SCMI"
DESCRIPTION = "This is a library which provides SCMI interface wrapper to clients."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "audio-log-util ar-osal safelinux-cfg-modules virtual/kernel-headers"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/audio-ar-service.git;branch=auto-audio-lrh.lnx.2.0.r5-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/audio-ar-service"
SRCREV = "448b14e0fae27e93b570a0a073e2d71da32557a6"

S = "${WORKDIR}/vendor/qcom/opensource/audio-ar-service/audio_driver/ar_scmi"

inherit cmake pkgconfig

CFLAGS += "\
    -I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel} \
    -I${STAGING_INCDIR}/uapi \
"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
