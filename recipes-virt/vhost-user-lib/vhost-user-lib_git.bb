SUMMARY = "Vhost user library"
DESCRIPTION = "vhost user library implement the vhost user protocol"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/vhost-user-lib.git;branch=vhost-user-lib.lnx.1.0.r15-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/vhost-user-lib"
SRCREV = "c85ec83a4867ae06ec86f60382cb5927cb885466"
DEPENDS = "systemd"

S = "${WORKDIR}/vendor/qcom/opensource/vhost-user-lib"

inherit cmake

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
