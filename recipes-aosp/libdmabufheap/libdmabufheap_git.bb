SUMMARY = "Library for memory allocator functions for dmabufheap"
DESCRIPTION = "Build Android libdmabufheap library for LV. \
DMA-BUF heaps is an alternative for ION that has made it into the upstream kernel. \
which offers greater control over who in user space \
can perform allocations by using SELinux policies "
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS += "libbase libion virtual/kernel-headers"

SRC_URI = "${CLO_LE_GIT}/platform/system/memory/libdmabufheap.git;branch=memory-le-apps.lnx.1.0.r35-rel;protocol=${OSS_PROTO};destsuffix=src/system/memory/libdmabufheap"
SRCREV = "42ee7943f7ea71588dea3a507fd0e13b7230e472"

S = "${WORKDIR}/src/system/memory/libdmabufheap"

inherit autotools-brokensep pkgconfig

CPPFLAGS += "-I${STAGING_INCDIR}/ion_headers"

EXTRA_OECONF:append = " --with-sanitized-headers=${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel}/"

PACKAGE_ARCH = "${MACHINE_ARCH}"
