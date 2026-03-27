SUMMARY = "Android libhardware library"
DESCRIPTION = "This library provides access to the Android libhardware HAL(Hardware Abstraction Layer)."
HOMEPAGE = "http://git.codelinaro.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://NOTICE;md5=9645f39e9db895a4aa6e02cb57294595"

DEPENDS += "libcutils liblog libutils system-core-headers"



SRCPROJECT  = "${CLO_LE_GIT}/platform/hardware/libhardware.git"
SRCBRANCH  = "lv-blast.lnx.1.1.r57-rel"
SRCREV  = "30e539c3b2dbcdd2cd21d0a7bbbb82e394ffd737"

SRC_URI = "\
    ${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=hardware/libhardware; \
    https://git.codelinaro.org/clo/la/platform/hardware/libhardware/-/raw/keystone/p-keystone-qcom-release/include/hardware/gralloc1.h;downloadfilename=gralloc1.h;name=gralloc-h \
"
SRC_URI[gralloc-h.sha256sum] = "19e9f8acac6ab89d8ec11aefa1e6e0aa6ca49b73f2c6fd17cb7bc487b5841ee6"

S = "${WORKDIR}/hardware/libhardware"

inherit autotools pkgconfig

do_install:append () {
    # remove headers, use libhardware-headers
    rm -rf ${D}${includedir}/hardware/
}
