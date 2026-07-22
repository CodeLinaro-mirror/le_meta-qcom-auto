# Common signing variables and functions to assist signing a loadable image.
# these tools complement the boot-loader's and peripheral image loader's
# verification stage.
#

DEPENDS += "sectools-native openssl-native python3-native"
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-avb', 'avbtool-native', '', d)}"

export TARGET_SHA_TYPE = "sha256"


## Functions to handle boot.img signing ##
sign_bootimg () {
    imgname="${BOOTIMAGE_TARGET}"
    if ${@bb.utils.contains('DISTRO_FEATURES', 'dm-verity', 'true', 'false', d)}; then
        imgname="${BOOTIMAGE_TARGET}".noverity
    fi
    if ${@bb.utils.contains('DISTRO_FEATURES', 'qti-avb', 'true', 'false', d)}; then
        avbsign_boot_image ${imgname}
    else
        sign_boot_image ${imgname}
    fi
}
sign_bootimg[dirs] = "${DEPLOYDIR}"

sign_veritybootimg () {
    imgname="${BOOTIMAGE_TARGET}"
    if ${@bb.utils.contains('DISTRO_FEATURES', 'avble', 'true', 'false', d)}; then
        avbsign_boot_image ${imgname}
    else
        sign_boot_image ${imgname}
    fi
}
sign_veritybootimg[dirs] = "${DEPLOY_DIR_IMAGE}"

# Function to sign boot image present in deploydir
sign_boot_image() {
    img="$1"
    cp ${img} ${img}.nonsecure

    openssl dgst -${TARGET_SHA_TYPE} -binary ${img}.nonsecure > ${img}.${TARGET_SHA_TYPE}
    if ${@bb.utils.contains('DISTRO_FEATURES', 'vble', 'true', 'false', d)}; then
        openssl pkeyutl -sign -in ${img}.${TARGET_SHA_TYPE} \
                -inkey ${STAGING_DIR_NATIVE}${sysconfdir}/signing_tools/sigkeys/vble-qti.key \
                -out ${img}.sig -pkeyopt digest:${TARGET_SHA_TYPE} -pkeyopt rsa_padding_mode:pkcs1
    elif ${@bb.utils.contains('DISTRO_FEATURES', 'vxbl', 'true', 'false', d)}; then
        openssl pkeyutl -sign -in ${img}.${TARGET_SHA_TYPE} \
                -inkey ${STAGING_DIR_NATIVE}${sysconfdir}/signing_tools/sigkeys/vxbl-qti.key \
                -out ${img}.sig -pkeyopt digest:${TARGET_SHA_TYPE}
    else
        openssl rsautl -sign -in ${img}.${TARGET_SHA_TYPE} \
                -inkey ${STAGING_DIR_NATIVE}${sysconfdir}/signing_tools/sigkeys/qti.key \
                -out ${img}.sig
    fi
    dd if=/dev/zero of=${img}.sig.padded bs=2048 count=1
    dd if=${img}.sig of=${img}.sig.padded conv=notrunc
    cat ${img}.nonsecure ${img}.sig.padded > ${img}.secure
    rm -rf ${img}.${TARGET_SHA_TYPE} ${img}.sig ${img}.sig.padded ${img}.nonsecure
    mv -f ${img}.secure ${img}
}

