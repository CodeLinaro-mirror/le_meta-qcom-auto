SUMMARY = "Android ext4-utils tools"
DESCRIPTION = "Command line tools to make sparse images from ext4 file system \
images and android images(.img) with ext4 file systems. This package contains \
tools like mkuserimg, ext4fixup and make_ext4fs tools."
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://NOTICE;md5=bb2810bf31da2f6bb39e0bfa86091da3"

DEPENDS += "libcutils libpcre libsparse"

PR = "r1"



SRCPROJECT  = "git://${OSS_REPO}/clo/le/platform/system/extras.git"
SRCBRANCH  = "lv-blast.lnx.1.1.r56-rel"
SRCREV  = "3e001cf18d002daeed49334f0c10dc73988898fe"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=system/extras;"

S = "${WORKDIR}/system/extras/ext4_utils"

inherit autotools pkgconfig

PACKAGECONFIG:class-target ?= "${@bb.utils.filter('DISTRO_FEATURES', 'selinux', d)}"
PACKAGECONFIG:class-native ?= "${@bb.utils.filter('DISTRO_FEATURES_NATIVE', 'selinux', d)}"
PACKAGECONFIG[selinux] = "--enable-selinux,--disable-selinux,libselinux"

CPPFLAGS:append = " -I${STAGING_INCDIR}/cutils"

BBCLASSEXTEND = "native"

MACHINEOVERRIDES:class-native = "${MACHINE}:${SOC_FAMILY}"
