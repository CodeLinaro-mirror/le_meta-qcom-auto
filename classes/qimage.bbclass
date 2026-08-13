do_make_dtb[depends] += "virtual/kernel"

DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-avb', 'avbtool-native', '', d)}"

# Filesystem type of the rootfs image that gets the hashtree footer.
ROOTFS_IMAGE_FSTYPE ?= "ext4"

do_make_avb_image(){
    if ${@bb.utils.contains('DISTRO_FEATURES', 'qti-avb', 'true', 'false', d)}; then
        rootfs_partition_size=$(avbtool calc_min_partition_size \
                                    --image ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.${ROOTFS_IMAGE_FSTYPE} \
                                    --partition_name system \
                                    --hash_algorithm sha256 \
                                    --do_not_generate_fec)
        #For lv avb2.0, add hashtree for system image and generate vbmeta.img.
        avbtool add_hashtree_footer \
            --image ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.${ROOTFS_IMAGE_FSTYPE} \
            --partition_size ${rootfs_partition_size} \
            --partition_name system  \
            --hash_algorithm sha256 \
            --key ${STAGING_DIR_NATIVE}${sysconfdir}/signing_tools/sigkeys/testkey_rsa4096.pem \
            --rollback_index 0 \
            --do_not_generate_fec
        avbtool make_vbmeta_image \
            --include_descriptors_from_image ${DEPLOY_DIR_IMAGE}/${BOOTIMAGE_TARGET} \
            ${@bb.utils.contains('MACHINE_FEATURES', 'dt-overlay', '--include_descriptors_from_image ${DEPLOY_DIR_IMAGE}/${PRODUCT}-dtbo.img', '', d)} \
            --include_descriptors_from_image ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.${ROOTFS_IMAGE_FSTYPE} \
            --setup_rootfs_from_kernel ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.${ROOTFS_IMAGE_FSTYPE} \
            --algorithm SHA256_RSA4096 \
            --key ${STAGING_DIR_NATIVE}${sysconfdir}/signing_tools/sigkeys/testkey_rsa4096.pem \
            --rollback_index 0 \
            --prop "com.android.build.boot.security_patch:${@time.strftime('%Y-%m-%d',time.gmtime())}" \
            --prop "com.android.build.boot.os_version:${@time.strftime('%Y-%m-%d',time.gmtime())}" \
            --output ${DEPLOY_DIR_IMAGE}/${VBMETAIMAGE_TARGET}
    fi
}

addtask do_make_avb_image after do_image_complete before do_build
