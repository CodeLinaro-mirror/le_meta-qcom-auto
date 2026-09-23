SUMMARY = "KSYNC drivers"
DESCRIPTION = "Build ksync driver to kernel module"
HOMEPAGE = "https://www.codelinaro.org"
LICENSE = "GPLv2.0-with-linux-syscall-note"
LIC_FILES_CHKSUM = "file://${QTI_LICENSE_DIR}/${LICENSE};md5=8afb6abdac9a14cb18a0d6c9c151e9b4"

SRC_URI = "${PATH_TO_REPO}/vendor/qcom/opensource/mm-auto-ksync/.git;protocol=${PROTO};destsuffix=vendor/qcom/opensource/mm-auto-ksync;usehead=1"

SRCREV = "${AUTOREV}"

DEPENDS += "unifdef-native"

S = "${UNPACKDIR}/vendor/qcom/opensource/mm-auto-ksync"

inherit qti-techpack

# Copy unifdef-native into ${STAGING_KERNEL_BUILDDIR}/scripts before headers_install runs.
do_install:prepend() {
    install -d ${STAGING_KERNEL_BUILDDIR}/scripts
    [ -e ${STAGING_KERNEL_BUILDDIR}/scripts/unifdef ] || install -m 0755 ${RECIPE_SYSROOT_NATIVE}/usr/bin/unifdef ${STAGING_KERNEL_BUILDDIR}/scripts/unifdef
}

TECHPACK_MODULE_OUT = "${UNPACKDIR}/mm-auto-ksync"
TECHPACK_MODULES = "qcom_ksync.ko"
TECHPACK_HEADERS = "${S}/include/uapi"

do_install:append() {
    install -m 0755 ${S}/qcom_ksync.conf -D ${D}${sysconfdir}/modules-load.d/qcom_ksync.conf
    install -d ${D}${includedir}/linux
    install -m 0644 ${S}/include/uapi/linux/msm_ksync.h ${D}${includedir}/linux
}

RPROVIDES:${PN} += "kernel-module-qcom-ksync-${KERNEL_VERSION}"

FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/*"
FILES:${PN} += "${sysconfdir}/*"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0001-ksync-add-vmalloc-h.patch \
                   file://0002-ksync-remove-dma-fence-ops-value-str-members.patch \
                   file://0003-ksync-fence-remove-dma-fence-ops-value-str-members.patch \
                   file://0004-ksync-platform-remove-returns-void.patch \
                   file://0005-ksync-fence-drop-signal-on-any-arg.patch \
                   file://0005-ksync-trace-adapt-assign-str-macro-signature.patch"