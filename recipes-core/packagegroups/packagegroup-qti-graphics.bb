SUMMARY = "QTI package group for kgsl"
LICENSE = "BSD-3-Clause-Clear"

PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

PACKAGES = "\
    packagegroup-qti-graphics \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    ${@bb.utils.contains_any('MACHINE_FEATURES', 'qti-umd', 'ksyncdlkm', '', d)} \
    vulkan-loader \
    ${@bb.utils.contains_any('MACHINE_FEATURES', 'qti-umd', 'auto-gfx-app', '', d)} \
    "

# =============================================================================
# LY.AU.0.3.0 wrynose porting exclusions
# NOTE: This section will be removed once the blocked recipes are unblocked.
# =============================================================================
# ksyncdlkm is required by kgsl@.service ExecStartPre=modprobe qcom_ksync.
# The kernel 7.x porting patches now build qcom_ksync.ko successfully, so keep
# ksyncdlkm in packagegroup-qti-graphics when qti-umd is enabled.
RDEPENDS:${PN}:gvm-gen4-5 += "graphics-hgsldlkm"
RDEPENDS:${PN}:gvm-gen5 += "graphics-hgsldlkm"
