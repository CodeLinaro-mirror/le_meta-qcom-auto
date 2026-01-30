DESCRIPTION = "Build kernel base-devicetree"
LICENSE = "(GPL-2.0-only | BSD-2-Clause) & BSD-3-Clause"
LIC_FILES_CHKSUM = "\
    file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6 \
    file://${COREBASE}/meta/files/common-licenses/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f \
    file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9 \
"

SRC_URI = "${PATH_TO_REPO}/vendor/qcom/opensource/base-devicetree/.git;protocol=${PROTO};usehead=1"

SRCREV = "${AUTOREV}"

S = "${WORKDIR}/vendor/qcom/opensource/base-devicetree"

inherit deploy kernel-arch module-base

do_compile() {
    make dtbos KDIR=${STAGING_KERNEL_DIR} O=${STAGING_KERNEL_BUILDDIR} CC="${KERNEL_CC}" LD="${KERNEL_LD}"
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
