SUMMARY = "AudioReach configurations"
DESCRIPTION = "This project provides business entity level, business application level, and platform level kvh2xml and/or card-defs xml"
HOMEPAGE = "https://github.com/Audioreach/audioreach-conf"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/audioreach-conf.git;branch=audio-core-auto.lnx.2.0.r5-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/audioreach-conf"
SRCREV = "d6f7e7cbb4769e61fb0d4bc8bdafc898ae3e3500"
S = "${WORKDIR}/vendor/qcom/opensource/audioreach-conf"

EXTRA_OECONF += "--with-qcom"

EXTRA_OECONF:append:gen5 = " --with-automotive --with-sa8797"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit autotools pkgconfig

do_compile[noexec] = "1"

FILES_${PN} += "${sysconfdir}/card-defs.xml"
