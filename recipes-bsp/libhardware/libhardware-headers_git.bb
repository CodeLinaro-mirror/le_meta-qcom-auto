SUMMARY = "Android libhardware library headers"
DESCRIPTION = "Headers files for the Android libhardware HAL(Hardware Abstraction Layer) interfaces"
HOMEPAGE = "http://git.codelinaro.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://NOTICE;md5=9645f39e9db895a4aa6e02cb57294595"

DEPENDS += "system-core-headers"

SRC_URI = "\
    ${CLO_LE_GIT}/platform/hardware/libhardware.git;branch=lv-blast.lnx.1.1.r63-rel;protocol=${OSS_PROTO};destsuffix=hardware/libhardware \
"
SRCREV = "c4e4351f6cf886204735474c3fcc1b992d476fbc"

S = "${WORKDIR}/hardware/libhardware"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${includedir}/hardware/
    install -m 0644 ${S}/include/hardware/*.h ${D}${includedir}/hardware/
}

BBCLASSEXTEND = "native"
