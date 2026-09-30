SUMMARY = "Git Large File Storage (LFS) extension"
DESCRIPTION = "git-lfs allows Git to handle large files via a pointer mechanism"
HOMEPAGE = "https://git-lfs.github.com"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PV = "3.4.1"

SRC_URI = "https://github.com/git-lfs/git-lfs/releases/download/v${PV}/git-lfs-linux-amd64-v${PV}.tar.gz"
SRC_URI[sha256sum] = "1772dc260961db27958088740b7e9ecebf945abad8c2d504d412448f53faf147"

S = "${WORKDIR}/git-lfs-${PV}"

do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/git-lfs ${D}${bindir}/git-lfs
}

inherit native
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_STRIP = "1"

