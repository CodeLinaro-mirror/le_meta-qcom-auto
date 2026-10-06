SUMMARY = "Package group to support userspace drivers"
DESCRIPTION = "Grouping of programs for userspace drivers on Linux System"

PROVIDES = "${PACKAGES}"

inherit packagegroup

PACKAGES = "\
    packagegroup-qti-umd \
"

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} = "\
    safelinux-cfg-modules \
    safelinux-system-cfg \
    safelinux-dbg-modules \
    dspfirmware-mount \
    scmi-test \
    scm-user-intf-test \
"

# =============================================================================
# LY.AU.0.3.0 wrynose porting exclusions
# NOTE: This section will be removed once the blocked recipes are unblocked.
# =============================================================================
# scmi-test: masked in qclinux-bbmask.inc[gvm-gen5] — cannot unmask via BBMASK:remove
# safelinux-dbg-modules: soc/qcom/secure_buffer.h removed in kernel 7.0; needs kernel team fix
RDEPENDS:${PN}:remove = "scmi-test safelinux-dbg-modules"
