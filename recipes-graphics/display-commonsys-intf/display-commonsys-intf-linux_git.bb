SUMMARY = "display commonsys intf Library"
DESCRIPTION = "Provide common display header files and libraries for \
other modules to use."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

DEPENDS += "libcutils libhardware-headers liblog libutils virtual/kernel-headers"

PR = "r3"

FILESEXTRAPATHS:prepend := "${THISDIR}/display-commonsys-intf-linux:"

SRC_URI = "${PATH_TO_REPO}/${TARGET_DIR}vendor/qcom/opensource/commonsys-intf/display/.git;protocol=${PROTO};destsuffix=${TARGET_DIR}vendor/qcom/opensource/commonsys-intf/display;usehead=1 \
           file://0001-configure-support-sanitized-kernel-headers.patch \
"

SRCREV = "${AUTOREV}"


S = "${UNPACKDIR}/${TARGET_DIR}vendor/qcom/opensource/commonsys-intf/display"


inherit autotools pkgconfig

EXTRA_OECONF += "--with-sanitized-headers=${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel}"

LDFLAGS += "-llog -lutils -lcutils"

CPPFLAGS += "-DTARGET_HEADLESS"
CPPFLAGS += "-DVENUS_COLOR_FORMAT"
CPPFLAGS += "-DPAGE_SIZE=4096"
CPPFLAGS += "-I${UNPACKDIR}/${TARGET_DIR}vendor/qcom/opensource/commonsys-intf/display/gralloc"
CPPFLAGS += "-I${UNPACKDIR}/${TARGET_DIR}vendor/qcom/opensource/commonsys-intf/display/libqdmetadata"
CPPFLAGS += "-I${UNPACKDIR}/${TARGET_DIR}vendor/qcom/opensource/commonsys-intf/display/include"

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
