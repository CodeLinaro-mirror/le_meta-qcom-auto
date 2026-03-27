FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "\
    file://generic.cfg \
    file://earlyboot.cfg \
    ${@bb.utils.contains('DISTRO_FEATURES', 'selinux', 'file://selinux.cfg', '', d)} \
    ${@bb.utils.contains_any('VARIANT', 'perf user', '', 'file://devmem.cfg', d)} \
    ${@bb.utils.contains_any('VARIANT', 'perf user', 'file://perf.cfg', '', d)} \
    file://0001-QCLINUX-vfio-Disable-iommu_group_claim_dma_owner-tem.patch \
    file://0001-PENDING-pinctrl-qcom-Enable-read-after-write-feature.patch \
    file://qup/0001-UPSTREAM-soc-qcom-geni-se-Add-M_TX_FIFO_NOT_EMPTY-bi.patch \
    file://qup/0002-UPSTREAM-soc-qcom-geni-se-add-GP_LENGTH-IRQ_EN_SET-I.patch \
    file://qup/0003-UPSTREAM-kfifo-introduce-and-use-kfifo_skip_count.patch \
    file://qup/0004-UPSTREAM-kfifo-add-kfifo_out_linear-_ptr.patch \
    file://qup/0007-UPSTREAM-serial-qcom-geni-Don-t-cancel-abort-if-we-c.patch \
    file://qup/0008-BACKPORT-tty-serial-switch-from-circ_buf-to-kfifo.patch \
    file://qup/0009-UPSTREAM-serial-meson-qcom-don-t-advance-the-kfifo-t.patch \
    file://qup/0010-UPSTREAM-serial-qcom-geni-fix-soft-lockup-on-sw-flow.patch \
    file://qup/0011-UPSTREAM-serial-qcom-geni-fix-hard-lockup-on-buffer-.patch \
    file://qup/0012-UPSTREAM-serial-qcom-geni-do-not-kill-the-machine-on.patch \
    file://qup/0014-UPSTREAM-serial-qcom-geni-fix-arg-types-for-qcom_gen.patch \
    file://qup/0015-UPSTREAM-serial-qcom-geni-introduce-qcom_geni_serial.patch \
    file://qup/0016-UPSTREAM-serial-qcom-geni-fix-console-corruption.patch \
    file://qup/0017-UPSTREAM-serial-qcom-geni-disable-interrupts-during-.patch \
    file://qup/0018-UPSTREAM-serial-qcom-geni-fix-polled-console-corrupt.patch \
    file://qup/0019-UPSTREAM-serial-qcom-geni-fix-shutdown-race.patch \
    file://qup/0001-PENDING-soc-qcom-geni-se-Enable-QUPs-on-SA8255p-Qual.patch \
    file://qup/0002-PENDING-serial-qcom-geni-Enable-Serial-on-SA8255p-pl.patch \
    file://qup/0003-PENDING-i2c-qcom-geni-Enable-I2C-on-SA8255p-Qualcomm.patch \
    file://qup/0004-PENDING-spi-geni-qcom-Enable-SPI-on-SA8255p-Qualcomm.patch \
    file://qup/0005-PENDING-spi-geni-qcom-Enable-SPI-GSI-mode-for-SA8255.patch \
    file://qup/0009-PENDING-i2c-qcom-geni-cleanup-in-probe-function.patch \
    file://qup/0010-PENDING-i2c-qcom-geni-Add-support-for-S2R-feature.patch \
    file://qup/0011-PENDING-serial-qcom-geni-Add-support-for-S2R-feature.patch \
    file://qup/0012-PENDING-spi-spi-geni-qcom-Add-support-for-S2R-featur.patch \
    file://qup/0013-PENDING-spi-spi-geni-qcom-set-lowest-OPP-during-susp.patch \
    file://qup/0014-PENDING-serial-qcom-geni-set-lowest-OPP-during-suspe.patch \
    file://qup/0015-PENDING-spi-qcom-geni-Allow-SPI-mode-reconfiguration.patch \
    file://qup/0016-PENDING-serial-Ensure-DMA-buffer-is-synced-before-CP.patch \
    file://qup/0017-PENDING-dmaengine-qcom-gpi-Handle-GPII-channel-recon.patch \
    file://qup/0017-serial-qcom_geni-Fix-TX-interrupt-setup-and-spurious.patch \
    file://qup/0018-serial-qcom-geni-set-lowest-OPP-during-console-suspe.patch \
    file://qup/0043-spi-spi-geni-qcom-Add-target-mode-abort-support.patch \
    file://qup/0018-i2c-qcom-geni-Remove-redundant-runtime_resume-fallba.patch \
    file://0007-PENDING-scsi-ufs-qcom-Enable-sa8255p-platform.patch \
    file://0001-PENDING-ufs-ufs-qcom-Skip-hibern8-FSM-state-check-fo.patch \
    file://0001-PENDING-ufs-ufs-qcom-Skip-UFS-link-startup-in-HLOS.patch \
    file://0004-PENDING-ufs-ufs-qcom-Remove-reset-GPIO-Dependency-an.patch \
    file://0005-PENDING-ufs-ufs-qcom-Don-t-skip-link-startup-for-QB-.patch \
    ${@bb.utils.contains('DISTRO_FEATURES', 'qti-rumi', 'file://0001-Revert-PENDING-ufs-ufs-qcom-Skip-UFS-link-startup-in.patch', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'qti-rumi', 'file://0001-QCLINUX-ufs-UFS-bringup-on-Seca-RUMI.patch', '', d)} \
    file://0001-PENDING-firmware-extend-vmid-support-to-128.patch \
    file://usb/0001-PENDING-usb-dwc3-qcom-Ensure-VBUS_VALID-is-set-after.patch \
    file://usb/0002-PENDING-usb-dwc3-qcom-Ensure-PIPE_UTMI_CLK_SEL-is-pr.patch \
    file://usb/0003-PENDING-usb-dwc3-drd-expose-role-switch-control-to-u.patch \
    file://usb/0004-PENDING-usb-dwc3-qcom-Trivial-code-cleanup.patch \
    file://usb/0005-PENDING-usb-dwc3-Enable-role-switch-control-from-use.patch \
    file://usb/0006-PENDING-usb-host-xhci-plat-Trivial-code-cleanup.patch \
    file://usb/0007-PENDING-usb-host-xhci-plat-Trivial-code-cleanup.patch \
    file://usb/0008-PENDING-usb-host-xhci-plat-Add-support-for-XHCI_RESE.patch \
    file://usb/0009-PENDING-usb-dwc3-host-Set-XHCI_RESET_ON_RESUME-for-x.patch \
    file://usb/0010-PENDING-phy-qcom-qmp-usb-Call-qmp_usb_remove-during-.patch \
    file://usb/0011-PENDING-phy-qcom-qmp-usb-Add-support-for-SA8255P.patch \
    file://usb/0012-PENDING-usb-dwc3-qcom-Add-support-for-sa8255p-for-qc.patch \
    file://usb/0013-PENDING-phy-qcom-snps-femto-v2-Call-qcom_snps_hsphy_.patch \
    file://usb/0014-PENDING-phy-qcom-snps-femto-v2-Add-support-for-SA825.patch \
    file://pcie/0001-PCIe_RC_Add-Qualcomm-PCIe-ECAM-root-complex-driv.patch \
    file://pcie/0004-MHI_RC_bus-mhi-host-pci_generic-Disable-auto-suspen.patch \
    file://pcie/0006-PCIe_EP_qcom-ep-Add-support-for-SCMI-based-PCIe-EP_Lemans.patch \
    file://0001-FROMLIST-of-of_reserved_mem-Increase-limit-for-reser.patch \
    file://0013-net-stmmac-dwmac-qcom-ethqos-Enable-SCMI-ETH.patch \
    file://0014-PENDING-qcom-Add-sa7255p-compatibles-for-core-driver.patch \
    file://usb/0015-PENDING-qcom-Add-sa7255p-compatibles-for-usb-drivers.patch \
    file://0016-PENDING-ice-Enable-ICE-on-SA8255p-Qualcomm-platforms.patch \
    file://scm_adci/0001-QCLINUX-arm64-dts-qcom-sa8255p-Modify-correct-dt-nam.patch \
    file://scm_adci/0002-QCLINUX-arm64-dts-qcom-sa8775p-Modify-correct-dt-nam.patch \
    file://scm_adci/0003-QCLINUX-arm64-dts-qcom-sa7255p-Modify-correct-dt-nam.patch \
    file://scm_adci/0004-BACKPORT-FROMLIST-firmware-qcom-scm-Support-multiple.patch \
    file://scm_adci/0005-PENDING-firmware-qcom-scm-Add-support-for-WAITQ_WAKE.patch \
    file://scm_adci/0006-PENDING-firmware-qcom-scm-Add-new-lock-and-selective.patch \
    file://scm_adci/0007-QCLINUX-arm64-dts-qcom-qcs9100-Modify-correct-dt-nam.patch \
    file://0019-net-phy-AQR-phy-10M-fix.patch \
    file://0001-mm-memblock-enable-memory-hotplug.patch \
    file://0001-PENDING-defer-no-map-memory-init-process.patch \
    file://0001-PENDING-kallsyms-Export-kallsyms_lookup_name.patch \
"

