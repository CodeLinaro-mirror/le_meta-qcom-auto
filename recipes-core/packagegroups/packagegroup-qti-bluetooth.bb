SUMMARY = "QTI package group for bluetooth"

inherit packagegroup

PACKAGES = "\
    packagegroup-qti-bluetooth \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', 'dspfirmware-mount-bt', '', d)} \
    bt-dlkm \
    "

# =============================================================================
# LY.AU.0.3.0 wrynose porting exclusions
# NOTE: This section will be removed once the blocked recipes are unblocked.
# =============================================================================
# bt-dlkm: kernel 7.x API (of_get_named_gpio removed in kernel 6.7+); deferred to kernel team
RDEPENDS:${PN}:remove = "bt-dlkm"
