SUMMARY = "Weston SDM Extension Headers"
DESCRIPTION = "Provides QTI specific header files. This package is isolated from weston-sdm-extension \
so that weston could depend on it. See weston-sdm-extension for more information."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause & MIT & BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9 \
                    file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a"
CODE_DIR = "${@bb.utils.contains_any('PREFERRED_PROVIDER_virtual/kernel', 'linux-qcom-custom linux-qcom-custom-rt',"vendor/qcom/opensource/display/weston-sdm-extension", "graphics/weston-sdm-extension", d)}"
CODE_DIR:sa8775 = "graphics/weston-sdm-extension"
CODE_DIR:sa7255 = "graphics/weston-sdm-extension"

SRC_URI = "${CLO_LE_GIT}/graphics/weston-sdm-extension.git;branch=display-le.lnx.6.5.r8-rel;protocol=${OSS_PROTO};destsuffix=${CODE_DIR}"
SRC_URI:sa8775-flex = "${CLO_LE_GIT}/graphics/gen4-5/weston-sdm-extension.git;branch=display-userspace_gen4-5.lnx.3.2.r2-rel;protocol=${OSS_PROTO};destsuffix=${CODE_DIR}"
SRCREV = "e3a228821c772f7e8e2e3a04a46ee9b182e20cf2"
SRCREV:sa8775-flex = "a4eefe71996d8a712b3ed4cf59c4360b42e7d69e"
S = "${WORKDIR}/${CODE_DIR}"

PREBUILT = "1"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install(){
    install -d ${D}${includedir}
    install -m 0644 ${S}/include/*.h ${D}${includedir}
}

ALLOW_EMPTY:${PN} = "1"