SRC_URI:append:gen5 = " \
    file://sa8797p-generic.cfg \
    file://usb/0016-usb-dwc3-qcom-Add-support-for-sa8775p-for-qcom-usb.patch \
    file://usb/0017-usb-phy-Add-snapshot-of-USB-PHY-EMU-driver.patch \
    file://0008-Window-Watchdog-support-for-Sa8797.patch \
    file://usb/0018-phy-qcom-phy-qcom-snps-eusb2-Add-support-for-SA8797P.patch \
    file://usb/0019-usb-phy-qmp-combo-Add-scmi-related-changes-for-SA879.patch \
    file://usb/0020-PENDING-phy-core-Introduce-PHY-type-and-helper-API.patch \
    file://usb/0021-PENDING-phy-snps-eusb2-Set-PHY-type-for-Synopsys-eUS.patch \
    file://usb/0022-PENDING-usb-dwc3-Program-eUSB2-UTMI-opmode-in-host-m.patch \
    file://usb/0023-phy-phy-qcom-snps-eusb2-Add-register-sequence-to-fix.patch \
    file://usb/0024-dwc3-gadget-Fix-compliance-TD-9.23-compliance-issue.patch \
    file://usb/0025-PENDING-usb-phy-qmp-combo-Update-PHY-init-sequence.patch \
    file://usb/0026-PENDING-phy-ptn3222-Add-support-to-parse-the-param-f.patch \
    file://0014-mailbox-qcom-cpucp-restructure-the-cpucp-mailbox-dri.patch \
    file://0015-scmi-support-for-scmi-vendor-protocol-and-log-driver.patch \
    file://0016-QCLINUX-MPAM-Snapshot-of-QCOM-MPAM-Driver.patch \
    file://0017-QCLINUX-MPAM-Snapshot-of-QCOM-SLC-MPAM-Driver.patch \
    file://0018-QCLINUX-MPAM-Add-CPU-map-partid-for-CPU-MPAM-Driver.patch \
    file://qup/0006-PENDING-soc-qcom-geni-se-Add-compatible-field-for-SA.patch \
    file://qup/0007-PENDING-i2c-i2c-qcom-geni-Add-compatible-field-for-S.patch \
    file://qup/0008-PENDING-spi-spi-geni-qcom-Add-compatible-field-for-S.patch \
    file://qup/0013-PENDING-dmaengine-gpi-Add-new-register-offset-to-sup.patch \
    file://qup/0014-PENDING-i2c-i2c-qcom-geni-Add-data-transfer-support-.patch \
    file://qup/0015-PENDING-dmaengine-gpi-Add-new-API-to-enhance-CCU-QUP.patch \
    file://qup/0016-PENDING-i2c-i2c-qcom-geni-Add-APIs-to-support-CCU-QU.patch \
    file://qup/0017-PENDING-soc-qcom-ccu-qup-Add-CCU-QUP-platform-driver.patch \
    file://qup/0018-PENDING-i2c-i2c-qcom-geni-Add-async-write-support.patch \
    file://qup/0019-PENDING-soc-qcom-ccu-qup-Add-read-and-poll-API-suppo.patch \
    file://qup/0020-PENDING-i2c-i2c-qcom-geni-Update-counter-setting-for.patch \
    file://qup/0021-meta-qti-auto-kernel-Fix-cyclic-depedancy-between-CC.patch \
    file://qup/0022-meta-qti-auto-kernel-Fix-double-unmap-issue-for-DMA-.patch \
    file://qup/0023-i2c-qcom-geni-Fix-I2C-multi-message-write-and-DMA-bu.patch \
    file://qup/0024-i2c-Enable-multi-random-write-feature.patch \
    file://qup/0025-i2c-i2c-qcom-geni-Fix-NULL-pointer-dereference-issue.patch \
    file://qup/0026-dmaengine-qcom-gpi-Add-I2C-bus-clear-and-bus-stop-su.patch \
    file://qup/0027-ccu-Add-support-to-load-the-CCU-QUP-FW.patch \
    file://qup/0028-i2c-Add-support-for-NOP-Frame.patch \
    file://qup/0029-i2c-Fix-the-I2c-probe-issue.patch \
    file://qup/0030-i2c-Add-validation-checks-and-fix-inter-frame-delay-.patch \
    file://qup/0031-dmaengine-qcom-gpi-Add-premature-cancel-support-for-.patch \
    file://qup/0032-PENDING-i2c-qcom-geni-Add-GSI-S2R-support-for-CCU-QU.patch \
    file://pcie/0003-PCIe_RC_Patch-PCIe-Fix-Safety-Features-for-sa8797p.patch \
    file://pcie/0005-MHI_RC_bus-mhi-host-pci_generic-Add-supoprt-for-SA8797P.patch \
    file://pcie/0007-PCIe_EP_qcom-ep-Add-support-for-SCMI-based-PCIe-EP-for-Nords.patch \
    file://pcie/0008-MHI_EP_dmaengine-dw-edma-Add-correct-offsets-for-HDMA-RD-WR.patch \
    file://qup/0033-meta-qti-auto-kernel-ccu-Add-Ftrace-support-for-CCU.patch \
    file://qup/0034-i2c-qcom-geni-Skip-TX-DMA-TRE-for-I2C-read-operation.patch \
    file://qup/0035-i2c-qcom-geni-Add-asynchronous-read-support-for-CCU-.patch \
    file://qup/0036-ccu-Optimize-interrupt-handling-and-improve-GSI-reso.patch \
    file://qup/0037-ccu-Refactor-response-list-handling-to-prevent-corru.patch \
    file://qup/0038-ccu-Add-support-for-passing-error-notification-event.patch \
    file://qup/0039-ccu-Initialize-CCU-QUP-core-during-firmware-load-and.patch \
    file://qup/0040-i2c-Prevent-kernel-panic-by-handling-ERR_PTR-from-dm.patch \
    file://qup/0041-i2c-Update-clock-cycle-formula-per-latest-HPG-specif.patch \
    file://qup/0042-ccu-WARN_ON-once-at-CCU_RETRY_CNT-during-GSI-pdev-lo.patch \
    file://qup/0043-i2c-Update-the-I2C-clock-counter-values-for-37.5MHz-.patch \
    file://qup/0044-ccu-Add-support-to-configure-trigger-type-in-GSI-ch-.patch \
    ${@bb.utils.contains('DISTRO_FEATURES', 'qti-rumi', 'file://rumi.cfg', '', d)} \
"

