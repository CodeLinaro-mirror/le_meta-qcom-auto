SUMMARY = "VMM power control utility"
DESCRIPTION = "Send power control commands to a Gunyah VM via the VMM power key service"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "vmm-lib"
SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/vm-tools.git;branch=auto-vmm.lnx.1.0.r29-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/vm-tools"
SRCREV = "36a86167c63220e0862206631908304d30d50e90"
S = "${WORKDIR}/vendor/qcom/opensource/vm-tools/vm-powerctl"
RDEPENDS:${PN} = "vmm-lib"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit cmake pkgconfig
