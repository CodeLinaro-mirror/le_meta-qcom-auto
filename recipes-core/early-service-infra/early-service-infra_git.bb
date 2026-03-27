SUMMARY = "systemd target for early services"
DESCRIPTION = "\
This systemd target is a synchronization point for all early services. \
Early services shall be configured to start Before=early-services.target"

HOMEPAGE = "https://git.codelinaro.org"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "systemd bootkpi-logging"



SRCPROJECT  = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/safelinux-services.git"
SRCBRANCH  = "safe-services.lnx.1.0.r19-rel"
SRCREV  = "980d81535505f2a2d7292789d69004016c3dbe49"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/safelinux-services;"


S = "${WORKDIR}/vendor/qcom/opensource/safelinux-services/early-service-infra"

inherit pkgconfig cmake systemd

SYSTEMD_SERVICE:${PN} = "early-services.target"

EXTRA_OECMAKE += "-DEXAMPLE_SERVICE:BOOL=OFF"
