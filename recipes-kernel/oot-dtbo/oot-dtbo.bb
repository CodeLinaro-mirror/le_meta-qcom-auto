SUMMARY = "External/Out of tree (OOT) device tree overlay"
DESCRIPTION = "External/out of tree (OOT) device tree overlay"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

DEPENDS += "bison-native dtc-native virtual/kernel"

SRC_URI = "${PATH_TO_REPO}/vendor/qcom/opensource/safelinux-system-cfg/.git;protocol=${PROTO};destsuffix=vendor/qcom/opensource/safelinux-system-cfg/devicetree;subpath=devicetree;usehead=1"
# The gen5 nord patch was generated against QLI-2.0 commit cc2200186 which had a
# stripped Makefile. Our HEAD (c7461ac) has the full 93-line Makefile so the hunk
# fails. Instead, add the nord-ride_overlay.dtbo entry via do_configure:prepend
# and bring in the new source files (dtso, headers) via a targeted patch.
SRC_URI:append:gen5 = " file://0001-oot-dtbo-enable-for-nord.patch;patchdir=.."
SRCREV = "${AUTOREV}"

S = "${UNPACKDIR}/vendor/qcom/opensource/safelinux-system-cfg/devicetree"
OVERLAYED_OOT_DTBS_OUT = "${UNPACKDIR}/overlaid-oot-dtbs"

inherit qcom-dtb-merge deploy kernel-arch

KERNEL_BASE_DTB ?= ""
OOT_DTBS ?= ""

do_compile() {
    local OOT_LINK_NAME="oot-dts-build"
    local OOT_LINK="${STAGING_KERNEL_DIR}/${OOT_LINK_NAME}"
    rm -f "${OOT_LINK}"
    ln -s "${S}" "${OOT_LINK}"

    local INC_PREFIXES="${STAGING_KERNEL_DIR}/scripts/dtc/include-prefixes"
    local OOT_INC_LINK="${INC_PREFIXES}/oot-dt-bindings"
    rm -f "${OOT_INC_LINK}"
    ln -s "${S}/oot-dt-bindings" "${OOT_INC_LINK}"

    oe_runmake -C ${STAGING_KERNEL_DIR} \
        O=${STAGING_KERNEL_BUILDDIR} \
        ARCH=${ARCH} \
        CROSS_COMPILE=${TARGET_PREFIX} \
        dtstree=${OOT_LINK_NAME} \
        QCLINUX_BUILD=true \
        dtbs

    find "${STAGING_KERNEL_BUILDDIR}/${OOT_LINK_NAME}" -name "*.dtbo" \
        -exec cp -f {} "${S}/" \; 2>/dev/null || true

    rm -f "${OOT_LINK}" "${OOT_INC_LINK}"
}

do_merge_dtb() {
    if [ -z "${KERNEL_BASE_DTB}" ]; then
        bbnote "KERNEL_BASE_DTB is empty; skipping do_merge_dtb"
        return 0
    fi

    rm -rf "${OVERLAYED_OOT_DTBS_OUT}"
    install -d "${OVERLAYED_OOT_DTBS_OUT}"
    install -d "${UNPACKDIR}/dtbodir"

    for oot_dtbo in ${OOT_DTBS}; do
        if [ -f "${S}/${oot_dtbo}" ]; then
            install -m 0644 "${S}/${oot_dtbo}" "${UNPACKDIR}/dtbodir/"
        else
            bbnote "DTBO not found: ${oot_dtbo} (skipping)"
        fi
    done

    # Search multiple locations for the base DTB — kernel deploys to DEPLOY_DIR_IMAGE directly
    dtb_dir=""
    for d in \
        "${DEPLOY_DIR_IMAGE}" \
        "${DEPLOY_DIR_IMAGE}/dtb" \
        "${DEPLOY_DIR_IMAGE}/build-artifacts/dtb" \
        "${DEPLOY_DIR_IMAGE}/build-artifacts/kernel-dtb"
    do
        if [ -f "${d}/${KERNEL_BASE_DTB}" ]; then
            dtb_dir="${d}"
            break
        fi
    done

    if [ -z "${dtb_dir}" ]; then
        bbfatal "Base DTB ${KERNEL_BASE_DTB} not found under ${DEPLOY_DIR_IMAGE}"
    fi

    bbnote "Merging base DTB: ${dtb_dir}/${KERNEL_BASE_DTB}"
    bbnote "Using DTBOs from: ${UNPACKDIR}/dtbodir"
    bbnote "Output dir: ${OVERLAYED_OOT_DTBS_OUT}"

    merge_dtbos_single "${dtb_dir}" "${UNPACKDIR}/dtbodir" "${OVERLAYED_OOT_DTBS_OUT}"
}
do_merge_dtb[depends] += "virtual/kernel:do_deploy"
addtask do_merge_dtb after do_compile before do_install

