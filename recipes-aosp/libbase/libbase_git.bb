SUMMARY = "Android base library"
DESCRIPTION = "This library provides APIs for basic tasks like \
handling files, Unicode strings, logging, memory allocation, \
integer parsing, etc."
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../NOTICE;md5=c1a3ff0b97f199c7ebcfdd4d3fed238e"

DEPENDS += "libcutils"

PR = "r1"



SRCPROJECT  = "${CLO_LE_GIT}/platform/system/core.git"
SRCBRANCH  = "lv-blast.lnx.1.1.r57-rel"
SRCREV  = "004f355bc763dd7df18b650afe34c42b566c1545"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=system/core;"

S = "${WORKDIR}/system/core/base"

inherit autotools pkgconfig

EXTRA_OECONF = "--with-core-sourcedir=${WORKDIR}/system/core"

BBCLASSEXTEND = "native"
