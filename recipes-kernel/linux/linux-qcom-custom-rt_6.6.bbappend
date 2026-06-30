FILESEXTRAPATHS:prepend := "${THISDIR}/linux/files:"

S = "${WORKDIR}/kernel/kernel_platform/kernel"

KERNEL_DEVICETREE:remove = "${KERNEL_DEVICETREE:pn-linux-qcom-custom}"
