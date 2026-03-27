SUMMARY = "display commonsys intf Library"
DESCRIPTION = "Provide common display header files and libraries for \
other modules to use."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

DEPENDS += "libcutils libhardware-headers liblog libutils virtual/kernel-headers"

PR = "r3"



SRCPROJECT  = "${CLO_LA_GIT}/platform/vendor/qcom-opensource/display-commonsys-intf.git"
SRCBRANCH  = "display-sysintf.lnx.14.0.r23-rel"
SRCREV  = "683fa7d92f46e2234ba39b4a77fc5e3ba5e238d6"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/commonsys-intf/display;"
S = "${WORKDIR}/vendor/qcom/opensource/commonsys-intf/display"

inherit autotools pkgconfig

EXTRA_OECONF += "--with-sanitized-headers=${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel}"
EXTRA_OECONF:remove:gen5 = "--with-sanitized-headers=${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel}"

LDFLAGS += "-llog -lutils -lcutils"

CPPFLAGS += "-DTARGET_HEADLESS"
CPPFLAGS += "-DVENUS_COLOR_FORMAT"
CPPFLAGS += "-DPAGE_SIZE=4096"
CPPFLAGS += "-I${WORKDIR}/vendor/qcom/opensource/commonsys-intf/display/gralloc"
CPPFLAGS += "-I${WORKDIR}/vendor/qcom/opensource/commonsys-intf/display/libqdmetadata"
CPPFLAGS += "-I${WORKDIR}/vendor/qcom/opensource/commonsys-intf/display/include"

do_install:append() {
    install -d ${D}${includedir}
    install -m 644 ${S}/gralloc/*.h ${D}${includedir}
    install -m 644 ${S}/include/*.h ${D}${includedir}
}

do_install:append:gen5() {
    rm -f ${D}${includedir}/color_extensions.h
}

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
