SUMMARY = "AOSP libstagefright/foundation headers for AOSP ReflectedParamUpdater"
DESCRIPTION = "Provide libstagefright/foundation headers for AOSP ReflectedParamUpdater, \
these headers are introduced by Android Open Source project, used for \
query and update C2Params"
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://NOTICE;md5=a3fcbe20ea5ac731ed3aa15fe59ba20a"



SRCPROJECT  = "git://${OSS_REPO}/clo/le/platform/vendor/qcom-opensource/le-framework.git"
SRCBRANCH  = "lv-frameworks.lnx.1.0.r55-rel"
SRCREV  = "1805874baadb4d9e7876d0960864a0942b8d8e2e"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=frameworks;"
S = "${WORKDIR}/frameworks"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${includedir}/media/stagefright/foundation
    install -m 0644 ${S}/av/include/media/stagefright/*.h -D ${D}${includedir}/media/stagefright
    install -m 0644 ${S}/av/media/libstagefright/foundation/include/media/stagefright/foundation/*.h -D ${D}${includedir}/media/stagefright/foundation
}

ALLOW_EMPTY:${PN} = "1"
