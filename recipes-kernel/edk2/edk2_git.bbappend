FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:gen5 = " \
    file://0001-DNM-QcomModulePkg-Disable-Keymaster-Loading-for-SA87.patch \
"
