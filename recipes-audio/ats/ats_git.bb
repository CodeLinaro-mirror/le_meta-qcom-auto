SUMMARY = "AudioReach Transport Service"
DESCRIPTION = "This is the library used to transport service to QACT."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "\
            acdb ar-osal glib-2.0 gsl diag \
"
SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/args.git;branch=audio-core-auto.lnx.2.0.r5-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/args/acdb/ats;subpath=acdb/ats"
SRCREV = "71c353f0b4427f1d33a7a5e7da374c7be062d74d"
S = "${WORKDIR}/vendor/qcom/opensource/args/acdb/ats"

inherit pkgconfig cmake

EXTRA_OECONF += "\
    --with-libdiag=yes \
    --with-diag=${STAGING_INCDIR}/diag \
    --with-glib \
"

RDEPENDS:${PN} += "\
    ar-osal \
    diagservice diag-lsm \
"

EXTRA_OECMAKE:append:gen5 = " -DPLATFORM_LY=ON"

CFLAGS += "-I${STAGING_INCDIR}/diag_lsm/include"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
PACKAGE_ARCH = "${MACHINE_ARCH}"
