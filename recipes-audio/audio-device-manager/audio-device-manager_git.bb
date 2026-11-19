SUMMARY = "Audio Device Manager"
DESCRIPTION = "This library manages the interactions of registering the sound card, and receiving and sending device controls from/to the Virtio audio driver"
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "libuhab audio-log-util sound-card-info-common-header compute-resmgr"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/audio-virtio-be.git;branch=audio-auto-virtio-snd-be.lnx.1.0.r5-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/audio-virtio-be/audio_device_manager;subpath=audio_device_manager"
SRCREV = "38030259bf8a19b0397a0b0953f59e0f5baac09e"

S = "${WORKDIR}/vendor/qcom/opensource/audio-virtio-be/audio_device_manager"

CXXFLAGS += "-I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel}"

inherit cmake pkgconfig

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
RDEPENDS:${PN} += "audio-plugin-intf sound-card-info-util"
