SUMMARY = "Graphics Applications"
DESCRIPTION = "A light kit for graphics"
HOMEPAGE = "https://www.codelinaro.org"
LICENSE = "BSD-3-Clause-Clear"

LIC_FILES_CHKSUM = "file://${QTI_LICENSE_DIR}/${LICENSE};md5=b796c0007db682166a1721da80267bb2"

DEPENDS:append = " wayland-native pkgconfig-native wayland-protocols gbm gbm-headers virtual/egl bootkpi-logging"

SRC_URI = "\
    ${CLO_LE_GIT}/platform/vendor/qcom/opensource/auto-gfx-app.git;branch=auto-gfx-app.dev.1.0.r7-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/auto-gfx-app \
"

SRC_URI:append = " \
    file://gles2_kpi.service \
    file://gles2_kpi.conf \
"

SRCREV = "e9aa6a2933e348f7c83d076935155a43139239d2"

inherit cmake systemd sleep-notify-service

SYSTEMD_SERVICE:${PN} = "gles2_kpi.service"

S = "${WORKDIR}/vendor/qcom/opensource/auto-gfx-app"

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

        install -m 0644 ${WORKDIR}/gles2_kpi.service \
                        ${D}${systemd_system_unitdir}/gles2_kpi.service

        ln -sf ${systemd_system_unitdir}/gles2_kpi.service ${D}/${sysconfdir}/systemd/system/multi-user.target.wants/gles2_kpi.service
        install -m 0644 ${WORKDIR}/gles2_kpi.conf -D ${D}${systemd_system_unitdir}/sleep-notify@gles2_kpi.service.d/gles2_kpi.conf
    fi
}

FILES:${PN} += "\
    ${systemd_system_unitdir}/*.service \
    ${systemd_system_unitdir}/sleep-notify@gles2_kpi.service.d/ \
"

