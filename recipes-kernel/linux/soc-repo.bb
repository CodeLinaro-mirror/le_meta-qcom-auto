SUMMARY = "Qualcomm SoC kernel modules"
DESCRIPTION = "Out-of-tree kernel modules for Qualcomm SoC platforms"
HOMEPAGE = "https://www.codelinaro.org"
LICENSE = "GPL-2.0-with-Linux-syscall-note"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=0bad96c422c41c3a94009dcfe1bff992"

COMPATIBLE_MACHINE = "gvm-omnix"

DEPENDS = "virtual/kernel"

PV = "1.0"

# Inherit kernel module class
inherit qti-techpack

# Disable automatic make clean in base_do_configure
CLEANBROKEN = "1"

# Source location
SRC_URI = "\
    ${PATH_TO_REPO}/vendor/qcom/opensource/soc-modules/.git;protocol=${PROTO};destsuffix=vendor/qcom/opensource/soc-modules;usehead=1 \
    file://configs/soc-repo_defconfig \
"

SRCREV = "${AUTOREV}"

TECHPACK_MODULES = "\
    drivers/virt/gunyah/gh_dbl.ko \
    drivers/virt/gunyah/gh_msgq.ko \
    drivers/virt/gunyah/gh_rm_drv.ko \
    arch/arm64/gunyah/gh_arm_drv.ko \
    drivers/soc/qcom/hab/msm_hab.ko \
    drivers/virtio/virtio_mmio.ko \
    drivers/iommu/arm/arm-smmu/arm_smmu.ko \
    drivers/iommu/iommu-logger.ko \
    drivers/iommu/qcom_iommu_util.ko \
    drivers/soc/qcom/qcom_wdt_core.ko \
    drivers/soc/qcom/qcom_soc_wdt.ko \
    drivers/soc/qcom/minidump.ko \
    drivers/soc/qcom/debug_symbol.ko \
"

# Ensure we build after linux-qcom-rt shared workdir is ready
do_compile[depends] += "virtual/kernel:do_shared_workdir"

# Source directory
S = "${UNPACKDIR}/vendor/qcom/opensource/soc-modules"
DEFCONFIG_FILE = "${UNPACKDIR}/configs/soc-repo_defconfig"

# Compile: Build modules using standard OOT approach
# CONFIG_VARS and DEFCONFIG_CFLAGS are passed explicitly to override any conflicting
# values that may already exist in auto.conf from the kernel's own Kconfig.
# For example, kernel may have CONFIG_ARM_SMMU=y (built-in) while soc-repo needs =m.
do_compile() {
    DEFCONFIG_CFLAGS=""
    CONFIG_VARS=""

    if [ -f ${DEFCONFIG_FILE} ]; then
        # Match kernel autoconf.h: =y -> -DCONFIG_FOO=1, =m -> -DCONFIG_FOO_MODULE=1
        # A bare -DCONFIG_FOO (no value) makes IS_ENABLED/IS_BUILTIN/IS_MODULE false.
        Y_CFLAGS=$(grep -E '^CONFIG_.*=y$' ${DEFCONFIG_FILE} | \
                   sed -E 's/^(CONFIG_[^=]*)=y$/-D\1=1/' | \
                   tr '\n' ' ')
        M_CFLAGS=$(grep -E '^CONFIG_.*=m$' ${DEFCONFIG_FILE} | \
                   sed -E 's/^(CONFIG_[^=]*)=m$/-D\1_MODULE=1/' | \
                   tr '\n' ' ')
        DEFCONFIG_CFLAGS="${Y_CFLAGS} ${M_CFLAGS}"

        # Append int/hex type CONFIG values as -DCONFIG_FOO=value
        INT_CFLAGS=$(grep -E '^CONFIG_.*=[0-9]+$' ${DEFCONFIG_FILE} | \
                     sed 's/^\(CONFIG_[^=]*\)=\(.*\)$/-D\1=\2/' | \
                     tr '\n' ' ')
        DEFCONFIG_CFLAGS="${DEFCONFIG_CFLAGS} ${INT_CFLAGS}"

        CONFIG_VARS=$(grep -E '^CONFIG_.*=[ym]$' ${DEFCONFIG_FILE} | \
                     sed 's/=y/=m/' | \
                     tr '\n' ' ')
    else
        bbwarn "soc-repo_defconfig not found at ${DEFCONFIG_FILE}"
    fi

    # This kernel's kbuild no longer reads EXTRA_CFLAGS (removed from
    # scripts/Makefile.build/scripts/Makefile.lib in newer kernels) -- only
    # KCFLAGS (via KBUILD_CFLAGS += $(KCFLAGS) in the top-level Makefile) is
    # honored, so DEFCONFIG_CFLAGS must be passed through KCFLAGS or the
    # CONFIG_* defines (e.g. CONFIG_VIRTIO_MMIO_POLL_RESET) are silently
    # dropped from the actual compile command.
    oe_runmake -C ${STAGING_KERNEL_BUILDDIR} \
        M=${S} \
        ARCH=${ARCH} \
        CROSS_COMPILE=${TARGET_PREFIX} \
        ${CONFIG_VARS} \
        KCFLAGS="-I${STAGING_KERNEL_DIR} ${DEFCONFIG_CFLAGS} -Wno-error=implicit-fallthrough -Wno-error=unused-variable -Wno-error=format -Wno-error=incompatible-pointer-types" \
        drivers/virtio/virtio_mmio.ko
}

KERNEL_MODULE_PACKAGE_PREFIX = "soc-repo-"
PACKAGES_DYNAMIC += "^soc-repo-kernel-module-.*"

# Exclude KERNEL_VERSION from task hash calculation to avoid metadata instability
# KERNEL_VERSION is read dynamically and may cause basehash changes during reparsing
do_patch[vardepsexclude] += "KERNEL_VERSION"
do_populate_sysroot[vardepsexclude] += "KERNEL_VERSION"
do_deploy_source_date_epoch[vardepsexclude] += "KERNEL_VERSION"
