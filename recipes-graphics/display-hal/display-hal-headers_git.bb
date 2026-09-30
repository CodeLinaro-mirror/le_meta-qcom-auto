SUMMARY = "Provide display-hal Headers"
DESCRIPTION = "Provide display Hardware Abstraction Layer header \
files. See display-hal-linux_git.bb for more information."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

DISPLAY_DIR = "${@bb.utils.contains_any('PREFERRED_PROVIDER_virtual/kernel', 'linux-qcom-custom linux-qcom-custom-rt',"vendor/qcom/opensource/display-core", "display/display-hal", d)}"
DISPLAY_DIR:sa8775 = "display/display-hal"
DISPLAY_DIR:sa7255 = "display/display-hal"

SRC_URI = "${CLO_LA_GIT}/platform/vendor/opensource/display-core.git;branch=display.lnx.12.5.r16-rel;protocol=${OSS_PROTO};destsuffix=${DISPLAY_DIR}"
SRC_URI:sa8775-flex = "${CLO_LA_GIT}/platform/hardware/qcom/gen4-5/display.git;branch=display_gen4-5.lnx.5.1.2.r2-rel;protocol=${OSS_PROTO};destsuffix=${DISPLAY_DIR}"
SRCREV = "f12f2a280e8e9686d716336d585eb086faa1a1e5"
SRCREV:sa8775-flex = "2290c05832da3ee2e5575de5cb3e1063556d9b8f"
S = "${WORKDIR}/${DISPLAY_DIR}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install() {
    install -d ${D}${includedir}
    install -m 644 ${S}/include/*.h ${D}${includedir}
    if ${@bb.utils.contains_any('PREFERRED_PROVIDER_virtual/kernel', 'linux-qcom-custom linux-qcom-custom-rt', 'false', 'true', d)}; then
      if ls ${S}/libqservice/*.h >/dev/null 2>&1; then
         install -m 644 ${S}/libqservice/*.h ${D}${includedir}
      fi
    fi
}

ALLOW_EMPTY:${PN} = "1"
