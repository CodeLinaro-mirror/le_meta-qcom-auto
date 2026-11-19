SUMMARY = "msm-ext-display drivers"
DESCRIPTION = "Build display drivers msm_ext_display.ko"
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/opensource/mm-drivers.git;branch=display-kernel.lnx.12.5.r18-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/mm-drivers"
SRC_URI:sa8775-flex = "${CLO_LE_GIT}/platform/vendor/opensource/gen4-5/mm-drivers.git;branch=display-kernel_gen4-5.lnx.5.15.1.r2-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/mm-drivers"

SRCREV = "52d58a03de9597f6325a382cc9d13b9196fa2fba"
SRCREV:sa8775-flex = "69644385c6df1a52def544d9349217a44e5860fa"

S = "${WORKDIR}/${TARGET_DIR}vendor/qcom/opensource/mm-drivers/msm_ext_display"

EXT_MODULE = "${TARGET_DIR}vendor/qcom/opensource/mm-drivers/msm_ext_display"
TECHPACK_MODULE_OUT = "${WORKDIR}/mm-drivers/msm_ext_display"
TECHPACK_MODULES = "msm_ext_display.ko"
TECHPACK_HEADERS = "${WORKDIR}/${TARGET_DIR}vendor/qcom/opensource/mm-drivers/msm_ext_display/include/uapi"

inherit qti-techpack

do_install:append:gvm-gen5(){
    install -d ${D}${includedir}/hw_fence/include
    install -m 644 ${S}/../hw_fence/include/*.h ${D}${includedir}/hw_fence/include/
}

RPROVIDES:${PN} += "kernel-module-msm-ext-display-${KERNEL_VERSION}"

FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/*"
FILES:${PN} += "${sysconfdir}/*"
