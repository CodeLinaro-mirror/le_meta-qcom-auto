SECTION = "kernel"

COMPATIBLE_MACHINE .= "|sa8797-ride-sx"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS += "skales-native xxd-native"

SRC_URI = "git://github.com/qualcomm-linux/kernel-topics.git;branch=refs/tags/nord-next-20260831;nobranch=1;protocol=https"
# Additional kernel configs.
SRC_URI:append = " \
    file://configs/bsp-additions.cfg \
    file://files/0001-qcom_scm_addon-Enable-qcom-scm-addons.patch \
    file://files/0002-pmdomain-core-Skip-sync_state-when-pd_ignore_unused-.patch \
    file://files/0001-QCLINUX-iommu-arm-smmu-protect-the-dereference-of-ad.patch \
    file://files/0002-arm-smmu-add-qcom-secure-vmid.patch \
    file://files/0003-arm-smmu-qcom-enable-secure-pagetable-alloc.patch \
    file://files/0004-firmware-qcom_scm-add-assign_mem_regions.patch \
    file://files/usb/0001-usb-dwc3-qcom-Ensure-VBUS_VALID-is-set-after-resume.patch \
    file://files/usb/0002-usb-dwc3-qcom-Ensure-PIPE_UTMI_CLK_SEL-is-proper-aft.patch \
    file://files/usb/0003-usb-host-xhci-plat-Add-support-for-XHCI_RESET_ON_RES.patch \
    file://files/usb/0004-usb-dwc3-host-Set-XHCI_RESET_ON_RESUME-for-xhci.patch  \
    file://files/usb/0005-phy-qcom-qmp-usb-Call-qmp_usb_remove-during-driver-r.patch \
    file://files/usb/0006-phy-qcom-qmp-usb-Add-support-for-SA8255P.patch  \
    file://files/usb/0007-usb-dwc3-qcom-Add-support-for-sa8255p-for-qcom-usb.patch  \
    file://files/usb/0008-phy-qcom-snps-femto-v2-Call-remove-and-add-support-f.patch \
    file://files/usb/0009-usb-dwc3-qcom-Add-support-for-sa8797p-for-qcom-usb.patch  \
    file://files/usb/0010-phy-qcom-phy-qcom-snps-eusb2-Add-support-for-SA8797P.patch \
    file://files/usb/0011-usb-phy-qmp-combo-Add-scmi-related-changes-for-SA879.patch \
    file://files/usb/0012-PENDING-phy-core-Introduce-PHY-type-and-helper-API.patch  \
    file://files/usb/0013-PENDING-phy-snps-eusb2-Set-PHY-type-for-Synopsys-eUS.patch \
    file://files/usb/0014-PENDING-usb-dwc3-Program-eUSB2-UTMI-opmode-in-host-m.patch \
    file://files/usb/0015-PENDING-usb-Fix-eUSB2-PHY-compliance-issues-CV9-TD-9.patch \
    file://files/usb/0016-PENDING-phy-ptn3222-Add-support-to-parse-the-param-f.patch \
    file://files/usb/0017-PENDING-phy-qcom-snps-femto-v2-Add-minimal-EUD-detec.patch \
"

# Pin to a specific commit once you know it
# tag: early/hwe/nord-next tip as of 2026-08-17 (previous pin fell off history after upstream branch rewrite)
SRCREV = "c0bd3ab23cd9fe3f6938bf9373572a4d3ada7c19"

KERNEL_CONFIG_FRAGMENTS = "\
    arch/arm64/configs/hardening.config \
    arch/arm64/configs/virt.config \
"


KBUILD_CONFIG_EXTRA:remove:aarch64 = "${S}/arch/arm64/configs/prune.config"
KBUILD_CONFIG_EXTRA:remove:aarch64 = "${S}/arch/arm64/configs/qcom.config"

# Use the kernel's own defconfig as the base
KERNEL_DEFCONFIG = "defconfig"

KBUILD_CONFIG_EXTRA = "${@bb.utils.contains('DISTRO_FEATURES', 'hardened', '${S}/kernel/configs/hardening.config', '', d)}"

KERNEL_MACHINE_DTB = "\
    arch/arm64/boot/dts/qcom/nord-ride.dtb \
"

# Emit a /__symbols__ node in the base DTBs so the OOT dtbo overlay
# (nord-ride_overlay.dtbo) can be applied with fdtoverlay at merge time.
do_compile:prepend() {
    export DTC_FLAGS="-@"
}

do_configure:prepend() {
    # Use a copy of the 'defconfig' from the actual repo to merge fragments
    cp ${S}/arch/${ARCH}/configs/${KBUILD_DEFCONFIG} ${B}/.config

    # Merge fragment for QCOM value add features
    ${S}/scripts/kconfig/merge_config.sh -m -O ${B} ${B}/.config ${KBUILD_CONFIG_EXTRA} ${@" ".join(find_cfgs(d))}
}

# NOTE: do NOT override do_packagedata. The oe-core do_packagedata is a Python
# function that syncs the split kernel-module package metadata (1789 modules)
# from the recipe workdir into the global PKGDATA_DIR. Overriding it with an
# empty stub breaks do_package_write_ipk (no kernel-module-*.ipk get emitted)
# and leaves the kernel-modules aggregate pkgdata missing — which in turn breaks
# early-ramdisk module loading and core-image-platform license_create_manifest.
# QLI-2.0-AUTO does not override this task; matching it produces the 1789
# kernel-module IPKs the early ramdisk needs.

# Remove SDK task dependency on kernel - not needed for image build
do_populate_sdk[noexec] = "1"
