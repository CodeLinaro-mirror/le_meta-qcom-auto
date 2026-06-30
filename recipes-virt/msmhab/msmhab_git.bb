SUMMARY = "HAB dlkm Modules"
DESCRIPTION = "HAB(Hypervisor ABstraction) can be used for the communication between the \
GVM(Guest Virtual Machine) and host. Here, the libuhab is a wrapper of the kernel space driver."
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

DEPENDS += "gunyah-drivers"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/mmhab-drv.git;branch=mmhab-drv.lnx.1.0.r15-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/mmhab-drv"

SRCREV = "7e095d9fae10d288c4d76967ea835718c214d4f3"

S = "${WORKDIR}/vendor/qcom/opensource/mmhab-drv"

TECHPACK_MAKE_ARGS = "KBUILD_EXTRA_SYMBOLS=${STAGING_INCDIR}/Module.symvers"
EXTRA_OEMAKE:append = " GUNYAH_DRIVERS_SYSROOT_INCDIR=${STAGING_INCDIR}"

TECHPACK_MODULE_OUT = "${WORKDIR}/vendor/qcom/opensource/mmhab-drv"
TECHPACK_MODULES = "msm_hab.ko"

inherit qti-techpack

do_patch_more() {
    rm -rf ${WORKDIR}/vendor/qcom/opensource/mmhab-drv/vhost.h
}

addtask patch_more after do_patch before do_compile

do_install:append() {
    install -m 0755 ${WORKDIR}/vendor/qcom/opensource/mmhab-drv/msmhab.conf -D ${D}${libdir}/modules-load.d/msmhab.conf
    install -d ${D}${includedir}/linux
    install -m 0644 ${WORKDIR}/vendor/qcom/opensource/mmhab-drv/include/uapi/linux/habmmid.h ${D}${includedir}/linux
    install -m 0644 ${WORKDIR}/vendor/qcom/opensource/mmhab-drv/include/uapi/linux/hab_ioctl.h ${D}${includedir}/linux
}

EXTRA_OECONF += "--disable-doc --disable-Werror"

RPROVIDES:${PN} += "kernel-module-msm-hab-${KERNEL_VERSION}"

FILES:${PN} += "${libdir}/modules-load.d/*"
FILES:${PN} += "${nonarch_base_libdir}/modules/*"
