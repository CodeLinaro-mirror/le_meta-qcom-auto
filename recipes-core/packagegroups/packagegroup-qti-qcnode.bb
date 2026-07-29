SUMMARY = "QTI package group for QcNode"
LICENSE = "BSD-3-Clause-Clear"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES = "\
    packagegroup-qti-qcnode \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "qcnode"

# =============================================================================
# LY.AU.0.3.0 wrynose porting exclusions
# NOTE: This section will be removed once the blocked recipes are unblocked.
# =============================================================================
# qcnode: requires fadas.h from fadas-noship (OpenCL header conflict, blocked)
RDEPENDS:${PN}:remove = "qcnode"
