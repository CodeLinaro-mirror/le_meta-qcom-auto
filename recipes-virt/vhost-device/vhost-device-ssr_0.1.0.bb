SUMMARY = "vhost ssr backend device"
DESCRIPTION = "A vhost-user backend that emulates a virtIO ssr agent. The daemon is provided by Qualcomm."
HOMEPAGE = "https://git.codelinaro.org/clo/le/platform/external/rust-vmm/vhost-device"
LICENSE = "Apache-2.0 | BSD-3-Clause"
LIC_FILES_CHKSUM = "\
    file://LICENSE-APACHE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://LICENSE-BSD-3-Clause;md5=2489db1359f496fff34bd393df63947e \
"
SYSTEMD_SERVICE:${PN} = "vhost-device-ssr.service"
SYSTEMD_SERVICE:${PN}:append:gen5 = " vhost-device-ssr_lv.service"
DEPENDS += "libssr-client"

SRC_URI = "${CLO_LE_GIT}/platform/external/rust-vmm/vhost-device.git;branch=auto-vmm.lnx.1.0.r29-rel;protocol=${OSS_PROTO};destsuffix=external/vhost-device"
SRCREV = "52a18c22e012532f7804ba9b94dc554313e16486"
S = "${WORKDIR}/external/vhost-device"

inherit cargo systemd

RDEPENDS:${PN} += "libssr-client"
CARGO_SRC_DIR = "vhost-device-ssr"

do_install:append() {
    install -d ${D}/${systemd_unitdir}/system/
    install -m 0644 ${S}/vhost-device-ssr/vhost-device-ssr.service ${D}/${systemd_unitdir}/system/vhost-device-ssr.service
}

do_install:append:gen5() {
    install -d ${D}/${systemd_unitdir}/system/
    install -m 0644 ${S}/vhost-device-ssr/vhost-device-ssr_sa8797.service ${D}/${systemd_unitdir}/system/vhost-device-ssr.service
    install -m 0644 ${S}/vhost-device-ssr/vhost-device-ssr_sa8797_lv.service ${D}/${systemd_unitdir}/system/vhost-device-ssr_lv.service
}

include vhost-device-crates.inc

RUSTFLAGS += "-L${STAGING_LIBDIR} -l ssr-client"
