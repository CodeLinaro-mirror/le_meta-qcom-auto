SUMMARY = "Utilities to enhance systemd power interactions"
DESCRIPTION = "Provides systemd units as infra to hook services into systemd sleep logic"
HOMEPAGE = "https://git.codelinaro.org"
SECTION = "base"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "glibc glib-2.0 bootkpi-logging systemd"



SRCPROJECT  = "git://${OSS_REPO}/clo/le/platform/vendor/qcom-opensource/safelinux-services.git"
SRCBRANCH  = "safe-services.lnx.1.0.r18-rel"
SRCREV  = "bf720df5345dc5211b7b48f25b6fb8a782f21796"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/safelinux-services;"
S = "${WORKDIR}/vendor/qcom/opensource/safelinux-services/power-utils"

inherit systemd cmake pkgconfig

SYSTEMD_SERVICE:${PN} = "\
    check-inhibitors.service \
    sleep-bounds.target \
    trigger-resume.service \
    failure-resume.service \
    sleep-apps.target \
    sleep-drivers.target \
    make-pm-dir.service \
"

FILES:${PN} += "\
    ${systemd_system_unitdir}/* \
    ${libdir}/tmpfiles-early.d/* \
"
