SUMMARY = "logwrapper - Android wrapper library for logging"
DESCRIPTION = "This library provides a wrapper interface for logging to the Android logging system. \
Includes an option to log to the kernel log."
HOMEPAGE = "https://www.codelinaro.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS += "libcutils liblog"



SRCPROJECT  = "git://${OSS_REPO}/clo/le/platform/system/core.git"
SRCBRANCH  = "lv-blast.lnx.1.1.r56-rel"
SRCREV  = "0d3d6f588509bdd67606fb783d50b5dd415562e6"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=system/core;"

S = "${WORKDIR}/system/core/logwrapper"

inherit autotools pkgconfig

BBCLASSEXTEND = "native"

PACKAGE_BEFORE_PN = "${PN}-utils"
FILES:${PN}-utils = "${bindir}/logwrapper"
