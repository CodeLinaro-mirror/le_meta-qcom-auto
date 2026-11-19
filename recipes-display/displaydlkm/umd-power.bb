SUMMARY = "UMD Power Driver For Multimedia"
DESCRIPTION = "Build umd power control to kernel module"
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

DEPENDS += "umd-power-devicetree"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/opensource/display-drivers.git;branch=display-kernel.lnx.12.5.r18-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/display-drivers"
SRC_URI:sa8775-flex = "${CLO_LE_GIT}/platform/vendor/opensource/gen4-5/display-drivers.git;branch=display-kernel_gen4-5.lnx.5.15.1.r2-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/display-drivers"

SRCREV = "10bb21ddaa0962244cca234a034d16a99ddf4d12"
SRCREV:sa8775-flex = "49d7d7761d6c87e46cb15d40b9bc45d20ced5ebe"

S = "${WORKDIR}/vendor/qcom/opensource/display-drivers/umd-mm"

TECHPACK_MODULE_OUT = "${WORKDIR}/display-drivers"
TECHPACK_MODULES = "umd_power.ko"
TECHPACK_HEADERS = "${S}/include/uapi"

inherit qti-techpack

RPROVIDES:${PN} += "kernel-module-umd-power-${KERNEL_VERSION}"

FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/*"
FILES:${PN} += "${sysconfdir}/*"
