SUMMARY = "Android core component headers"
DESCRIPTION = "The system/core directory is intended for pieces of the world that are \
the core of the embedded linux platform at the heart of Android. These essential bits \
are required for basic booting, operation, and debugging."
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://NOTICE;md5=c1a3ff0b97f199c7ebcfdd4d3fed238e"



SRCPROJECT  = "git://${OSS_REPO}/clo/le/platform/system/core.git"
SRCBRANCH  = "lv-blast.lnx.1.1.r56-rel"
SRCREV  = "0d3d6f588509bdd67606fb783d50b5dd415562e6"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=system/core;"

S = "${WORKDIR}/system/core"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    # export head files
    install -d ${D}${includedir}/system
    install -m 0644 ${S}/include/system/camera.h  ${D}${includedir}/system/
    install -m 0644 ${S}/include/system/graphics.h  ${D}${includedir}/system/
    install -m 0644 ${S}/include/system/thread_defs.h  ${D}${includedir}/system/
    install -m 0644 ${S}/include/system/window.h  ${D}${includedir}/system/

    install -d ${D}${includedir}/sys
    install -m 0644 ${S}/include/sys/system_properties.h  ${D}${includedir}/sys/

    install -d ${D}${includedir}/netutils
    install -m 0644 ${S}/include/netutils/dhcp.h  ${D}${includedir}/netutils/
    install -m 0644 ${S}/include/netutils/ifc.h  ${D}${includedir}/netutils/
}
