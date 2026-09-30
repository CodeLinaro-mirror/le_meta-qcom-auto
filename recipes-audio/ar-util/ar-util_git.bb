SUMMARY = "AudioReach Util library"
DESCRIPTION = "This is the library used to define public AudioReach util APIs for double linked list."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "glib-2.0"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/args.git;branch=audio-core-auto.lnx.2.0.r5-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/args"
SRCREV = "3bd8490decae7456de9bd07b4d0f4a718ce218ca"

S = "${WORKDIR}/vendor/qcom/opensource/args/ar_util"

inherit pkgconfig cmake

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
