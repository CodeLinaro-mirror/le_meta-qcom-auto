SUMMARY = "display Library"
DESCRIPTION = "Provide display HAL (Hardware Abstraction Layer) \
libraries. These libraries serves as an abstraction layer between \
physical hardware and software. They provide display driver interfaces, \
allowing program to communicate with the hardware."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

DEPENDS += "display-commonsys-intf-linux \
            drm \
            gbm-headers \
            libdrm \
            libhardware \
            virtual/kernel-headers \
            system-core \
            ${@bb.utils.contains_any("PREFERRED_VERSION_linux-msm", '5.15 6.1', 'displaydlkm', '', d)} \
            ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', 'display-kernel-headers', '', d)} \
            ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', 'compute-resmgr', '', d)} \
"

DEPENDS:append:gen5 = " display-kernel-headers display-intf-headers"

PR = "r8"

DISPLAY_DIR = "${@bb.utils.contains_any('PREFERRED_PROVIDER_virtual/kernel', 'linux-qcom-custom linux-qcom-custom-rt',"vendor/qcom/opensource/display-core", "display/display-hal", d)}"
DISPLAY_DIR:sa8775 = "display/display-hal"
DISPLAY_DIR:sa7255 = "display/display-hal"

SRC_URI = "${CLO_LA_GIT}/platform/vendor/opensource/display-core.git;branch=display.lnx.12.5.r16-rel;protocol=${OSS_PROTO};destsuffix=${DISPLAY_DIR}"
SRC_URI:sa8775-flex = "${CLO_LA_GIT}/platform/hardware/qcom/gen4-5/display.git;branch=display_gen4-5.lnx.5.1.2.r2-rel;protocol=${OSS_PROTO};destsuffix=${DISPLAY_DIR}"
SRCREV = "345f79e722d5d716a603a0636960f8946b0c1c03"
SRCREV:sa8775-flex = "2290c05832da3ee2e5575de5cb3e1063556d9b8f"
S = "${WORKDIR}/${DISPLAY_DIR}"

inherit autotools-brokensep pkgconfig

EXTRA_OECONF += "--with-sanitized-headers=${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel}"
EXTRA_OECONF += "--enable-sdmhaldrm"
EXTRA_OECONF:append:gen5 = " ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', '--enable-rt-schedule', '--disable-rt-schedule', d)}"

LDFLAGS += "-llog -lhardware -lutils -lcutils"

CPPFLAGS += "-DCOMPILE_DRM"
CPPFLAGS += "-DVENUS_COLOR_FORMAT"
CPPFLAGS += "-DPAGE_SIZE=4096"
CPPFLAGS += "-I${WORKDIR}/${DISPLAY_DIR}/include"
CPPFLAGS += "-I${WORKDIR}/${DISPLAY_DIR}/sdm/include"
CPPFLAGS += "-I${WORKDIR}/${DISPLAY_DIR}/libdebug"
CPPFLAGS += "-I${WORKDIR}/${DISPLAY_DIR}/libdrmutils"
CPPFLAGS += "-I${WORKDIR}/${DISPLAY_DIR}/gpu_tonemapper"
CPPFLAGS += "-I${WORKDIR}/${DISPLAY_DIR}/libqdutils"
CPPFLAGS += "-I${WORKDIR}/${DISPLAY_DIR}/libqservice"
CPPFLAGS += "-I${STAGING_INCDIR}/libdrm"

CPPFLAGS:append:gen5 = " -DDEMURA_STAND_ALONE"
CPPFLAGS:append:sa8775 = " -DTARGET_HEADLESS"
CPPFLAGS:append:sa7255 = " -DTARGET_HEADLESS"

# fix for uapi msm_drm.h header file related compilation issue
CPPFLAGS += "-fno-operator-names"

# add display techpack headers
CPPFLAGS += "-I${STAGING_INCDIR}/${PREFERRED_PROVIDER_virtual/kernel}/display"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
