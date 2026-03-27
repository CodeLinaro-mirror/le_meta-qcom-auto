SUMMARY = "Vhost user library"
DESCRIPTION = "vhost user library implement the vhost user protocol"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"



SRCPROJECT  = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/vhost-user-lib.git"
SRCBRANCH  = "vhost-user-lib.lnx.1.0.r11-rel"
SRCREV  = "70dce40fce94e9f6e9d6a7d9ed1637b962bd2e8d"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/vhost-user-lib;"
DEPENDS = "systemd"

S = "${WORKDIR}/vendor/qcom/opensource/vhost-user-lib"

inherit cmake

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
