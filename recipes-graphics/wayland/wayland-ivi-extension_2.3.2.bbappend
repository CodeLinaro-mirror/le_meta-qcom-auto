FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://0001-Update-wayland-ivi-extension-to-be-aligned-with-west.patch \
    file://0001-ivi-controller-Use-weston_view_add_transform.patch \
"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0001-wayland-ivi-extension-use-public-weston-15-layer-api.patch"
