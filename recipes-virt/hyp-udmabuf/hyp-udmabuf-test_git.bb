SUMMARY = "Hyp udmabuf test"
DESCRIPTION = "This is the hyp udmabuf test used to test hyp dmabuf"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "libkiumd"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/hyp-udmabuf.git;branch=auto-vmm-kernel.lnx.1.0.r15-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/hyp-udmabuf"

SRCREV = "edc91039526c90b1160ed84344fe875e4b7cedd8"

S = "${WORKDIR}/vendor/qcom/opensource/hyp-udmabuf/test"

inherit cmake

