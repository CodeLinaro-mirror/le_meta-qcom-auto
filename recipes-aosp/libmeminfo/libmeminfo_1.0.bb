SUMMARY = "Libmeminfo utility"
DESCRIPTION = "Build Android libdmabufinfo tools for LV"
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"
DEPENDS += "virtual/kernel-headers libbase libprocinfo "

SRC_URI = "${CLO_LA_GIT}/platform/system/memory/libmeminfo;protocol=https;branch=aosp-new/master;destsuffix=src/system/memory/libmeminfo \
           file://0001-libmeminfo-add-automake.patch \
           file://0001-libmeminfo-solve-build-issues-under-yocto.patch \
           "

SRCREV = "9a8f5952d95c704431bc477f7f0228db47a17ae9"

S = "${UNPACKDIR}/src/system/memory/libmeminfo"

inherit autotools pkgconfig

EXTRA_OECONF:append = " \
    --disable-static \
"

# GCC 16 / C++20 no longer pulls these in transitively (uint64_t, ULLONG_MAX,
# std::find_if/find/sort). Force-include instead of patching each source.
CXXFLAGS:append = " -include cstdint -include climits -include algorithm"
