SUMMARY = "QCOM Kernel Boot image"
DESCRIPTION = "Build QCOM kernel boot image"
LICENSE = "BSD-3-Clause-Clear"

COMPATIBLE_MACHINE = "sa8797-ride-sx"

# oot-dtbo source (safelinux-system-cfg/devicetree) absent — not in DEPENDS
DEPENDS += "mkbootimg-native openssl-native  python3-native virtual/kernel early-ramdisk-init dtc-native"

IMAGE_FEATURES:remove = "ssh-server-openssh"
IMAGE_CLASSES:remove = "qimage"
EXTRA_IMAGE_FEATURES = ""

inherit image qcom-dtb-merge

# BOOT_RAMDISK_IMG = "initramfs-rootfs-image-${PRODUCT}.cpio.gz"
# BOOT_RAMDISK_IMG ?= "${@bb.utils.contains('MACHINE_FEATURES', 'early-ramdisk-init', 'early-ramdisk-image-${PRODUCT}.cpio.lz4', '/dev/null', d)}"

BOOT_RAMDISK_IMG = "early-ramdisk-image-${PRODUCT}.rootfs.cpio.gz"

# NORD_COMMON_OVERLAYS : applied to every variant (functional L1 + mm-vfio)
# NORD_VARIANT_OVERLAYS: one final board per entry ("" token = plain, no variant overlay)
# NORD_DDR_OVERLAYS    : DDR size overlays (L2), board-id-tagged by update_dtb_ddr_id
NORD_BASE_DTB ?= "nord-ride.dtb"
NORD_COMMON_OVERLAYS ?= "nord-ride_overlay.dtbo sa8797p-mm-vfio.dtbo sa8797p_audiolite_common.dtbo"
NORD_VARIANT_OVERLAYS ?= "nord-ride-safe.dtbo nord-ride-non-safe-ivi.dtbo nord-ride-pcie-ep.dtbo"
NORD_DDR_OVERLAYS ?= "nord-ddr-32gb_overlay.dtbo nord-ddr-48gb_overlay.dtbo nord-ddr-64gb_overlay.dtbo"
do_make_dtb() {
    local stage="${UNPACKDIR}/nord-dtb-merge"
    rm -rf "${stage}"
    install -d "${stage}/base" "${stage}/common" "${stage}/inter" "${stage}/ddr"
    rm -rf ${DEPLOY_DIR_IMAGE}/dtbs
    install -d ${DEPLOY_DIR_IMAGE}/dtbs
    tp="${DEPLOY_DIR_IMAGE}/build-artifacts/techpack-dtbs"
    ddrsrc="${DEPLOY_DIR_IMAGE}/build-artifacts/ddrdtbos"

    if [ -f "${DEPLOY_DIR_IMAGE}/build-artifacts/dtb/${NORD_BASE_DTB}" ]; then
        install -m 0644 "${DEPLOY_DIR_IMAGE}/build-artifacts/dtb/${NORD_BASE_DTB}" "${stage}/base/"
    elif [ -f "${DEPLOY_DIR_IMAGE}/${NORD_BASE_DTB}" ]; then
        install -m 0644 "${DEPLOY_DIR_IMAGE}/${NORD_BASE_DTB}" "${stage}/base/"
    else
        bbfatal "nord base DTB ${NORD_BASE_DTB} not found in build-artifacts/dtb or deploy root"
    fi
    base_dtb="${stage}/base/${NORD_BASE_DTB}"
    base_name="nord-ride"

    common=""
    for o in ${NORD_COMMON_OVERLAYS}; do
        if [ -f "${tp}/${o}" ]; then
            common="${common} ${tp}/${o}"
        else
            bbnote "nord common overlay ${o} not found in techpack-dtbs (skipping)"
        fi
    done

    for v in "" ${NORD_VARIANT_OVERLAYS}; do
        set=""
        vlabel="ride"
        if [ -n "$v" ]; then
            if [ -f "${tp}/${v}" ]; then
                set="${tp}/${v}"
                # nord-ride-safe.dtbo -> vlabel "ride-safe"
                vlabel=$(basename "$v" .dtbo | sed -e 's/^nord-//')
            else
                bbnote "nord variant overlay ${v} not found (skipping variant)"
                continue
            fi
        fi
        ovls=$(sort_dtbos_by_dep "${base_dtb}" ${common} ${set} | tr '\n' ' ')
        out="${stage}/inter/nord-${vlabel}.dtb"
        if [ -n "${ovls## }" ]; then
            fdtoverlay -i "${base_dtb}" -o "${out}" -v ${ovls}
            if [ $? -ne 0 ]; then bbfatal "L1 fdtoverlay failed for variant '${vlabel}'"; fi
        else
            cp "${base_dtb}" "${out}"
        fi
        if [ -z "$v" ]; then
            fdtput -t x "${out}" / qcom,sku-id 0x100
            if [ $? -ne 0 ]; then bbfatal "failed to set qcom,sku-id on base QAM ${out}"; fi
        fi
    done

    for d in ${NORD_DDR_OVERLAYS}; do
        if [ -f "${ddrsrc}/${d}" ]; then
            install -m 0644 "${ddrsrc}/${d}" "${stage}/ddr/"
        else
            bbnote "nord DDR overlay ${d} not found in ddrdtbos (skipping)"
        fi
    done

    merge_dtbos_single "${stage}/inter" "${stage}/ddr" ${DEPLOY_DIR_IMAGE}/dtbs
    update_dtb_ddr_id ${DEPLOY_DIR_IMAGE}/dtbs

    cat ${DEPLOY_DIR_IMAGE}/dtbs/*.dtb* > ${DEPLOY_DIR_IMAGE}/dtbs/dtb.img
    bbnote "Created dtbs/dtb.img via nord variant x DDR two-layer merge"
}
do_make_dtb[depends] += "virtual/kernel:do_deploy"
do_make_dtb[depends] += "kernel-basedevicetree:do_deploy"
do_make_dtb[depends] += "${@bb.utils.contains_any('MACHINE_FEATURES', 'qti-multimedia qti-display qti-audio qti-graphics qti-camera', 'mm-vfio-devicetree:do_deploy', '', d)}"
do_make_dtb[depends] += "audiolite-devicetree:do_deploy"
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
