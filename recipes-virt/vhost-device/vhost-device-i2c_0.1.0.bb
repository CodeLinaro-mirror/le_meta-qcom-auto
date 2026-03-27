SUMMARY = "vhost i2c backend device"
DESCRIPTION = "A vhost-user backend that emulates a VirtIO I2C device. The daemon provided by rust-vmm/vhost-device."
HOMEPAGE = "https://github.com/rust-vmm/vhost-device"
LICENSE = "Apache-2.0 | BSD-3-Clause"
LIC_FILES_CHKSUM = "\
    file://LICENSE-APACHE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://LICENSE-BSD-3-Clause;md5=2489db1359f496fff34bd393df63947e \
"



SRCPROJECT  = "${CLO_LE_GIT}/platform/external/rust-vmm/vhost-device.git"
SRCBRANCH  = "auto-vmm.lnx.1.0.r23-rel"
SRCREV  = "fcc85600c227107d21681e6e2b0000ce106bc238"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=external/vhost-device;"

S = "${WORKDIR}/external/vhost-device"
CARGO_SRC_DIR = "vhost-device-i2c"

inherit cargo systemd
SYSTEMD_SERVICE:${PN}:gen5 = "vhost-device-i2c.service"

do_install:append:gen5() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/vhost-device-i2c/vhost-device-i2c_sa8797.service ${D}${systemd_system_unitdir}/vhost-device-i2c.service
}

include vhost-device-crates.inc

CARGO_BUILD_FLAGS:remove = "--frozen"
