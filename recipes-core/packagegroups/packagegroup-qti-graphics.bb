SUMMARY = "QTI package group for kgsl"
LICENSE = "BSD-3-Clause-Clear"

PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

PACKAGES = "\
    packagegroup-qti-graphics \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    ${@bb.utils.contains_any('PREFERRED_VERSION_linux-msm', '5.15 6.1', 'graphicsdlkm', '', d)} \
    ${@bb.utils.contains_any('MACHINE_FEATURES', 'qti-umd', 'ksyncdlkm', '', d)} \
    vulkan-loader \
    auto-gfx-app \
    "

# =============================================================================
# LY.AU.0.3.0 wrynose porting exclusions
# NOTE: This section will be removed once the blocked recipes are unblocked.
# =============================================================================
# ksyncdlkm is required by kgsl@.service ExecStartPre=modprobe qcom_ksync.
# The kernel 7.x porting patches now build qcom_ksync.ko successfully, so keep
# ksyncdlkm in packagegroup-qti-graphics when qti-umd is enabled.
