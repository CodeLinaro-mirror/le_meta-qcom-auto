SUMMARY = "Audiolite device tree overlay"
DESCRIPTION = "Audiolite device tree overlay"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

DEPENDS += "bison-native oot-dtbo virtual/kernel-headers"

SRC_URI = "\
    ${PATH_TO_REPO}/vendor/qcom/opensource/audiolite/.git;protocol=${PROTO};usehead=1;destsuffix=vendor/qcom/opensource/audiolite \
"
SRCREV = "${AUTOREV}"

S = "${UNPACKDIR}/vendor/qcom/opensource/audiolite/devicetree"

inherit qti-techpack

EXTRA_OEMAKE += "\
    ${@bb.utils.contains('TARGET_USES_AUDIO_FRAMEWORK', 'audiolite', 'ENABLE_AUDIOLITE_OVERLAY=yes', '', d)} \
"

do_compile:prepend() {
    # Kernel 7.x hardcodes DTC_INCLUDE to scripts/dtc/include-prefixes.
    # Symlink audiolite-dt-bindings there so #includes in the dtso resolve.
    ln -sf ${S}/audiolite-dt-bindings \
        ${STAGING_KERNEL_DIR}/scripts/dtc/include-prefixes/audiolite-dt-bindings || true
    # Makefile.bbbuild passes DTC_INCLUDE="${AUDIOLITE_DTC_INCLUDE}" which
    # overrides the kernel's hardcoded path with an empty string. Fix it to
    # use the kernel's own include-prefixes directory.
    sed -i 's|DTC_INCLUDE="${AUDIOLITE_DTC_INCLUDE}"|DTC_INCLUDE="$(KERNEL_SRC)/scripts/dtc/include-prefixes"|' \
        ${S}/Makefile.bbbuild || true
}

do_compile:prepend:gen5() {
    # For gen5 (sa8797p) only build the sa8797p dtbo — other SoC dtsos
    # have missing includes that fail on this kernel. Rewrite Kbuild to
    # only list sa8797p targets.
    cat > ${S}/Kbuild << 'KBUILD_EOF'
dtbo-y += sa8797p_audiolite_common.dtbo
always-y := $(dtbo-y)
clean-files := *.dtb *.dtbo
KBUILD_EOF
}

TECHPACK_DTBS = "\
    sa8775p_audiolite_common.dtbo \
    ${@bb.utils.contains('TARGET_USES_AUDIO_FRAMEWORK', 'audiolite', 'sa8775p_audiolite_overlay.dtbo', '', d)} \
    sa8255p_audiolite_common.dtbo \
    ${@bb.utils.contains('TARGET_USES_AUDIO_FRAMEWORK', 'audiolite', 'sa8255p_audiolite_overlay.dtbo', '', d)} \
    sa8797p_audiolite_common.dtbo \
    ${@bb.utils.contains('TARGET_USES_AUDIO_FRAMEWORK', 'audiolite', 'sa8797p_audiolite_overlay.dtbo', '', d)} \
    sa8787p_audiolite_common.dtbo \
    ${@bb.utils.contains('TARGET_USES_AUDIO_FRAMEWORK', 'audiolite', 'sa8787p_audiolite_overlay.dtbo', '', d)} \
"

TECHPACK_DTBS:gen5 = "\
    sa8797p_audiolite_common.dtbo \
    ${@bb.utils.contains('TARGET_USES_AUDIO_FRAMEWORK', 'audiolite', 'sa8797p_audiolite_overlay.dtbo', '', d)} \
"
