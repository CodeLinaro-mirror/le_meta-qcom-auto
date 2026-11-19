FILESEXTRAPATHS:prepend = "${SRC_DIR_ROOT}/layers/meta-qcom-auto/recipes-kernel/linux/files:"

S = "${WORKDIR}/kernel/kernel_platform/kernel"

KERNEL_DEVICETREE:remove = "${KERNEL_DEVICETREE:pn-linux-qcom-custom}"

RT_PATCH_6_6_142 = "https://cdn.kernel.org/pub/linux/kernel/projects/rt/6.6/older/patch-6.6.142-rt75.patch.gz;md5sum=c106fbc5651db93bc840d8b7ee3ce7fe"
RT_PATCH_6_6_144 = "https://cdn.kernel.org/pub/linux/kernel/projects/rt/6.6/older/patch-6.6.144-rt76.patch.gz;md5sum=74a2242486d3bba93b71b98d5e124820"

SRC_URI:remove = "${RT_PATCH_6_6_142}"
SRC_URI:append = " ${RT_PATCH_6_6_144}"
