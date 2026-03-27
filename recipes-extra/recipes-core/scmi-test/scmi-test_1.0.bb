SUMMARY = "scmi-test"
DESCRIPTION = "Applications to test scmi framework and gearvm"
HOMEPAGE = "https://git.codelinaro.org/"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a"
DEPENDS += "safelinux-cfg-modules virtual/kernel-headers glib-2.0"



SRCPROJECT  = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/safelinux-services.git"
SRCBRANCH  = "safe-services.lnx.1.0.r19-rel"
SRCREV  = "980d81535505f2a2d7292789d69004016c3dbe49"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/safelinux-services;"

S = "${WORKDIR}/vendor/qcom/opensource/safelinux-services/scmi-test"

EXTRA_OECMAKE:append:gen5 = " -DVENDOR_USCMI=1"

inherit pkgconfig cmake
