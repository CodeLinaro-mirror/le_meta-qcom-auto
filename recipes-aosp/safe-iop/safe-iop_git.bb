SUMMARY = "Safe integer operation library for C"
DESCRIPTION = "This library supplies a set of standard functions for performing and checking safe integer operations"
HOMEPAGE = "http://developer.android.com/"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://NOTICE;md5=e7235a4d576addf0c399983b1c7f673e"

SRC_URI = "${CLO_LE_GIT}/platform/external/safe-iop.git;branch=lv-blast.lnx.1.1.r63-rel;protocol=${OSS_PROTO};destsuffix=external/safe-iop"
SRCREV = "0a7c456e8ddb35dd29e2105fecee93a7ec02f968"

S = "${WORKDIR}/external/safe-iop"

inherit autotools-brokensep
