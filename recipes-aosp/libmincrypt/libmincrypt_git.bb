SUMMARY = "Android library for mincrypt"
DESCRIPTION = "This library provides minimalistic encryption support and \
implements SHA1 and SHA-256 hash algoraithm"
HOMEPAGE = "http://developer.android.com/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://NOTICE;md5=c19179f3430fd533888100ab6616e114"



SRCPROJECT  = "git://${OSS_REPO}/clo/le/platform/system/core.git"
SRCBRANCH  = "lv-blast.lnx.1.1.r56-rel"
SRCREV  = "0d3d6f588509bdd67606fb783d50b5dd415562e6"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=system/core;"

S = "${WORKDIR}/system/core/libmincrypt"

inherit autotools pkgconfig

EXTRA_OECONF += "--with-core-includes=${WORKDIR}/system/core/include"

BBCLASSEXTEND = "native"
