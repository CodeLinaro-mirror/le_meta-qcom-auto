SUMMARY = "scm-user-intf-test"
DESCRIPTION = "Unit tests for the scm_user_intf driver (/dev/scmnode)"
HOMEPAGE = "https://git.codelinaro.org/"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a"
DEPENDS += "safelinux-cfg-modules virtual/kernel-headers"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/safelinux-services.git;branch=safe-services.lnx.1.0.r24-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/safelinux-services"
SRCREV = "148038b08b4a8669794482a6a0b115dd29492e35"

S = "${WORKDIR}/vendor/qcom/opensource/safelinux-services/scm-user-intf-test"

inherit pkgconfig cmake
