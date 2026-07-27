SUMMARY = "Android library for fs-mgr"
DESCRIPTION = "fs-mgr provides an interface for filesystem management. \
The fs-mgr interface allows for querying the filesystem, mounting and \
unmounting, and other functionality."
HOMEPAGE = "https://www.codelinaro.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS += "logwrapper libmincrypt ext4-utils glib-2.0"

SRC_URI = "${PATH_TO_REPO}/system/core/.git;protocol=${PROTO};destsuffix=system/core;usehead=1"
SRCREV = "${AUTOREV}"

S = "${UNPACKDIR}/system/core/fs_mgr"

inherit autotools pkgconfig

EXTRA_OECONF += "--with-glib"

# glibc 2.38+ hides the transitional LFS64 API (lseek64/pread64/pwrite64)
# unless _LARGEFILE64_SOURCE is defined. Re-expose it instead of patching.
CFLAGS:append = " -D_LARGEFILE64_SOURCE"

BBCLASSEXTEND = "native"

PACKAGE_BEFORE_PN = "${PN}-utils"
FILES:${PN}-utils = "${bindir}/fs_mgr"
