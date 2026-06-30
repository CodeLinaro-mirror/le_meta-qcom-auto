SUMMARY = "bootkpi-logging API"
DESCRIPTION = "bootkpi-logging API that abstracts the implementation of bootkpi logging for other userspace apps"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "glibc systemd"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/safelinux-system-cfg.git;branch=safe-services.lnx.1.0.r24-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/safelinux-system-cfg"
SRCREV = "0628db7814eeab139f72595e49392725798e0ad5"
S = "${WORKDIR}/vendor/qcom/opensource/safelinux-system-cfg/bootkpi-logging"

inherit pkgconfig cmake
