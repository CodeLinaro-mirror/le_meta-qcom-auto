SUMMARY = "Android utils library for C"
DESCRIPTION = "This library provides set of fundamental routines which are \
essential to basically any Unix utility or daemon application written in C."
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://NOTICE;md5=9645f39e9db895a4aa6e02cb57294595"

DEPENDS += "liblog"

PR = "r1"

SRC_URI = "${CLO_LE_GIT}/platform/system/core.git;branch=lv-blast.lnx.1.1.r63-rel;protocol=${OSS_PROTO};destsuffix=system/core"
SRCREV = "e407a64ace2ee2d2b77ce3d659edbf9f92254ef6"

S = "${WORKDIR}/system/core/libcutils"

inherit autotools pkgconfig

EXTRA_OECONF += "\
    --with-core-includes=${WORKDIR}/system/core/include \
    --with-host-os=${HOST_OS} \
    --disable-static \
    LE_PROPERTIES_ENABLED=true \
"

EXTRA_OECONF:append:class-native = " --with-glib"

do_install:append() {
    ln -sf ../private/android_filesystem_capability.h ${D}${includedir}/cutils/android_filesystem_capability.h
    ln -sf ../private/android_filesystem_config.h ${D}${includedir}/cutils/android_filesystem_config.h
}

BBCLASSEXTEND = "native"
