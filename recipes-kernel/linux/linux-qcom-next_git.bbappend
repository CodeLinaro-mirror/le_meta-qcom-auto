inherit qimage
SECTION = "kernel"

COMPATIBLE_MACHINE .= "|sa8797-ride-sx"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS += "skales-native xxd-native"

SRC_URI = "git://github.com/qualcomm-linux/kernel-topics.git;branch=early/hwe/nord;protocol=https"
# Additional kernel configs.
SRC_URI:append = " \
    file://configs/bsp-additions.cfg \
    file://files/0001-qcom_scm_addon-Enable-qcom-scm-addons.patch \
"

# Pin to a specific commit once you know it
SRCREV = "96a62f5aca96193f402373d0269f7c877ff1876a"

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
    arch/arm64/boot/dts/qcom/nord-evk.dtb \
    arch/arm64/boot/dts/qcom/nord-ride.dtb \
"

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
