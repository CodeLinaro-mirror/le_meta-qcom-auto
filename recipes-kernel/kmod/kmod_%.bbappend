FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

BLACKLIST_FILE = "${@bb.utils.contains("MACHINE_FEATURES", "qti-gvm", "blacklist_gvm.conf", "blacklist.conf", d)}"
# shikra-evk-auto has no GearVM/btpower dependency (unlike the HGY LV PVM
# machines blacklist.conf's "blacklist hci_uart" was added for) and stock
# meta-qcom shikra-evk never blacklists hci_uart, so use a variant without
# it here to keep Bluetooth (hci_uart -> btqca -> wcn399x) autoloading at
# parity with shikra-evk instead of requiring a manual modprobe.
BLACKLIST_FILE:shikra-evk-auto = "blacklist_shikra-evk-auto.conf"
SRC_URI:append = " file://${BLACKLIST_FILE}"

do_install:append () {
    install -Dm644 "${UNPACKDIR}/${BLACKLIST_FILE}" "${D}${sysconfdir}/modprobe.d/blacklist.conf"
}
