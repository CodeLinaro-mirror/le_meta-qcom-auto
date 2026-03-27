SUMMARY = "bootkpi-logging API"
DESCRIPTION = "bootkpi-logging API that abstracts the implementation of bootkpi logging for other userspace apps"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "glibc systemd"



SRCPROJECT  = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/safelinux-system-cfg.git"
SRCBRANCH  = "safe-services.lnx.1.0.r19-rel"
SRCREV  = "ccb920c69536aec2b52b17b6880bcd4ac2660261"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/safelinux-system-cfg;"
S = "${WORKDIR}/vendor/qcom/opensource/safelinux-system-cfg/bootkpi-logging"

inherit pkgconfig cmake
