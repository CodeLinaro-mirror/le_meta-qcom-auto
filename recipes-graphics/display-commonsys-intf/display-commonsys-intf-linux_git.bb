SUMMARY = "display commonsys intf Library"
DESCRIPTION = "Provide common display header files and libraries for \
other modules to use."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

DEPENDS += "libcutils libhardware-headers liblog libutils virtual/kernel-headers"

PR = "r3"

SRC_URI = "${CLO_LA_GIT}/platform/vendor/qcom-opensource/display-commonsys-intf.git;branch=display-sysintf.lnx.14.0.r27-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/commonsys-intf/display"
SRC_URI:sa8775-flex = "${CLO_LA_GIT}/platform/vendor/qcom-opensource/gen4-5/display-commonsys-intf.git;branch=display-android-commonsys_gen4-5.lnx.1.0.r2-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/commonsys-intf/display"

SRCREV = "ed017f86c7348e3a4b1d9b8087e4073982c4b44e"


SRCREV:sa8775-flex = "992d69ea4f36455fb57aba066a6e2f014cbd70d8"
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

do_install:append:gvm-gen5() {
    rm -f ${D}${includedir}/color_extensions.h
}

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
