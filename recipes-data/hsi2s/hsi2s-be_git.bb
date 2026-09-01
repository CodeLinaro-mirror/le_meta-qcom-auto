SUMMARY = "hsi2s-be"
DESCRIPTION = "Application of hsi2s backend."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "hsi2s-interface libuhab"
SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/hsi2s.git;branch=vnw-hsi2s.lnx.1.0.r96-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/hsi2s"
SRCREV = "da4b1b752583dfbfeeefbe1d8d09a2cae9997474"
S = "${WORKDIR}/vendor/qcom/opensource/hsi2s/be"

inherit systemd cmake pkgconfig useradd

USERADD_PACKAGES = "${PN}"

USERADD_PARAM:${PN} = "--no-create-home --shell /bin/false -g vnw vnw"
GROUPADD_PARAM:${PN} = "vnw;"
