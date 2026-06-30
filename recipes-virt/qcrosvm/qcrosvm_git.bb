SUMMARY = "QCrosVM Support"
DESCRIPTION = "It is based on google CrosVM to launch Linux based GVM on QTI platforms."
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "BSD-3-Clause-Clear & BSD-3-Clause & (Apache-2.0 | MIT) & Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a \
                    file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9 \
                    file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS += "cargo-native libcap rust-native rust-llvm-native pkgconfig-native"

SRC_URI = "\
    ${CLO_LE_GIT}/platform/vendor/qcom-opensource/crosvm-gunyah.git;branch=auto-android-core-sys.lnx.13.0.r31-rel;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/crosvm-gunyah;name=platform_vendor_qcom_opensource_crosvm_gunyah \
    ${CLO_LA_GIT}/platform/external/crosvm.git;branch=auto-vmm.lnx.1.0.r29-rel;protocol=${OSS_PROTO};destsuffix=external/crosvm;name=platform_external_crosvm \
    ${CLO_LA_GIT}/platform/external/minijail.git;branch=auto-vmm.lnx.1.0.r29-rel;protocol=${OSS_PROTO};destsuffix=external/minijail;name=platform_external_minijail \
    ${CLO_LA_GIT}/platform/external/rust/crates/android_logger.git;branch=auto-vmm.lnx.1.0.r29-rel;protocol=${OSS_PROTO};destsuffix=external/rust/crates/android_logger;name=platform_external_rust_crates_android_logger \
    ${CLO_LA_GIT}/platform/external/rust/crates/simplelog.git;branch=auto-android-core-sys.lnx.13.0.r31-rel;protocol=${OSS_PROTO};destsuffix=external/rust/crates/simplelog;name=platform_external_rust_crates_simplelog \
    ${CLO_LA_GIT}/platform/external/rust/crates/vmm_vhost.git;branch=auto-vmm.lnx.1.0.r29-rel;protocol=${OSS_PROTO};destsuffix=external/rust/crates/vmm_vhost;name=platform_external_rust_crates_vmm_vhost \
"
SRCREV_platform_vendor_qcom_opensource_crosvm_gunyah = "2511efb3b93b7dc88ee61251c7b4e62e99c8ac0b"
SRCREV_platform_external_crosvm = "4d85a154de6ff5552568a069913ae2a59e7491eb"
SRCREV_platform_external_minijail = "8cb44f9fc32f538a90b6a3f09d3941e26fe6d6f9"
SRCREV_platform_external_rust_crates_android_logger = "7f4eca1ac49d9c7fc88cfa5f547ca09c7b5eace3"
SRCREV_platform_external_rust_crates_simplelog = "b2d25e663deaa1e8294c6bf94942eca8d86ae4c2"
SRCREV_platform_external_rust_crates_vmm_vhost = "e20be590881517ac8a47008986221be81eca505c"
SRCREV_FORMAT = "platform_vendor_qcom_opensource_crosvm_gunyah_platform_external_crosvm_platform_external_minijail_platform_external_rust_crates_android_logger_platform_external_rust_crates_simplelog_platform_external_rust_crates_vmm_vhost"

S = "${WORKDIR}/vendor/qcom/opensource/crosvm-gunyah"

inherit cargo systemd cargo-update-recipe-crates

require ${BPN}-crates.inc

CARGO_BUILD_FLAGS += "${@bb.utils.contains('DISTRO_FEATURES', 'qti-qcvirtio', '--features=vhost-user-generic', '', d)}"

CFLAGS:append = " -Wno-error=stringop-overflow="

SYSTEMD_SERVICE:${PN} = "qcrosvm.service"
SYSTEMD_SERVICE:${PN}-lvgvm:append:sa7255-ivi = " qcrosvm_lv.service"
SYSTEMD_SERVICE:${PN}-lvgvm:append:sa8255-ivi = " qcrosvm_lv.service"
SYSTEMD_SERVICE:${PN}-lvgvm:append:sa8775-flex = " qcrosvm_lv.service"
SYSTEMD_PACKAGES = "${PN} ${PN}-lvgvm"

EXTRA_OECMAKE += "\
    -DENABLE_TARGET=${BASEMACHINE} \
"

VM_CONFIG_XML ?= "vm_config_la.xml"
VM_CONFIG_XML:sa8255-ivi = "vm_config_lalv.xml"
VM_CONFIG_XML:sa7255-ivi = "vm_config_lalv.xml"
VM_CONFIG_XML:sa8775-flex = "vm_config_lalv.xml"

do_install:append() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${S}/vm_config_xml/${VM_CONFIG_XML} ${D}${sysconfdir}/vm_config.xml
}

do_install:append:gen5() {
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${S}/qcrosvm_sa8797.service ${D}/${systemd_unitdir}/system/qcrosvm.service
}

do_install:append:sa8775() {
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${S}/qcrosvm.service ${D}/${systemd_unitdir}/system/qcrosvm.service
}

do_install:append:sa7255() {
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${S}/qcrosvm_sa7255.service ${D}/${systemd_unitdir}/system/qcrosvm.service
}

do_install:append:sa7255-ivi() {
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${S}/qcrosvm_lv_sa7255.service ${D}/${systemd_unitdir}/system/qcrosvm_lv.service
}

do_install:append:sa8255-ivi() {
    install -d ${D}${systemd_unitdir}/system/
    if ${@bb.utils.contains('DISTRO_FEATURES', 'qti-qcvirtio', 'true', 'false', d)}; then
        install -m 0644 ${S}/qcrosvm_lv_qcvirtio.service ${D}/${systemd_unitdir}/system/qcrosvm_lv.service
        install -d ${D}${bindir}
        install -m 0755 ${S}/qcrosvm_lv_qcvirtio.sh ${D}/${bindir}
    else
        install -m 0644 ${S}/qcrosvm_lv.service ${D}/${systemd_unitdir}/system/qcrosvm_lv.service
    fi
}

do_install:append:sa8775-flex() {
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${S}/qcrosvm_lv.service ${D}/${systemd_unitdir}/system/qcrosvm_lv.service
    install -m 0644 ${S}/vm_config_xml/vm_config_la.xml ${D}${sysconfdir}/vm_config_la.xml
    install -m 0644 ${S}/vm_config_xml/vm_config_lalv.xml ${D}${sysconfdir}/vm_config_lalv.xml
}

PACKAGES =+ "${PN}-lvgvm"

FILES:${PN}-lvgvm += "\
    ${systemd_system_unitdir}/qcrosvm_lv.service \
    ${sysconfdir}/vm_config_lalv.xml \
"

cargo_common_do_patch_paths() {
    :
}
