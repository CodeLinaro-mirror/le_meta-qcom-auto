FILESEXTRAPATHS:prepend := "${THISDIR}/files:${THISDIR}/${BPN}-${PV}:"
# Oryon-1 GCC patch dropped — Oryon-1 support (-mcpu=oryon-1) is upstream in GCC 14+
# GCC 16.1 already includes it.
#
# Sanitizer patches also dropped for GCC 16.1:
#   0001-Fix-sanitizer-compile-error.patch  — AM_LIBTOOLFLAGS=--tag=CC is already
#     present in GCC 16.1 libsanitizer/{asan,hwasan,tsan}/Makefile.in (line ~219).
#   0001-asan-forbid-allocator64.patch — patch context no longer matches GCC 16.1;
#     sanitizer_platform.h was refactored upstream (SANITIZER_CAN_USE_ALLOCATOR64
#     block moved and restructured). aarch64 ASAN with Allocator64 is supported
#     upstream in GCC 16.1.
