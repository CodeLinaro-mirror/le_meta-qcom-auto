SUMMARY = "logwrapper - Android wrapper library for logging"
DESCRIPTION = "This library provides a wrapper interface for logging to the Android logging system. \
Includes an option to log to the kernel log."
HOMEPAGE = "https://www.codelinaro.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS += "libcutils liblog"

SRC_URI = "${CLO_LE_GIT}/platform/system/core.git;branch=lv-blast.lnx.1.1.r63-rel;protocol=${OSS_PROTO};destsuffix=system/core"
SRCREV = "e407a64ace2ee2d2b77ce3d659edbf9f92254ef6"

S = "${WORKDIR}/system/core/logwrapper"

inherit autotools pkgconfig

BBCLASSEXTEND = "native"

PACKAGE_BEFORE_PN = "${PN}-utils"
FILES:${PN}-utils = "${bindir}/logwrapper"
