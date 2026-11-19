SUMMARY = "QCOM Linux Kernel"
DESCRIPTION = "QCOM Linux Kernel for QTI SoC"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPLv2.0-with-linux-syscall-note"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

require recipes-kernel/linux/linux-qcom.inc

COMPATIBLE_MACHINE = "sa8775|sa7255|gen5|qclinux-gvm-gen5"

SRC_URI = "\
    ${CLO_LA_GIT}/kernel/qcom.git;branch=kernel.qclinux.1.0.r16-rel;protocol=${OSS_PROTO};destsuffix=kernel/kernel_platform/kernel \
"
SRCREV = "473ba322e11d0edc454e654c0d01f049bad86ba0"
SRC_URI:append:qclinux-gvm-gen5 = " \
    file://configs/kernel_defconfig \
    file://kernel-gvm/0001-QcLinux-kernel-adapt-LVGVM.patch \
    file://kernel-gvm/0002-QcLinux-Gunyah-RM-Driver-Adaption.patch \
    file://kernel-gvm/0003-PENDING-remoteproc-qcom-Export-SSR-subsystem-APIs-fo.patch \
    ${@bb.utils.contains_any('VARIANT', 'perf user', '', 'file://configs/kernel_defconfig_debug.fragment', d)} \
"

# Debug-only config fragment; empty for perf/user builds so it plays no
# part in do_generate_base_defconfig below.
KERNEL_DEBUG_CONFIG_FRAGMENT:qclinux-gvm-gen5 = "${@bb.utils.contains_any('VARIANT', 'perf user', '', '${WORKDIR}/configs/kernel_defconfig_debug.fragment', d)}"

S = "${WORKDIR}/kernel/kernel_platform/kernel"

do_generate_base_defconfig:qclinux-gvm-gen5() {
    # GVM uses a pre-built full defconfig as the base, then applies
    # KERNEL_CONFIG_FRAGMENTS (e.g. qcom_rt.cfg from meta-qti-realtime) and,
    # for debug builds, KERNEL_DEBUG_CONFIG_FRAGMENT on top via
    # merge_config.sh so that RT/debug/other fragments take effect.
    cp ${WORKDIR}/configs/kernel_defconfig ${WORKDIR}/defconfig
    if [ -n "${KERNEL_CONFIG_FRAGMENTS}" ] ||
       [ -n "${KERNEL_DEBUG_CONFIG_FRAGMENT}" ]; then
        ${S}/scripts/kconfig/merge_config.sh -m -r -O ${WORKDIR} \
            ${WORKDIR}/defconfig \
            ${KERNEL_CONFIG_FRAGMENTS} \
            ${KERNEL_DEBUG_CONFIG_FRAGMENT}
        cp ${WORKDIR}/.config ${WORKDIR}/defconfig
    fi
}

# Additional compiler flags required for GVM kernel build
do_compile:prepend:qclinux-gvm-gen5() {
    export KCFLAGS="-Wno-error=unused-variable -Wno-error=format"
}

# DTBs are provided by devicetree-qcom-gvm.bb, not by the kernel recipe
PACKAGES:remove:qclinux-gvm-gen5 = "${KERNEL_PACKAGE_NAME}-devicetree"
