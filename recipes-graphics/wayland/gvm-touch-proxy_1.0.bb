SUMMARY = "GVM touch proxy simple Wayland client"
DESCRIPTION = "Builds the gvm-touch-proxy client from the Weston source tree as a standalone Yocto package."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause-Clear;md5=3f9cee2b2df16dbb0eee9095e0a52996"

inherit meson pkgconfig

DEPENDS = "\
    libxml2 \
    wayland \
    wayland-native \
    wayland-protocols \
    wayland-ivi-extension \
    pkgconfig-native \
"

CODE_DIR = "vendor/qcom/opensource/display/weston-sdm-extension"
SRC_URI = "${CLO_LE_GIT}/graphics/weston-sdm-extension.git;branch=display-le.lnx.6.5.r8-rel;protocol=${OSS_PROTO};destsuffix=${CODE_DIR}"
SRCREV = "ad8df4d7a9ed0a3e8bea2c0d3d754d16c6f2f9a5"

PACKAGECONFIG ??= ""
PACKAGECONFIG[coverage] = "-Db_coverage=true,-Db_coverage=false,"

S = "${WORKDIR}/${CODE_DIR}/gvm-touch-proxy"
B = "${WORKDIR}/build"

do_configure:prepend() {
    install -m 0644 \
        ${STAGING_DATADIR}/wayland-protocols/stable/ivi-application/ivi-application.xml \
        ${WORKDIR}/${CODE_DIR}/protocol/ivi-application.xml
}

EXTRA_OEMESON += "${@bb.utils.contains('PACKAGECONFIG', 'coverage', '-Db_coverage=true', '-Db_coverage=false', d)}"

do_install:append() {
    install -d ${D}${bindir}
    install -m 0644 ${S}/shared_touch.ini ${D}${bindir}/shared_touch.ini

    install -d ${D}${sysconfdir}/udev/rules.d
    install -m 0644 ${S}/99-gvm-touch-proxy-seat.rules ${D}${sysconfdir}/udev/rules.d/99-gvm-touch-proxy-seat.rules
    install -m 0644 ${S}/test/99-pvm-touch-monitor.rules ${D}${sysconfdir}/udev/rules.d/99-pvm-touch-monitor.rules
}

FILES:${PN} += "\
    ${bindir}/gvm-touch-proxy \
    ${bindir}/pvm-touch-monitor \
    ${bindir}/shared_touch.ini \
    ${bindir}/gvm-touch-proxy-test \
    ${sysconfdir}/udev/rules.d/99-gvm-touch-proxy-seat.rules \
    ${sysconfdir}/udev/rules.d/99-pvm-touch-monitor.rules \
"
