SUMMARY = "QTI package group for audio"
DESCRIPTION = "This is the minimal set of packages required for audio kernel modules and audio initialization scripts."
LICENSE = "GPL-2.0-only & BSD-3-Clause"

PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

PACKAGES = "${PN}"

# qti-audio is for elite framework, qti-audio-ar is for AR framework
# KMD means Kernel Mode Driver compare with UMD as User Mode Driver
# NOTE: For kernel 5.4 + AR + hyp, uses audiodlkm rather than ar-audiodlkm
KMD_RDEPENDS = "\
    alsa-lib \
    alsa-utils \
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-audio', 'audiodlkm  init-audio', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-audio-ar', 'agm agm-client agm-alsa-plugin agm-server agm-sndparser init-audio', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-audio-ar', bb.utils.contains('MACHINE_FEATURES', 'qti-hypervisor', '', 'ar-audiodlkm', d), '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-audio-ar', oe.utils.version_less_or_equal('${preferred-kernel}', '5.14', '', 'ar-audiodlkm', d), '', d)} \
"

KMD_RDEPENDS:append:qti-dpk = " \
    agm-tinyalsa-plugin \
    ar-pal \
    pal-control-plugin \
    system-media \
    tinyalsa \
    tinycompress \
"

AUDIOLITE_RDEPENDS = "\
    audiolite-dlkm \
"

AUDIOREACH_RDEPENDS = "\
    ar2-audio-service \
    agm \
    ar-dev-plugin \
    audioreach-conf \
    ar-audio-init-service \
    audio-device-manager \
"

RDEPENDS:${PN} += "\
    ${@bb.utils.contains('COMBINED_FEATURES', 'qti-audio-aw', '${AUDIOLITE_RDEPENDS} alsa-utils', \
        bb.utils.contains('MACHINE_FEATURES', 'qti-gunyah qti-umd', '${AUDIOLITE_RDEPENDS} alsa-utils', '${KMD_RDEPENDS}', d), \
    d)} \
"
# Gen5 AUDIOREACH
RDEPENDS:${PN}:append:gen5 = " \
    ${@bb.utils.contains('COMBINED_FEATURES', 'qti-audio-ar', '${AUDIOREACH_RDEPENDS}', '', \
    d)} \
"
# =============================================================================
# LY.AU.0.3.0 wrynose porting exclusions
# NOTE: This section will be removed once the blocked recipes are unblocked.
# =============================================================================
# ar-audiodlkm: source at vendor/qcom/opensource/audio-kernel-ar has no .git
# agm sub-packages: meta-qti-automotive layer not present in LY.AU.0.3.0 workspace
# AUDIOREACH stack (ar-dev-plugin etc.): masked, no sstate available in this workspace
# audiolite-dlkm: kernel 7.x API incompatible — deferred to kernel team
RDEPENDS:${PN}:remove = "ar-audiodlkm agm agm-client agm-alsa-plugin agm-server agm-sndparser"
RDEPENDS:${PN}:remove += "ar-dev-plugin ar2-audio-service ar-audio-init-service"
RDEPENDS:${PN}:remove += "audio-device-manager audioreach-conf ar-audio-service-utils"
AUDIOLITE_RDEPENDS:remove = "audiolite-dlkm"
