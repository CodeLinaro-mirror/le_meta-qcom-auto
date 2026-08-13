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

#sign boot img
do_sign_boot_img () {
    imgname="${DEPLOY_DIR_IMAGE}/${BOOTIMAGE_TARGET}"
    if ${@bb.utils.contains('DISTRO_FEATURES', 'qti-avb', 'true', 'false', d)}; then
        avb_sign_boot_image ${imgname}
        if [ -f "${DEPLOY_DIR_IMAGE_LAGVM}/${PRODUCT}-lagvm-boot.img" ]; then
            imgname="${DEPLOY_DIR_IMAGE_LAGVM}/${PRODUCT}-lagvm-boot.img"
            avb_sign_boot_image ${imgname}
        fi
        if [ -f "${DEPLOY_DIR_IMAGE_PVM}/${PRODUCT}-pvm-boot.img" ]; then
            imgname="${DEPLOY_DIR_IMAGE_PVM}/${PRODUCT}-pvm-boot.img"
            avb_sign_boot_image ${imgname}
        fi
    fi
}

avb_sign_boot_image() {
    img="$1"
    boot_partition_size=$(avbtool calc_min_partition_size \
                              --image ${img} \
                              --partition_name boot \
                              --hash_algorithm sha256 \
                              --no_hashtree)
    avbtool add_hash_footer  \
        --image ${img}  \
        --partition_size ${boot_partition_size}  \
        --partition_name boot \
        --algorithm SHA256_RSA4096 \
        --key ${STAGING_DIR_NATIVE}${sysconfdir}/signing_tools/sigkeys/testkey_rsa4096.pem \
        --rollback_index 0
    if ${@bb.utils.contains('MACHINE_FEATURES', 'dt-overlay', 'true', 'false', d)}; then
       avbtool add_hash_footer  \
              --image ${DEPLOY_DIR_IMAGE}/${PRODUCT}-dtbo.img  \
              --partition_size 0x00200000 \
              --partition_name dtbo \
              --algorithm SHA256_RSA4096 \
              --key ${STAGING_DIR_NATIVE}${sysconfdir}/signing_tools/sigkeys/testkey_rsa4096.pem \
              --rollback_index 0
    fi
}

#Sign boot image after generation
do_sign_boot_img[dirs] = "${DEPLOY_DIR_IMAGE}"
do_sign_boot_img[depends] += "sectools-native:do_populate_sysroot avbtool-native:do_populate_sysroot"

addtask do_sign_boot_img after do_makeboot before do_build

do_rootfs[noexec] = "1"
do_image[noexec] = "1"
do_image_complete[noexec] = "1"
do_create_rootfs_spdx[noexec] = "1"
do_create_image_sbom_spdx[noexec] = "1"
do_create_image_spdx[noexec] = "1"
