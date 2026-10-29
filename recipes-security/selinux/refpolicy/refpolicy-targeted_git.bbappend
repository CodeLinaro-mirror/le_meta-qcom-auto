FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom = " \
    file://0004-label-qcom-var-overlay-workdir.patch \
    file://0005-label-qti-vhost-devices-as-vhost-device.patch \
    file://0006-refpolicy-fix-root-shell-started-through-su-from-ini.patch \
"
