SUMMARY = "Safe integer operation library for C"
DESCRIPTION = "This library supplies a set of standard functions for performing and checking safe integer operations"
HOMEPAGE = "http://developer.android.com/"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://NOTICE;md5=e7235a4d576addf0c399983b1c7f673e"



SRCPROJECT  = "git://${OSS_REPO}/clo/le/platform/external/safe-iop.git"
SRCBRANCH  = "lv-blast.lnx.1.1.r56-rel"
SRCREV  = "0a7c456e8ddb35dd29e2105fecee93a7ec02f968"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=external/safe-iop;"

S = "${WORKDIR}/external/safe-iop"

inherit autotools-brokensep
