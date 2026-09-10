FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://0001-gptp-restore-tsc-feature-gating-and-fallback.patch \
"

# Restore gen5 build behavior from 1.0 so TSC mode is only enabled where intended.
EXTRA_OEMAKE:append:gen5 = " ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', 'TSC_FEATURE=1', '', d)}"
