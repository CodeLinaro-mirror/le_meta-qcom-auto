SUMMARY = "Provide display-hal Headers"
DESCRIPTION = "Provide display Hardware Abstraction Layer header \
files. See display-hal-linux_git.bb for more information."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

DISPLAY_DIR = "${@bb.utils.contains_any('PREFERRED_PROVIDER_virtual/kernel', 'linux-qcom-custom linux-qcom-custom-rt',"vendor/qcom/opensource/display-core", "display/display-hal", d)}"
DISPLAY_DIR:sa8775 = "display/display-hal"
DISPLAY_DIR:sa7255 = "display/display-hal"

SRCPROJECT  = "git://${OSS_REPO}/clo/la/platform/hardware/qcom/display.git"
SRCBRANCH  = "display.lnx.5.1.2.r11-rel"
SRCREV  = "2290c05832da3ee2e5575de5cb3e1063556d9b8f"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=display/display-hal;"
S = "${WORKDIR}/display/display-hal"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install() {
    install -d ${D}${includedir}
    install -m 644 ${S}/include/*.h ${D}${includedir}
    if ${@bb.utils.contains_any('PREFERRED_PROVIDER_virtual/kernel', 'linux-qcom-custom linux-qcom-custom-rt', 'false', 'true', d)}; then
      install -m 644 ${S}/libqservice/*.h ${D}${includedir}
    fi
}

ALLOW_EMPTY:${PN} = "1"
