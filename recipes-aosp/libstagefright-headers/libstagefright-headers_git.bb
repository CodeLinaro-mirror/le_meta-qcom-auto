SUMMARY = "AOSP libstagefright/foundation headers for AOSP ReflectedParamUpdater"
DESCRIPTION = "Provide libstagefright/foundation headers for AOSP ReflectedParamUpdater, \
these headers are introduced by Android Open Source project, used for \
query and update C2Params"
HOMEPAGE = "http://developer.android.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://NOTICE;md5=a3fcbe20ea5ac731ed3aa15fe59ba20a"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/le-framework.git;branch=lv-frameworks.lnx.1.0.r61-rel;protocol=${OSS_PROTO};destsuffix=frameworks"
SRCREV = "e3f88b79c1547e39195e9374bbcc44c9d40bd191"
S = "${WORKDIR}/frameworks"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${includedir}/media/stagefright/foundation
    install -m 0644 ${S}/av/include/media/stagefright/*.h -D ${D}${includedir}/media/stagefright
    install -m 0644 ${S}/av/media/libstagefright/foundation/include/media/stagefright/foundation/*.h -D ${D}${includedir}/media/stagefright/foundation
}

ALLOW_EMPTY:${PN} = "1"
