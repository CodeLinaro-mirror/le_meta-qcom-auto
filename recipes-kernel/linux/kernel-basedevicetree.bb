DESCRIPTION = "Build kernel base-devicetree"
LICENSE = "(GPL-2.0-only | BSD-2-Clause) & BSD-3-Clause"
LIC_FILES_CHKSUM = "\
    file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6 \
    file://${COREBASE}/meta/files/common-licenses/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f \
    file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9 \
"

SRC_URI = "${PATH_TO_REPO}/vendor/qcom/opensource/base-devicetree/.git;protocol=${PROTO};destsuffix=vendor/qcom/opensource/base-devicetree;usehead=1"
SRCREV = "${AUTOREV}"
S = "${UNPACKDIR}/vendor/qcom/opensource/base-devicetree"

inherit deploy kernel-arch module-base

do_compile() {
    local LINK_NAME="base-dts-build"
    local LINK="${STAGING_KERNEL_DIR}/${LINK_NAME}"
    rm -f "${LINK}"
    ln -s "${S}" "${LINK}"

    local INC_PREFIXES="${STAGING_KERNEL_DIR}/scripts/dtc/include-prefixes"
    local INC_LINK="${INC_PREFIXES}/oot-dt-bindings"
    rm -f "${INC_LINK}"
    if [ -d "${S}/arch/arm64/boot/dts/qcom/oot-dt-bindings" ]; then
        ln -s "${S}/arch/arm64/boot/dts/qcom/oot-dt-bindings" "${INC_LINK}"
    fi

    if [ -d "${S}/dt-bindings" ]; then
        for hdr in $(cd "${S}/dt-bindings" && find . -name '*.h'); do
            rel="${hdr#./}"
            install -d "${INC_PREFIXES}/dt-bindings/$(dirname ${rel})"
            install -m 0644 "${S}/dt-bindings/${rel}" "${INC_PREFIXES}/dt-bindings/${rel}"
        done
    fi

    oe_runmake -C ${STAGING_KERNEL_DIR} \
        O=${STAGING_KERNEL_BUILDDIR} \
        ARCH=${ARCH} \
        CROSS_COMPILE=${TARGET_PREFIX} \
        dtstree=${LINK_NAME} \
        DTC_FLAGS="-@" \
        dtbs

    # Stage built artifacts under ${B} preserving their dtstree-relative path,
    # so do_deploy's `${B}/$dtb` lookups resolve.
    for f in ${OOT_DTBS} ${OOT_DDR_DTBOS} ${KERNEL_MACHINE_DTB}; do
        src="${STAGING_KERNEL_BUILDDIR}/${LINK_NAME}/${f}"
        if [ -f "${src}" ]; then
            install -d "${B}/$(dirname ${f})"
            install -m 0644 "${src}" "${B}/${f}"
        fi
    done

    rm -f "${LINK}" "${INC_LINK}"
}

# lock to avoid parallel compiling with techpack
do_compile[lockfiles] += "${TMPDIR}/qti-techpack.lock"

do_deploy() {
    if [ -n "${KERNEL_MACHINE_DTB}" ]; then
        install -d ${DEPLOYDIR}/build-artifacts/dtb

        for dtb in ${KERNEL_MACHINE_DTB}; do
            if [ -f ${B}/$dtb ]; then
                install -m 0644 ${B}/$dtb ${DEPLOYDIR}/build-artifacts/dtb
            fi
        done
    fi

    if [ -n "${OOT_DDR_DTBOS}" ]; then
        install -d ${DEPLOYDIR}/build-artifacts/ddrdtbos
        install -d ${DEPLOYDIR}/build-artifacts/ddrdtbosflex

        for dtb in ${OOT_DDR_DTBOS}; do
            if [ -f ${B}/$dtb ]; then
                # copy flex dtbo in separate directory
                if [[ "$dtb" == *flex* ]]; then
                    install -m 0644 ${B}/$dtb ${DEPLOYDIR}/build-artifacts/ddrdtbosflex
                else
                    install -m 0644 ${B}/$dtb ${DEPLOYDIR}/build-artifacts/ddrdtbos
                fi
            fi
        done
    fi

    if [ -n "${KERNEL_BASE_DTB}" ]; then
        install -d ${DEPLOYDIR}/build-artifacts/kernel-dtb

        for dtb in ${KERNEL_BASE_DTB}; do
            if [ -f ${B}/$dtb ]; then
                install -m 0644 ${B}/$dtb ${DEPLOYDIR}/build-artifacts/kernel-dtb
            fi
        done
    fi

    if [ -n "${OOT_DTBS}" ]; then
        install -d ${DEPLOYDIR}/build-artifacts/techpack-dtbs

        for dtb in ${OOT_DTBS}; do
            if [ -f ${B}/$dtb ]; then
                install -m 0644 ${B}/$dtb ${DEPLOYDIR}/build-artifacts/techpack-dtbs
            fi
        done
    fi
}

addtask do_deploy after do_compile before do_packagedata
