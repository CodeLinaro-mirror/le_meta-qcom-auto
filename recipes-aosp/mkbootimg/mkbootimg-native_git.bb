SUMMARY = "Tool used for creating boot image"
DESCRIPTION = "Python tool to generate boot imgages for LRH targets"
HOMEPAGE = "https://android.googlesource.com/platform/system/tools/mkbootimg"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

PROVIDES = "mkbootimg-native"



SRCPROJECT  = "${CLO_LE_GIT}/platform/system/core.git"
SRCBRANCH  = "lv-blast.lnx.1.1.r57-rel"
SRCREV  = "004f355bc763dd7df18b650afe34c42b566c1545"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=system/core;"

S = "${WORKDIR}/system/core/mkbootimg"

inherit native

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}/${bindir}/scripts/
    install -m 0755 ${S}/mkbootimg.py ${D}/${bindir}/scripts/
    install -d ${D}/${bindir}/scripts/gki/
    install -m 0755 ${S}/gki/generate_gki_certificate.py ${D}/${bindir}/scripts/gki/
    install -d ${D}/${bindir}/scripts/pil_tools/
    install -m 0755 ${S}/pil_tools/elf_tools.py ${D}/${bindir}/scripts/pil_tools/
    install -m 0755 ${S}/pil_tools/image_header.py ${D}/${bindir}/scripts/pil_tools/
    install -m 0755 ${S}/pil_tools/pil-splitter.py ${D}/${bindir}/scripts/pil_tools/
}

