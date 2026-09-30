DESCRIPTION = "Build kernel base-devicetree"
LICENSE = "(GPL-2.0-only | BSD-2-Clause) & BSD-3-Clause"
LIC_FILES_CHKSUM = "\
    file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6 \
    file://${COREBASE}/meta/files/common-licenses/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f \
    file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9 \
"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/base-devicetree.git;branch=auto-kernel.qclinux.1.0.r13-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/base-devicetree"

SRCREV = "649dc8f4e4d421a0f0129dc453023c427ca02c33"
SRCREV:sa8775-flex = "fb22d631b0fd178f2658f772e68fac81f3f98d01"

S = "${WORKDIR}/vendor/qcom/opensource/base-devicetree"

inherit deploy kernel-arch module-base

do_compile() {
    make dtbos KDIR=${STAGING_KERNEL_DIR} O=${STAGING_KERNEL_BUILDDIR} CC="${KERNEL_CC}" LD="${KERNEL_LD}"
}

# Install oot-dt-bindings headers to sysroot so that vms-devicetree
# and other out-of-tree devicetree recipes can include them.
do_install:append:gen5() {
    if [ -d ${S}/arch/arm64/boot/dts/qcom/oot-dt-bindings ]; then
        install -d ${D}${includedir}/oot-dt-bindings
        install -m 0644 ${S}/arch/arm64/boot/dts/qcom/oot-dt-bindings/*.h \
            ${D}${includedir}/oot-dt-bindings/
    fi
}

FILES:${PN}-dev:append:gen5 = " ${includedir}/oot-dt-bindings/*"

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

        for dtb in ${OOT_DDR_DTBOS}; do
            if [ -f ${B}/$dtb ]; then
                install -m 0644 ${B}/$dtb ${DEPLOYDIR}/build-artifacts/ddrdtbos
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
