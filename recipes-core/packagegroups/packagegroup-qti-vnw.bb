SUMMARY = "QTI package group for vichle network"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES = "\
    packagegroup-qti-vnw \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    ${@bb.utils.contains('PREFERRED_PROVIDER_virtual/kernel', 'linux-ark', 'hsi2s-qmi-test', '', d)} \
    gptp \
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-hypervisor', 'ptp-virtual', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-hypervisor', 'avb-utils', '', d)} \
    ${@bb.utils.contains('PREFERRED_PROVIDER_virtual/kernel', 'linux-ark', 'aurix-can iproute2', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', 'hsi2s-be', '', d)} \
    "


# =============================================================================
# LY.AU.0.3.0 wrynose porting exclusions
# NOTE: This section will be removed once the blocked recipes are unblocked.
# =============================================================================
# ptp-virtual/avb-utils: source dirs absent in LY.AU.0.3.0 workspace manifest
RDEPENDS:${PN}:remove = "ptp-virtual avb-utils"
