SUMMARY = "Sound Card Info Utils"
DESCRIPTION = "This is the Sound Card Info Utils class which provides the sound card information to the Virtio audio device "
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "expat audio-headers-export audio-log-util virtual/kernel-headers sound-card-info-common-header"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/audio-virtio-be.git;branch=audio-auto-virtio-snd-be.lnx.1.0.r5-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/audio-virtio-be/sound_card_info_util;subpath=sound_card_info_util"
SRCREV = "df54461e0ff2b522243fd0d0c4e2fb8ba57f6636"

S = "${WORKDIR}/vendor/qcom/opensource/audio-virtio-be/sound_card_info_util/ar_sound_card_info_util"

inherit cmake pkgconfig

CFLAGS += "-I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel}"

do_install:append() {
    install -d ${D}${sysconfdir}/sciu_cfg
    install -m 0644 ${S}/libs/xml_parser/configs/card_defs.xml ${D}${sysconfdir}/sciu_cfg/card_defs.xml
    install -m 0644 ${S}/libs/xml_parser/configs/ctl_extns.xml ${D}${sysconfdir}/sciu_cfg/ctl_extns.xml
    install -m 0644 ${S}/libs/xml_parser/configs/backends.xml ${D}${sysconfdir}/sciu_cfg/backends.xml
}

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
