SUMMARY = "AOSP Build Tools"
DESCRIPTION = "Kernel AOSP build Tools for create boot image"
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

BASE_GIT_PATH = "${@'${PATH_TO_REPO}/kernel/kernel-%s/kernel_platform' % d.getVar('PREFERRED_VERSION_linux-msm') if d.getVar('PREFERRED_VERSION_linux-msm') else '${PATH_TO_REPO}/kernel_platform'}"
BASE_PATH = "${@'kernel/kernel-%s/kernel_platform' % d.getVar('PREFERRED_VERSION_linux-msm') if d.getVar('PREFERRED_VERSION_linux-msm') else 'kernel_platform'}"

SRC_URI = "${CLO_LA_GIT}/kernel/build.git;branch=kernel.qclinux.1.0.r16-rel;protocol=${OSS_PROTO};name=kernel-build;destsuffix=${BASE_PATH}/build/kernel \
           ${CLO_LA_GIT}/kernel/prebuilts/build-tools.git;branch=kernel-build-tools.qclinux.0.0.r16-rel;protocol=${OSS_PROTO};name=kernel-tools;destsuffix=${BASE_PATH}/prebuilts/kernel-build-tools \
           "

SRC_URI:gen5 = "${CLO_LA_GIT}/kernel/prebuilts/build-tools.git;branch=kernel-build-tools.qclinux.0.0.r16-rel;protocol=${OSS_PROTO};destsuffix=kernel/kernel_platform/prebuilts/kernel-build-tools"

SRCREV_kernel-build = "dcf8b59ea2f6f5db9b50f2b02b9fbff68f6c4b1b"
SRCREV_kernel-tools = "e979ba5e4295e4c9761e1f49b8f0ab6848ad146f"
SRCREV_FORMAT = "kernel-build_kernel-tools"
SRCREV:gen5 = "e979ba5e4295e4c9761e1f49b8f0ab6848ad146f"

S = "${WORKDIR}"

inherit deploy native

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    cd ${S}/${BASE_PATH}/build/kernel/android
    install -d ${D}/${bindir}/build/android/

    for SC_FILE in `ls *.py *.sh`; do
        install -D ${S}/${BASE_PATH}/build/kernel/android/${SC_FILE} ${D}/${bindir}/build/android/
    done

    install -d ${D}/${bindir}/build/prebuilts
    cp -rf ${S}/${BASE_PATH}/prebuilts/kernel-build-tools ${D}/${bindir}/build/prebuilts
}

do_install:gen5() {
    :
}

do_deploy() {
    :
}

do_deploy:append:gen5() {
    install -d ${DEPLOYDIR}/kernel-tools
    install -m 0755 ${S}/kernel/kernel_platform/prebuilts/kernel-build-tools/linux-x86/bin/mke2fs ${DEPLOYDIR}/kernel-tools
    install -m 0755 ${S}/kernel/kernel_platform/prebuilts/kernel-build-tools/linux-x86/bin/e2fsdroid ${DEPLOYDIR}/kernel-tools
}

addtask do_deploy after do_install

MACHINEOVERRIDES = "${MACHINE}:${SOC_FAMILY}"
