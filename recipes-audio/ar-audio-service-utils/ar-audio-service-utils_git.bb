SUMMARY = "AR Audio Service Utils"
DESCRIPTION = "This is a library which provides a variety of subroutines in init, deinit, and subsystem restart handling for the audio service of AudioReach's."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "audio-log-util audio-headers-export mm-osal libkiumd agm \
    tinyalsa-new tinycompress expat dbus libglink-client libglink-core ar-dev-plugin \
    alsa-lib ar2-acdbdata ar-osal ar-util glib-2.0 ats gsl \
    virtual/kernel-headers spf \
"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/audio-ar-service.git;branch=auto-audio-lrh.lnx.2.0.r5-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/audio-ar-service/audio_driver/ar_service_utils;subpath=audio_driver/ar_service_utils"
SRCREV = "2c7ab8382615650f3e9e154aa6377951a2b5a2e0"

S = "${WORKDIR}/vendor/qcom/opensource/audio-ar-service/audio_driver/ar_service_utils"

inherit cmake pkgconfig

CFLAGS += "\
   -I${STAGING_INCDIR}/ \
   -I${STAGING_INCDIR}/uapi \
   -I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel} \
   -I${STAGING_INCDIR}/agm/plugins/tinyalsa/test  \
"
EXTRA_OECONF += "\
    --with-glib \
"

OEM_CONF_SRC_DIR = "config/etc/audio_oem_cfg"
OEM_CONF_DEST_DIR = "/etc/audio_oem_cfg"

do_install:append:gen5() {
    install -d ${D}${OEM_CONF_DEST_DIR}/
    install -m 0666 ${S}/${OEM_CONF_SRC_DIR}/gen5/ar_prm_mclk.cfg ${D}${OEM_CONF_DEST_DIR}/
}

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
