SUMMARY = "QCOM Linux Kernel"
DESCRIPTION = "QCOM Linux Kernel for QTI SoC"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPLv2.0-with-linux-syscall-note"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

require recipes-kernel/linux/linux-qcom.inc

COMPATIBLE_MACHINE = "sa8775|sa7255|gen5"

SRCPROJECT  = "${CLO_LA_GIT}/kernel/qcom.git"
SRCBRANCH  = "kernel.qclinux.1.0.r13-rel"
SRCREV  = "01859753058a29a4e6603490cefc1c524a88de0d"



SRC_URI = "\
${SRCPROJECT};protocol=${OSS_PROTO};branch=${SRCBRANCH};destsuffix=kernel/kernel_platform/kernel  \
"


S = "${WORKDIR}/kernel/kernel_platform/kernel"

