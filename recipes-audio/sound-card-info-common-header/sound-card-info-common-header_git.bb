SUMMARY = "sound card info Headers Export"
DESCRIPTION = "This is sound card info header files recipe"
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/audio-virtio-be.git;branch=audio-auto-virtio-snd-be.lnx.1.0.r5-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/audio-virtio-be/sound_card_info_util_header;subpath=sound_card_info_util_header"
SRCREV = "df54461e0ff2b522243fd0d0c4e2fb8ba57f6636"

S = "${WORKDIR}/vendor/qcom/opensource/audio-virtio-be/sound_card_info_util_header"

inherit cmake pkgconfig

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
