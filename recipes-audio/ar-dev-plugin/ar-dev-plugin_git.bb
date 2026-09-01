SUMMARY = "AR Dev Plugin"
DESCRIPTION = "This is the library which loads and executes codec/dac/expander modules"
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "audio-log-util ar-osal libkiumd"
DEPENDS:append:gen5 = " ar-scmi"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/audio-ar-service.git;branch=auto-audio-lrh.lnx.2.0.r5-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/audio-ar-service"
SRCREV = "2c7ab8382615650f3e9e154aa6377951a2b5a2e0"

S = "${WORKDIR}/vendor/qcom/opensource/audio-ar-service/audio_driver/ar_dev_plugin"

inherit cmake pkgconfig

EXTRA_OECMAKE:append:gen5 = " -DENABLE_TARGET=sa8797"

RDEPENDS:${PN} += "audio-utils libkiumd"
RDEPENDS:${PN}:append:gen5 = " ar-scmi"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
