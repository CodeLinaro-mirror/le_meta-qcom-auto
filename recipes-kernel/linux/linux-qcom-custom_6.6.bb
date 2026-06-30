SUMMARY = "QCOM Linux Kernel"
DESCRIPTION = "QCOM Linux Kernel for QTI SoC"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPLv2.0-with-linux-syscall-note"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

require recipes-kernel/linux/linux-qcom.inc

COMPATIBLE_MACHINE = "sa8775|sa7255|gen5"

SRC_URI = "\
    ${CLO_LA_GIT}/kernel/qcom.git;branch=kernel.qclinux.1.0.r16-rel;protocol=${OSS_PROTO};destsuffix=kernel/kernel_platform/kernel \
"
SRCREV = "40205a85d096cfaaaff98e26b8d0aa39560faebb"

S = "${WORKDIR}/kernel/kernel_platform/kernel"
