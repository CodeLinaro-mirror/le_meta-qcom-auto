SUMMARY = "vmm boot lifecycle manager binary"
DESCRIPTION = "Manage the boot lifecycle of vms through vmm service"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "glib-2.0 vmm-lib abctl"


SRCPROJECT  = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/vmm-boot-lcm.git"
SRCBRANCH  = "vmm.apss.1.0.r13-rel"
SRCREV  = "c9011811cb106fe6f5d62195d39d97530e71ccea"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/vmm-boot-lcm;"
S = "${WORKDIR}/vendor/qcom/opensource/vmm-boot-lcm"
RDEPENDS:${PN} = "vmm-lib abctl"

SYSTEMD_SERVICE:${PN} = "vmm-boot-lcm.service"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit cmake pkgconfig systemd

do_install:append() {
    install -d ${D}/${systemd_unitdir}/system
    install -m 0644 ${S}/vmm-boot-lcm.service ${D}/${systemd_unitdir}/system/vmm-boot-lcm.service
}
