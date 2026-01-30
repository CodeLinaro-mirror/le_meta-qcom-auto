SUMMARY = "Vhost user library"
DESCRIPTION = "vhost user library implement the vhost user protocol"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"



SRCPROJECT  = "git://${OSS_REPO}/clo/le/platform/vendor/qcom-opensource/vhost-user-lib.git"
SRCBRANCH  = "vhost-user-lib.lnx.1.0.r10-rel"
SRCREV  = "9fa657c0fcaf76b9c68032061c4997e43b6eac13"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/vhost-user-lib;"
DEPENDS = "systemd"

S = "${WORKDIR}/vendor/qcom/opensource/vhost-user-lib"

inherit cmake

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
