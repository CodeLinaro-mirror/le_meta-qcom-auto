SUMMARY = "Android library for mincrypt"
DESCRIPTION = "This library provides minimalistic encryption support and \
implements SHA1 and SHA-256 hash algoraithm"
HOMEPAGE = "http://developer.android.com/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://NOTICE;md5=c19179f3430fd533888100ab6616e114"

SRC_URI = "${CLO_LE_GIT}/platform/system/core.git;branch=lv-blast.lnx.1.1.r63-rel;protocol=${OSS_PROTO};destsuffix=system/core"
SRCREV = "e1ff195e2d007b85996c4dd6e61d571ee982496a"

S = "${WORKDIR}/system/core/libmincrypt"

inherit autotools pkgconfig

EXTRA_OECONF += "--with-core-includes=${WORKDIR}/system/core/include"

BBCLASSEXTEND = "native"