SRC_URI:remove:gen5 = " \
    file://earlyboot.cfg \
    file://0019-net-phy-AQR-phy-10M-fix.patch \
    file://pcie/0006-PCIe_EP_qcom-ep-Add-support-for-SCMI-based-PCIe-EP_Lemans.patch \
"

SRC_URI:append:auto-fts = " \
    file://auto-fts.cfg \
    file://pcie/0002-PCIe_RC_Add-pcie-module-dependency.patch \
"

KERNEL_CONFIG_FRAGMENTS:append:gen5 = " ${WORKDIR}/sa8797p-generic.cfg"
KERNEL_CONFIG_FRAGMENTS:append = " ${WORKDIR}/generic.cfg"
KERNEL_CONFIG_FRAGMENTS:append:sa8775 = " ${WORKDIR}/earlyboot.cfg"
KERNEL_CONFIG_FRAGMENTS:append:sa7255 = " ${WORKDIR}/earlyboot.cfg"
KERNEL_CONFIG_FRAGMENTS:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'selinux', '${WORKDIR}/selinux.cfg', '', d)}"
KERNEL_CONFIG_FRAGMENTS:append = " ${@bb.utils.contains_any('VARIANT', 'perf user', '', '${WORKDIR}/devmem.cfg', d)}"
KERNEL_CONFIG_FRAGMENTS:append:auto-fts = " ${WORKDIR}/auto-fts.cfg"
KERNEL_CONFIG_FRAGMENTS:append:gen5 = " ${@bb.utils.contains('DISTRO_FEATURES', 'qti-rumi', '${WORKDIR}/rumi.cfg', '', d)}"
KERNEL_CONFIG_FRAGMENTS:append = " ${@bb.utils.contains_any('VARIANT', 'perf user', '${WORKDIR}/perf.cfg', '', d)}"
