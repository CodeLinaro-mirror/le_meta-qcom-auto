SUMMARY = "scmi-test"
DESCRIPTION = "Applications to test scmi framework and gearvm"
HOMEPAGE = "https://git.codelinaro.org/"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a"
DEPENDS += "safelinux-cfg-modules virtual/kernel-headers glib-2.0"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/safelinux-services.git;branch=safe-services.lnx.1.0.r24-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/safelinux-services"
SRCREV = "3128411e0312b7ad0e397c923c08990190525dfd"

S = "${WORKDIR}/vendor/qcom/opensource/safelinux-services/scmi-test"

EXTRA_OECMAKE:append:gen5 = " -DVENDOR_USCMI=1"

inherit pkgconfig cmake