# Skip zero padding if avbtool is used to sign the image
avbsign_boot_image() {
    img="$1"
    if ${@bb.utils.contains('MACHINE_FEATURES', 'qti-hypervisor', 'true', 'false', d)}; then
        # For lvgvm avb2.0, add hash for boot image.
        avbtool add_hash_footer  \
            --image ${img}  \
            --partition_size 0x04000000  \
            --partition_name boot \
            --algorithm SHA256_RSA4096 \
            --key ${STAGING_DIR_NATIVE}${sysconfdir}/signing_tools/sigkeys/vbgvm_private_key_4096.pem \
            --rollback_index 0
    else
        # For lv avb2.0, add hash for boot image and dtbo image.
        avbtool add_hash_footer  \
            --image ${img}  \
            --partition_size 0x04000000  \
            --partition_name boot \
            --algorithm SHA256_RSA4096 \
            --key ${STAGING_DIR_NATIVE}${sysconfdir}/signing_tools/sigkeys/testkey_rsa4096.pem \
            --rollback_index 0
        #add hash for dtbo image.
        avbtool add_hash_footer  \
            --image ${DEPLOYDIR}/${PRODUCT}-dtbo.img  \
            --partition_size 0x00200000 \
            --partition_name dtbo \
            --algorithm SHA256_RSA4096 \
            --key ${STAGING_DIR_NATIVE}${sysconfdir}/signing_tools/sigkeys/testkey_rsa4096.pem \
            --rollback_index 0
    fi
}

SIGNING_FUNCTION ?= "${@bb.utils.contains('SECTOOLS_VERSION', 'sectools-v1', 'sectools_sign_abl', 'sectoolsv2_sign_abl', d)}"

python sign_abl() {
        bb.note("using %s to sign abl" % (d.getVar('SIGNING_FUNCTION')))
        bb.build.exec_func(d.getVar('SIGNING_FUNCTION'), d)
}

sectoolsv2_sign_abl() {
    install -d ${D}/boot
    if [ ! -f ${S}/../abl.elf ] && [ -f ${S}/../unsigned_abl.elf ]; then
      install -m 0644 ${S}/../unsigned_abl.elf ${S}/../abl.elf
    fi

    if [ "${SECTOOLS_V2_MULTI_PROFILE_SIGNING}" = "1" ]; then
        set --
        for profile in ${SECTOOLS_V2_SECURITY_PROFILES}; do
            profile_path="${STAGING_BINDIR_NATIVE}/${profile}"
            if [ ! -f "${profile_path}" ]; then
                bbfatal "Missing Security profile: ${profile_path}"
            fi

            # Append each profile
            set -- "$@" "${profile_path}"
        done

        sectools secure-image ${S}/../abl.elf \
            --image-id ABL \
            --security-profile "$@" \
            --sign --signing-mode TEST \
            --outfile ${D}/boot/${PRODUCT}-abl.elf
    else
    sectools secure-image ${S}/../abl.elf \
      --image-id ABL --security-profile ${STAGING_BINDIR_NATIVE}/${SECTOOLS_SECURITY_PROFILE} \
      --sign --signing-mode TEST --outfile  ${D}/boot/${PRODUCT}-abl.elf
    fi
}

sectools_sign_abl() {
    install -d ${D}/boot
    # This performs signing the image at lk/build folder. The final image
    # gets written in the folder image/boot/. Subsequently deploy stage in the
    # main recipe installs to the DEPLOYDIR where rest of the system images are located.
    #
    if [ ! -f ${S}/../abl.elf ] && [ -f ${S}/../unsigned_abl.elf ]; then
        install -m 0644 ${S}/../unsigned_abl.elf ${S}/../abl.elf
    fi
    USES_SEC_POLICY_MULTIPLE_DEFAULT_SIGN=1 \
    USES_SEC_POLICY_INTEGRITY_CHECK=1 \
    python ${STAGING_BINDIR_NATIVE}/sectools/sectools_builder.py \
        -i ${S}/../abl.elf \
        -t ${D}/boot \
        -g abl \
        --soc_hw_version ${SOC_HW_VERSION} \
        --soc_vers ${SOC_VERS} \
        --config=${STAGING_BINDIR_NATIVE}/sectools/config/integration/secimage${SIGNING_VERSION}.xml \
        --install_base_dir=${D}/boot \
        2>&1
    install -m 0644 ${D}/boot/abl.elf ${D}/boot/${PRODUCT}-abl.elf
}
