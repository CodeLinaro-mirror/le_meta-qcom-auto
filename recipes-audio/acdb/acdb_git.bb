SUMMARY = "Audio Calibration Database"
DESCRIPTION = "This is a library of the Audio Calibration Database (ACDB) module."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"
DEPENDS += "ar-osal"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/args.git;branch=audio-core-auto.lnx.2.0.r5-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/args/acdb;subpath=acdb"
SRCREV = "71c353f0b4427f1d33a7a5e7da374c7be062d74d"

S = "${WORKDIR}/vendor/qcom/opensource/args/acdb"

inherit pkgconfig cmake

RDEPENDS:${PN} += "ar-osal"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
