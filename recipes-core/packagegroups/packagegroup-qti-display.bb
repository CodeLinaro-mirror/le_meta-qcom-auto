SUMMARY = "QTI package group for weston"

PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

PACKAGES = "\
    packagegroup-qti-display \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    ${@bb.utils.contains_any("PREFERRED_VERSION_linux-msm", "5.15 6.1", "displaydlkm", "", d)} \
    libdrm \
    wayland \
    wayland-ivi-extension \
    weston \
    weston-init \
    weston-examples \
    display-hal-linux \
    display-commonsys-intf-linux \
    weston-sdm-extension \
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', 'kiumd-headers', '', d)} \
    "

RDEPENDS:${PN}:remove:gen5 = "display-commonsys-intf-linux"
RDEPENDS:${PN}:remove:qti-dpk = "wayland-ivi-extension"
RDEPENDS:${PN}:append:qti-dpk = " weston-udev"
RDEPENDS:${PN}:remove:qti-dpk = "weston-sdm-extension"

# =============================================================================
# LY.AU.0.3.0 wrynose porting exclusions
# NOTE: This section will be removed once the blocked recipes are unblocked.
# =============================================================================
# display-hal-linux: kernel display drivers not in linux-qcom-next. Team
# decision: unblock regardless of runtime driver availability 
# so the package gets compiled;
# weston-sdm-extension: required for the SDM-backed Weston backend on gen5.
# Weston 15 porting patches are applied in weston-sdm-extension_1.0.0.bb.
