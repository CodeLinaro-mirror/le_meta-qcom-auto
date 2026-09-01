SUMMARY = "Provide display-intf Headers"
DESCRIPTION = "Provide display interface header files."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

SRC_URI = "${CLO_LA_GIT}/platform/vendor/opensource/display-intf.git;branch=display-intf.lnx.1.0.r60-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/display-intf"
SRCREV = "4fc099a351f4a74782008c1c1f04dbca5e01569c"
S = "${WORKDIR}/vendor/qcom/opensource/display-intf"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install() {
    install -d ${D}${includedir}
    install -m 644 ${S}/common/*.h ${D}${includedir}
    install -m 644 ${S}/snapalloc/*.h ${D}${includedir}
}

ALLOW_EMPTY:${PN} = "1"
