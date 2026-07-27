SUMMARY = "libprocinfo utility"
DESCRIPTION = "libprocinfo provide API for parsing /proc info"
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0 & BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
LIC_FILES_CHKSUM += "file://${COREBASE}/meta/files/common-licenses/\
BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a"
DEPENDS = "libbase libcutils libutils gtest"

SRC_URI = "${CLO_LA_GIT}/platform/system/libprocinfo;name=native;protocol=https;branch=aosp-new/master \
           file://0001-libprocinfo-add-automake.patch \
           file://0001-libprocinfo-solve-build-issues-under-yocto.patch \
          "

SRCREV = "e8bf00215cb2175a87c38deb9ae3ff5a441ffd18"


inherit autotools pkgconfig

# GCC 16 / C++20 no longer pulls in uint64_t transitively (process.h).
# Force-include <cstdint> instead of patching the header.
CXXFLAGS:append = " -include cstdint"