do_install:append() {
    if [ -d ${S}/oot-dt-bindings/ ]; then
        install -d ${D}${includedir}/safelinux-system-cfg/oot-dt-bindings
        install -m 0644 ${S}/oot-dt-bindings/*.h ${D}${includedir}/safelinux-system-cfg/oot-dt-bindings/
    fi
}


do_deploy() {
    install -d ${DEPLOYDIR}/build-artifacts/dtb
    # Copy non-overlayed OOT DTBs, if OOT_DTBS supplied
    if [ -n "${OOT_DTBS}" ]; then
        for dtb in ${OOT_DTBS}; do
            if [ -f ${S}/$dtb ]; then
                install -m 0644 ${S}/$dtb ${DEPLOYDIR}/build-artifacts/dtb/
            fi
        done
    fi
    # Copy overlayed OOT DTBs, if generated post DTBO match
    if ls ${OVERLAYED_OOT_DTBS_OUT}/*.dtb 2>&1 > /dev/null; then
        install -m 0644 ${OVERLAYED_OOT_DTBS_OUT}/*.dtb ${DEPLOYDIR}/build-artifacts/dtb/
    fi
    if [ -n "${OOT_DDR_DTBOS}" ]; then
        install -d ${DEPLOYDIR}/build-artifacts/ddrdtbos

        for dtb in ${OOT_DDR_DTBOS}; do
            if [ -f ${B}/$dtb ]; then
                if [ "${SINGLE_GVM_SUPPORT}" = "1" ]; then
                    install -d ${DEPLOYDIR}/build-artifacts/ddrdtbos0gvm

                    # copy 0gvm dtbo in separate directory
                    case "$dtb" in
                        *0gvm*)
                            install -m 0644 ${B}/$dtb ${DEPLOYDIR}/build-artifacts/ddrdtbos0gvm
                            ;;
                        *)
                            install -m 0644 ${B}/$dtb ${DEPLOYDIR}/build-artifacts/ddrdtbos
                            ;;
                    esac
                else
                    install -m 0644 ${B}/$dtb ${DEPLOYDIR}/build-artifacts/ddrdtbos
                fi
            fi
        done
    fi
    # Create build-artifacts/dtb/dtb.img from the overlay-merged DTB
    # The merged file was just copied above as nord-ride_overlay.dtb
    merged_dtb="${DEPLOYDIR}/build-artifacts/dtb/${KERNEL_BASE_DTB%.*}_overlay.dtb"
    if [ ! -f "$merged_dtb" ]; then
        merged_dtb="${DEPLOYDIR}/build-artifacts/dtb/${KERNEL_BASE_DTB}"
    fi
    if [ -f "$merged_dtb" ]; then
        cat "$merged_dtb" > ${DEPLOYDIR}/build-artifacts/dtb/dtb.img
        bbnote "Created build-artifacts/dtb/dtb.img from $merged_dtb"
    else
        bbnote "No merged DTB found for dtb.img (${KERNEL_BASE_DTB%.*}_overlay.dtb absent)"
    fi
}
addtask do_deploy after do_install
