SUMMARY = "Audio Plugin Intf"
DESCRIPTION = "This is an interface library used by Virtio audio device to realize audio functionality"
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "sound-card-info-util agm audio-headers-export audio-log-util sound-card-info-common-header"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/audio-virtio-be.git;branch=audio-auto-virtio-snd-be.lnx.1.0.r5-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/audio-virtio-be/audio_plugin_intf;subpath=audio_plugin_intf"
SRCREV = "38030259bf8a19b0397a0b0953f59e0f5baac09e"

S = "${WORKDIR}/vendor/qcom/opensource/audio-virtio-be/audio_plugin_intf/agm_audio_plugin"

inherit cmake pkgconfig

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
