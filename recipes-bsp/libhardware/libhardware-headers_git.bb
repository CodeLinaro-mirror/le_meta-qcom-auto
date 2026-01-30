SUMMARY = "Android libhardware library headers"
DESCRIPTION = "Headers files for the Android libhardware HAL(Hardware Abstraction Layer) interfaces"
HOMEPAGE = "http://git.codelinaro.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://NOTICE;md5=9645f39e9db895a4aa6e02cb57294595"

DEPENDS += "system-core-headers"



SRCPROJECT  = "git://${OSS_REPO}/clo/le/platform/hardware/libhardware.git"
SRCBRANCH  = "lv-blast.lnx.1.1.r56-rel"
SRCREV  = "30e539c3b2dbcdd2cd21d0a7bbbb82e394ffd737"

SRC_URI = "\
    ${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=hardware/libhardware; \
    https://git.codelinaro.org/clo/la/platform/hardware/libhardware/-/raw/keystone/p-keystone-qcom-release/include/hardware/gralloc1.h;downloadfilename=gralloc1.h;name=gralloc-h \
"
SRC_URI[gralloc-h.sha256sum] = "19e9f8acac6ab89d8ec11aefa1e6e0aa6ca49b73f2c6fd17cb7bc487b5841ee6"

S = "${WORKDIR}/hardware/libhardware"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${includedir}/hardware/
    install -m 0644 ${WORKDIR}/gralloc1.h ${D}${includedir}/hardware/
    install -m 0644 ${S}/include/hardware/*.h ${D}${includedir}/hardware/
}
