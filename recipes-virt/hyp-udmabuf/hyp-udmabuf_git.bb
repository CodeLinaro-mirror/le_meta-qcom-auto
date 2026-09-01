SUMMARY = "Hyp udmabuf Kernel Modules"
DESCRIPTION = "This is the hyp udmabuf driver used to share dmabufs cross VMs."
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

DEPENDS += "virtual/kernel"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/hyp-udmabuf.git;branch=auto-vmm-kernel.lnx.1.0.r15-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/hyp-udmabuf"

SRCREV = "edc91039526c90b1160ed84344fe875e4b7cedd8"

S = "${WORKDIR}/vendor/qcom/opensource/hyp-udmabuf/drivers"

TECHPACK_MODULES = "\
    hyp-udmabuf.ko \
"
inherit qti-techpack

do_install:append() {
    install -d ${D}${includedir}/linux
    install -d ${D}${libdir}/modules-load.d/
    install -m 0644 ${S}/include/uapi/linux/hyp_udmabuf.h ${D}${includedir}/linux
    install -m 0755 ${S}/hyp-udmabuf.conf -D ${D}${libdir}/modules-load.d/hyp-udmabuf.conf
}

FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/*"
FILES:${PN} += "${libdir}/modules-load.d/*"

RPROVIDES:${PN} += "kernel-module-hyp-udmabuf-${KERNEL_VERSION}"
