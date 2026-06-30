SUMMARY = "systemd target for early services"
DESCRIPTION = "\
This systemd target is a synchronization point for all early services. \
Early services shall be configured to start Before=early-services.target"

HOMEPAGE = "https://git.codelinaro.org"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "systemd bootkpi-logging"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/safelinux-services.git;branch=safe-services.lnx.1.0.r24-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/safelinux-services"

SRCREV = "3128411e0312b7ad0e397c923c08990190525dfd"

S = "${WORKDIR}/vendor/qcom/opensource/safelinux-services/early-service-infra"

inherit pkgconfig cmake systemd

SYSTEMD_SERVICE:${PN} = "early-services.target"

EXTRA_OECMAKE += "-DEXAMPLE_SERVICE:BOOL=OFF"
