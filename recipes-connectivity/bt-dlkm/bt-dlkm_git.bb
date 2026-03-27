SUMMARY = "QTI Bluetooth Kernel Module"
DESCRIPTION = "QTI Bluetooth Kernel Module mainly includes btpower driver to\
power on/off QTI Bluetooth chips"
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${QTI_LICENSE_DIR}/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "bt-devicetree"



SRCPROJECT  = "${CLO_LA_GIT}/platform/vendor/qcom-opensource/bt-kernel.git"
SRCBRANCH  = "bt-kernel.lnx.1.1.r34-rel"
SRCREV  = "e4dc54f761ed7e659db08a46d9b6b4f0e0f57135"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/bt-kernel;"

S = "${WORKDIR}/vendor/qcom/opensource/bt-kernel"

TECHPACK_MODULE_OUT = "${WORKDIR}/bt-dlkm"
TECHPACK_MODULES = "pwr/btpower.ko"
TECHPACK_MAKE_ARGS = "CONFIG_MSM_BT_POWER=m"

inherit qti-techpack

RPROVIDES:${PN} += "kernel-module-btpower-${KERNEL_VERSION}"

FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/*"
