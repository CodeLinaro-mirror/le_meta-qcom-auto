SECTION = "kernel"

COMPATIBLE_MACHINE .= "|sa8797-ride-sx"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:${THISDIR}/files:"

DEPENDS += "skales-native xxd-native"

SRC_URI = "git://github.com/qualcomm-linux/kernel-topics.git;branch=refs/tags/nord-next-20260831;nobranch=1;protocol=https"
# Additional kernel configs.
SRC_URI:append = " \
    file://configs/bsp-additions.cfg \
    file://0001-qcom_scm_addon-Enable-qcom-scm-addons.patch \
    file://0002-qcom_scm-add-legacy-qtee-smcinvoke-helper.patch \
    file://0003-qcom-scm-allow-qseecom-on-nord.patch \
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
    file://files/0014-mailbox-qcom-cpucp-restructure-the-cpucp-mailbox-dri.patch \
    file://files/0015-scmi-support-for-scmi-vendor-protocol-and-log-driver.patch \
    file://files/0016-arm64-dts-qcom-nord-add-reg-names-to-cpucp-mbox-node.patch \
    file://0019-soc-qcom-socinfo-Report-SA8797P-as-QAM8797P.patch \
    file://0020-buffer-fix-NULL-pointer-deref-in-__bh_submit-for-vi.patch \
    file://files/0002-uapi-add-ccu_qup-header.patch \
    file://files/0003-uapi-add-smcinvoke-header.patch \
    file://files/ethernet/0001-PENDING-net-PCS-Add-Qualcomm-XPCS-driver.patch \
    file://files/ethernet/0002-PENDING-net-PCS-Add-10GBaseR-support-in-XPCS-driver.patch \
    file://files/ethernet/0003-PENDING-net-PCS-Fix-USXGMII-reset-sequence-to-poll-f.patch \
    file://files/ethernet/0004-PENDING-net-pcs-Add-5G-baseR-changes-in-PCS-driver.patch \
    file://files/ethernet/0005-PENDING-net-PCS-Call-PCS-link-up-sequence-for-10GBAS.patch \
    file://files/ethernet/0006-PENDING-net-PCS-Rework-and-expose-qcom_xpcs_destroy-.patch \
    file://files/ethernet/0007-PENDING-net-stmmac-Fix-USXGMII-link-up-sequence.patch \
    file://files/ethernet/0008-PENDING-net-pcs-Enable-PCS-Fusa-Shared-interrupt.patch \
    file://files/ethernet/0009-PENDING-net-PCS-supply-Tx-to-Rx-loopback-clock-for-H.patch \
    file://files/ethernet/0010-PENDING-net-stmmac-Remove-global-variable-for-tracki.patch \
    file://files/ethernet/0011-PENDING-net-PCS-Check-and-recover-pcs-link-after-ser.patch \
    file://files/ethernet/0012-PENDING-net-PCS-Loopback-TX-to-RX-clocks-when-XPCS-l.patch \
    file://files/ethernet/0013-PENDING-net-PCS-Check-for-AN-status-before-posting-l.patch \
    file://files/ethernet/0014-PENDING-net-pcs-Update-the-XPCS-sequence-and-add-ret.patch \
    file://files/ethernet/0015-PENDING-net-pcs-wait-for-AN-completion-in-USXGMII-re.patch \
    file://files/ethernet/0016-net-pcs-qcom-avoid-duplicate-init_module-cleanup_mod.patch \
    file://files/ethernet/stmmac-caller/0001-FROMLIST-net-stmmac-Add-basic-dw25gmac-support-in-st.patch \
    file://files/ethernet/stmmac-caller/0002-FROMLIST-net-stmmac-Add-snps_id-dev_id-to-struct-pla.patch \
    file://files/ethernet/stmmac-caller/0003-PENDING-net-stmmac-pass-stmmac_priv-in-some-callback.patch \
    file://files/ethernet/stmmac-caller/0004-PENDING-net-stmmac-Add-4k-Virtualization-support-to-.patch \
    file://files/ethernet/stmmac-caller/0005-PENDING-net-stmmac-Add-sa8797p-support-within-ethqos.patch \
    file://files/ethernet/stmmac-caller/0006-PENDING-net-stmmac-Add-IO-macro-configuration-for-US.patch \
    file://files/ethernet/stmmac-caller/0007-PENDING-net-stmmac-Add-Qcom-specific-changes-for-HDM.patch \
    file://files/ethernet/stmmac-caller/0008-PENDING-net-stmmac-Add-missing-25GMAC-link-capabilit.patch \
    file://files/ethernet/stmmac-caller/0009-PENDING-net-stmmac-Move-desc-cache-compute-within-HD.patch \
    file://files/ethernet/stmmac-caller/0010-PENDING-net-stmmac-Support-10GBaseR-iomacro-configur.patch \
    file://files/ethernet/stmmac-caller/0011-PENDING-net-stmmac-dwmac-qcom-ethqos-Configure-host-.patch \
    file://files/ethernet/stmmac-caller/0012-PENDING-net-stmmac-Enable-MMC-counters-for-25gmac.patch \
    file://files/ethernet/stmmac-caller/0013-PENDING-net-stmmac-Reset-few-of-the-IO-macro-registe.patch \
    file://files/ethernet/stmmac-caller/0014-PENDING-net-stmmac-change-mapping-between-VDMA-TC-PD.patch \
    file://files/ethernet/stmmac-caller/0015-PENDING-net-stmmac-Change-PDMA-mapping-to-resolve-SM.patch \
    file://files/ethernet/stmmac-caller/0016-PENDING-net-stmmac-enable-SGMII-loopback-for-DMA-res.patch \
    file://files/ethernet/stmmac-caller/0017-PENDING-net-stmmac-Add-NULL-check-for-match-data.patch \
    file://files/ethernet/stmmac-caller/0018-PENDING-net-stmmac-Disable-SPH.patch \
    file://files/ethernet/stmmac-caller/0019-PENDING-net-stmmac-Define-AXI-clock-rate-for-referen.patch \
    file://files/ethernet/stmmac-caller/0020-PENDING-net-stmmac-qcom-ethqos-configure-VDMA-and-PD.patch \
    file://files/ethernet/stmmac-caller/0021-net-stmmac-Add-multi-channel-support.patch \
    file://files/ethernet/stmmac-caller/0022-net-stmmac-mmc_core-drop-stale-mmc_rx_ipc_intr-field.patch \
    file://files/ethernet/stmmac-caller/0023-PENDING-net-stmmac-Use-new-Qualcomm-XPCS-driver.patch \
    file://files/ethernet/stmmac-caller/0024-PENDING-net-stmmac-Use-pcs_init-and-pcs_exit-functio.patch \
    file://files/ethernet/stmmac-caller/0025-PENDING-net-stmmac-Use-generic-PCS-field-and-move-pc.patch \
    file://files/ethernet/stmmac-caller/0026-eth_scmi_patch.patch \
    file://files/ethernet/stmmac-caller/0027-DEBUG-net-stmmac-dwmac-qcom-ethqos-add-ETHDBG-bring-.patch \
    file://files/ethernet/stmmac-caller/0028-net-pcs-qcom-build-as-its-own-module-so-the-driver-s.patch \
    file://files/ethernet/stmmac-caller/0029-enable-ethqos-driver.patch \
    file://files/ethernet/stmmac-caller/0030-hwif-ptp-fix.patch \
    file://files/ethernet/dts/0001-arm64-dts-qcom-nord-enable-ethernet0-and-xpcs0.patch \
    file://files/ethernet/dts/0002-arm64-dts-qcom-sa8x97p-add-SCMI-SGMII-SerDes-PHY-nod.patch \
    file://files/ethernet/0017-net-stmmac-dwmac-qcom-ethqos-switch-to-SCMI-PHY-driv.patch \
    file://files/ethernet/0018-phy-qcom-add-SCMI-managed-SGMII-SerDes-PHY-driver.patch \
"

# Pin to a specific commit once you know it
SRCREV = "da28394e5c5b0a590086385484d3ea2453ec825e"

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

do_deploy:append() {
    install -m 0644 ${B}/vmlinux ${DEPLOYDIR}
    install -d ${DEPLOYDIR}/build-artifacts/dtb
    for dtb in ${KERNEL_MACHINE_DTB}; do
        if [ -f ${B}/$dtb ]; then
            install -m 0644 ${B}/$dtb ${DEPLOYDIR}/build-artifacts/dtb/
        fi
    done
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
