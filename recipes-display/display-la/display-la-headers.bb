SUMMARY = "Provide display la Headers"
DESCRIPTION = "Provide display LA header files."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"



SRCPROJECT  = "${CLO_LA_GIT}/platform/hardware/qcom/display.git"
SRCBRANCH  = "display-la.lnx.16.5.r10-rel"
SRCREV  = "decab9449da8b7c3e7301b4d256065cdbb7ecfad"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=hardware/qcom/display;"
S = "${WORKDIR}/hardware/qcom/display"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install() {
    install -d ${D}${includedir}
    install -m 644 ${S}/libqservice/*.h ${D}${includedir}
}

ALLOW_EMPTY:${PN} = "1"
