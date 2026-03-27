SUMMARY = "vhost gpio backend device"
DESCRIPTION = "A vhost-user backend that emulates a VirtIO GPIO device"
HOMEPAGE = "https://github.com/rust-vmm/vhost-device"
LICENSE = "Apache-2.0 | BSD-3-Clause"
LIC_FILES_CHKSUM = "\
    file://LICENSE-APACHE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://LICENSE-BSD-3-Clause;md5=2489db1359f496fff34bd393df63947e \
"
DEPENDS += "libgpiod"
# libgpiod-sys generates bindings using bindgen, which depends on clang
DEPENDS += "clang-native"

SRCPROJECT  = "${CLO_LE_GIT}/platform/external/rust-vmm/vhost-device.git"
SRCBRANCH  = "auto-vmm.lnx.1.0.r23-rel"
SRCREV  = "fcc85600c227107d21681e6e2b0000ce106bc238"

SRC_URI = "\
    ${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=external/vhost-device; \
"
S = "${WORKDIR}/external/vhost-device"
CARGO_SRC_DIR = "vhost-device-gpio"

inherit cargo pkgconfig systemd
include vhost-device-crates.inc

CARGO_BUILD_FLAGS:remove = "--frozen"
