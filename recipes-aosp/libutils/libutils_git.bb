SUMMARY = "Android Utility Function Library"
DESCRIPTION = "This library provides miscellaneous utility functions \
and common definitions, such as log, thread, buffer, vector and mutex. \
It is used by some QTI multimedia components like ais and video. \
It is implemented by C++."
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://NOTICE;md5=9645f39e9db895a4aa6e02cb57294595"

DEPENDS += "safe-iop"

PR = "r1"



SRCPROJECT  = "${CLO_LE_GIT}/platform/system/core.git"
SRCBRANCH  = "lv-blast.lnx.1.1.r57-rel"
SRCREV  = "004f355bc763dd7df18b650afe34c42b566c1545"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=system/core;"

S = "${WORKDIR}/system/core/libutils"

inherit autotools pkgconfig

EXTRA_OECONF += "\
    --with-system-core-includes=${WORKDIR}/system/core/include \
    --with-liblog-includes=${WORKDIR}/system/core/liblog \
"
