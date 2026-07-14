SUMMARY = "QCOM Kernel Boot image"
DESCRIPTION = "Build QCOM kernel boot image"
LICENSE = "BSD-3-Clause-Clear"

COMPATIBLE_MACHINE = "sa8797-ride-sx"

# oot-dtbo source (safelinux-system-cfg/devicetree) absent — not in DEPENDS
DEPENDS += "mkbootimg-native openssl-native  python3-native virtual/kernel early-ramdisk-init"

IMAGE_FEATURES:remove = "ssh-server-openssh"
IMAGE_CLASSES:remove = "qimage"
EXTRA_IMAGE_FEATURES = ""

inherit image

# BOOT_RAMDISK_IMG = "initramfs-rootfs-image-${PRODUCT}.cpio.gz"
# BOOT_RAMDISK_IMG ?= "${@bb.utils.contains('MACHINE_FEATURES', 'early-ramdisk-init', 'early-ramdisk-image-${PRODUCT}.cpio.lz4', '/dev/null', d)}"

BOOT_RAMDISK_IMG = "early-ramdisk-image-${PRODUCT}.rootfs.cpio.gz"

# Use overlay-merged DTB from oot-dtbo (build-artifacts/dtb/) if available,
# otherwise fall back to base kernel DTBs
do_make_dtb() {
    install -d ${DEPLOY_DIR_IMAGE}/dtbs
    if [ -f "${DEPLOY_DIR_IMAGE}/build-artifacts/dtb/dtb.img" ]; then
        # oot-dtbo:do_deploy wrote build-artifacts/dtb/dtb.img — use it
        cat ${DEPLOY_DIR_IMAGE}/build-artifacts/dtb/dtb.img \
            > ${DEPLOY_DIR_IMAGE}/dtbs/dtb.img
        bbnote "Created dtbs/dtb.img from oot-dtbo overlay (build-artifacts/dtb/dtb.img)"
    elif ls ${DEPLOY_DIR_IMAGE}/build-artifacts/dtb/*overlay*.dtb 2>/dev/null | grep -q nord-ride; then
        # fallback: overlay .dtb present but dtb.img not pre-built
        cat ${DEPLOY_DIR_IMAGE}/build-artifacts/dtb/nord-ride_overlay.dtb \
            > ${DEPLOY_DIR_IMAGE}/dtbs/dtb.img
        bbnote "Created dtbs/dtb.img from overlay-merged DTB (oot-dtbo)"
    else
        # Fallback: use only the nord-ride base DTB.
        # NOTE: do NOT "cat *.dtb" here — the deploy dir also contains
        # nord-evk.dtb (and versioned copies). Concatenating them all makes
        # the bootloader pick nord-evk (wrong SCMI topology: single mailbox
        # pdp_scmi instance vs the SMC scmi-common set), which floods the
        # console with "Message for <n> type 0 is not expected!" and blocks
        # login. Match QLI-2.0-AUTO: ship a single nord-ride FDT.
        cat ${DEPLOY_DIR_IMAGE}/nord-ride.dtb > ${DEPLOY_DIR_IMAGE}/dtbs/dtb.img
        bbnote "Created dtbs/dtb.img from nord-ride base DTB (no oot-dtbo overlay)"
    fi
}
do_make_dtb[depends] += "virtual/kernel:do_deploy oot-dtbo:do_deploy"
do_make_dtb[dirs] = "${DEPLOY_DIR_IMAGE}"
addtask do_make_dtb after do_image before do_makeboot

do_makeboot () {
    if [ "${KERNEL_IMAGE_HEADER_VERSION}" = "2" ]; then
        # Make bootimage
        ${STAGING_BINDIR_NATIVE}/scripts/mkbootimg.py --header_version ${KERNEL_IMAGE_HEADER_VERSION} \
        --kernel  ${DEPLOY_DIR_IMAGE}/Image \
        --dtb  ${DEPLOY_DIR_IMAGE}/dtbs/dtb.img \
        --ramdisk ${DEPLOY_DIR_IMAGE}/${BOOT_RAMDISK_IMG} \
        --pagesize ${PAGE_SIZE} \
        --base ${KERNEL_BASE} \
        --ramdisk_offset 0x0 \
        --cmdline "${KERNEL_CMD_PARAMS}" \
        --output  ${DEPLOY_DIR_IMAGE}/${PRODUCT}-boot-${KERNEL_VERSION}.img
    else
        echo "Unknown Boot Image Header Version"
        return 1
    fi
    cp ${DEPLOY_DIR_IMAGE}/${PRODUCT}-boot-${KERNEL_VERSION}.img ${DEPLOY_DIR_IMAGE}/${PRODUCT}-boot.img
}

do_makeboot[dirs] = "${DEPLOY_DIR_IMAGE}"
# Make sure native tools and kernel artifacts are ready.
# early-ramdisk-image must be built and deployed first so --ramdisk points at a
# real file. No circular dep results: early-ramdisk-image.bb sets
# EXTRA_IMAGEDEPENDS="" so it never pulls qcom-image-boot back in.
do_makeboot[depends] += "virtual/kernel:do_deploy mkbootimg-native:do_populate_sysroot early-ramdisk-image:do_image_complete"

addtask do_makeboot before do_build after do_make_dtb

# EXTRA_IMAGEDEPENDS (gen5.inc) references qcom-image-boot:do_sign_boot_img.
# The full production recipe (recipes-products/) that provides real signing is
# BBMASK'd in local.conf. This simplified recipe provides a pass-through task so
# the dependency resolves; NO signing is performed — the unsigned boot image is
# used as-is. Remove once the production recipe is un-masked.
do_sign_boot_img () {
    bbnote "qcom-image-boot (simplified): no signing performed; using unsigned ${PRODUCT}-boot.img"
    :
}
do_sign_boot_img[dirs] = "${DEPLOY_DIR_IMAGE}"
addtask do_sign_boot_img after do_makeboot before do_build

do_rootfs[noexec] = "1"
do_image[noexec] = "1"
do_image_complete[noexec] = "1"
do_create_rootfs_spdx[noexec] = "1"
do_create_image_sbom_spdx[noexec] = "1"
do_create_image_spdx[noexec] = "1"
