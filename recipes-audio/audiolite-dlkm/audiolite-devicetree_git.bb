SUMMARY = "Audiolite device tree overlay"
DESCRIPTION = "Audiolite device tree overlay"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

DEPENDS += "bison-native oot-dtbo virtual/kernel-headers"



SRCPROJECT  = "git://${OSS_REPO}/clo/la/platform/vendor/qcom-opensource/audiolite.git"
SRCBRANCH  = "audiolite.lnx.1.0.r30-rel"
SRCREV  = "1c963f721d336bcb102a562bcac0ede922f2f35d"

SRC_URI = "\
    ${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/audiolite; \
"

S = "${WORKDIR}/vendor/qcom/opensource/audiolite/devicetree"

inherit qti-techpack

EXTRA_OEMAKE += "\
    AUDIOLITE_DTC_INCLUDE=${STAGING_INCDIR}\ ${STAGING_KERNEL_DIR}/include\ ${S} \
    ${@bb.utils.contains('TARGET_USES_AUDIO_FRAMEWORK', 'audiolite', 'ENABLE_AUDIOLITE_OVERLAY=yes', '', d)} \
"

TECHPACK_DTBS = "\
    sa8775p_audiolite_common.dtbo \
    ${@bb.utils.contains('TARGET_USES_AUDIO_FRAMEWORK', 'audiolite', 'sa8775p_audiolite_overlay.dtbo', '', d)} \
    sa8255p_audiolite_common.dtbo \
    ${@bb.utils.contains('TARGET_USES_AUDIO_FRAMEWORK', 'audiolite', 'sa8255p_audiolite_overlay.dtbo', '', d)} \
    sa8797p_audiolite_common.dtbo \
    ${@bb.utils.contains('TARGET_USES_AUDIO_FRAMEWORK', 'audiolite', 'sa8797p_audiolite_overlay.dtbo', '', d)} \
"
