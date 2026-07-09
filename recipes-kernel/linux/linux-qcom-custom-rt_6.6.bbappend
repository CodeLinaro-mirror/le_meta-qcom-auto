FILESEXTRAPATHS:prepend = "${SRC_DIR_ROOT}/layers/meta-qti-automotive/recipes-kernel/linux/files:"

S = "${UNPACKDIR}/kernel/kernel_platform/kernel"

KERNEL_DEVICETREE:remove = "${KERNEL_DEVICETREE:pn-linux-qcom-custom}"
