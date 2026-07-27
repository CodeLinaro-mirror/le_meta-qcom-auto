SUMMARY = "Graphics Applications"
DESCRIPTION = "A light kit for graphics"
HOMEPAGE = "https://www.codelinaro.org"
LICENSE = "BSD-3-Clause-Clear"

LIC_FILES_CHKSUM = "file://${QTI_LICENSE_DIR}/${LICENSE};md5=b796c0007db682166a1721da80267bb2"

DEPENDS:append = " wayland-native pkgconfig-native wayland-protocols gbm gbm-headers virtual/egl bootkpi-logging"

SRC_URI = "\
    ${PATH_TO_REPO}/vendor/qcom/opensource/auto-gfx-app/.git;protocol=${PROTO};destsuffix=vendor/qcom/opensource/auto-gfx-app;usehead=1 \
"

SRC_URI:append = " \
    file://gles2_kpi.service \
    file://gles2_kpi.conf \
"

SRCREV = "${AUTOREV}"

inherit cmake systemd sleep-notify-service

SYSTEMD_SERVICE:${PN} = "gles2_kpi.service"

S = "${UNPACKDIR}/vendor/qcom/opensource/auto-gfx-app"

do_install() {
    install -d ${D}${libdir} \
               ${D}${bindir}

    install -m 0755 gles/kpi ${D}${bindir}/gles2-kpi
    install -m 0755 yxw/libyxw.so.1.0.0 ${D}${libdir}/libyxw.so.1.0.0

    ln -sf libyxw.so.1.0.0 ${D}${libdir}/libyxw.so.1
    ln -sf libyxw.so.1 ${D}${libdir}/libyxw.so

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir} \
                   ${D}/${sysconfdir}/systemd/system/multi-user.target.wants

        install -m 0644 ${UNPACKDIR}/gles2_kpi.service \
                        ${D}${systemd_system_unitdir}/gles2_kpi.service

        ln -sf ${systemd_system_unitdir}/gles2_kpi.service ${D}/${sysconfdir}/systemd/system/multi-user.target.wants/gles2_kpi.service
        install -m 0644 ${UNPACKDIR}/gles2_kpi.conf -D ${D}${systemd_system_unitdir}/sleep-notify@gles2_kpi.service.d/gles2_kpi.conf
    fi
}

FILES:${PN} += "\
    ${systemd_system_unitdir}/*.service \
    ${systemd_system_unitdir}/sleep-notify@gles2_kpi.service.d/ \
"

# CMake 4.x policy + cross-compile pkgconfig fix (FindPkgConfig doesn't inherit PKG_CONFIG_PATH)
EXTRA_OECMAKE:append = " -DCMAKE_POLICY_VERSION_MINIMUM=3.5 \
    -DPKG_CONFIG_EXECUTABLE=${STAGING_BINDIR_NATIVE}/pkg-config \
    -DCMAKE_PREFIX_PATH=${STAGING_DIR_TARGET}${prefix} \
    -DPKG_CONFIG_PATH=${STAGING_LIBDIR}/pkgconfig:${STAGING_DATADIR}/pkgconfig \
"
export PKG_CONFIG_SYSROOT_DIR = "${STAGING_DIR_TARGET}"

# GCC 15.3: int/pointer conversions + implicit declarations + _GNU_SOURCE for glibc extensions
CFLAGS:append = " -Wno-int-conversion -Wno-incompatible-pointer-types -Wno-implicit-function-declaration -D_GNU_SOURCE"
CXXFLAGS:append = " -Wno-int-conversion -Wno-incompatible-pointer-types -D_GNU_SOURCE"
