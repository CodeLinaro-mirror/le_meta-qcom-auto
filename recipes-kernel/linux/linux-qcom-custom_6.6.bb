SUMMARY = "QCOM Linux Kernel"
DESCRIPTION = "QCOM Linux Kernel for QTI SoC"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPLv2.0-with-linux-syscall-note"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

require recipes-kernel/linux/linux-qcom.inc

COMPATIBLE_MACHINE = "sa8775|sa7255|gen5"


SRCPROJECT  = "git://${OSS_REPO}/clo/la/kernel/qcom.git"
SRCBRANCH  = "kernel.qclinux.1.0.r12-rel"
SRCREV  = "8ff69362ed0497623f5c58b1d6e6f2dee389ec42"



SRC_URI = "\
${SRCPROJECT};protocol=${OSS_PROTO};branch=${SRCBRANCH};destsuffix=kernel/kernel_platform/kernel  \
"


S = "${WORKDIR}/kernel/kernel_platform/kernel"

