SUMMARY = "QCrosVM Support"
DESCRIPTION = "It is based on google CrosVM to launch Linux based GVM on QTI platforms."
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "BSD-3-Clause-Clear & BSD-3-Clause & (Apache-2.0 | MIT) & Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a \
                    file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9 \
                    file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS += "cargo-native libcap rust-native rust-llvm-native pkgconfig-native"


QCOM_VMM_VHOST_SRC_bb  ?= "git://${OSS_REPO}/clo/la/platform/external/rust/crates/vmm_vhost.git"
QCOM_VMM_VHOST_SRCBRANCH_bb  ?= "auto-vmm.lnx.1.0.r22-rel"
QCOM_VMM_VHOST_SRCREV_bb  ?= "e20be590881517ac8a47008986221be81eca505c"


QCOM_SIMPLELOG_SRC_bb  ?= "git://${OSS_REPO}/clo/la/platform/external/rust/crates/simplelog.git"
QCOM_SIMPLELOG_SRCBRANCH_bb  ?= "auto-android-core-sys.lnx.13.0.r23-rel"
QCOM_SIMPLELOG_SRCREV_bb  ?= "b2d25e663deaa1e8294c6bf94942eca8d86ae4c2"


QCOM_ANDROID_LOGGER_SRC_bb  ?= "git://${OSS_REPO}/clo/la/platform/external/rust/crates/android_logger.git"
QCOM_ANDROID_LOGGER_SRCBRANCH_bb  ?= "auto-vmm.lnx.1.0.r22-rel"
QCOM_ANDROID_LOGGER_SRCREV_bb  ?= "7f4eca1ac49d9c7fc88cfa5f547ca09c7b5eace3"


QCOM_MINIJAIL_SRC_bb  ?= "git://${OSS_REPO}/clo/la/platform/external/minijail.git"
QCOM_MINIJAIL_SRCBRANCH_bb  ?= "auto-vmm.lnx.1.0.r22-rel"
QCOM_MINIJAIL_SRCREV_bb  ?= "8cb44f9fc32f538a90b6a3f09d3941e26fe6d6f9"


QCOM_CROSVM_SRC_bb  ?= "git://${OSS_REPO}/clo/la/platform/external/crosvm.git"
QCOM_CROSVM_SRCBRANCH_bb  ?= "auto-vmm.lnx.1.0.r22-rel"
QCOM_CROSVM_SRCREV_bb  ?= "abba075f0c3bc90f6409e62dec1e4dfa5813c73b"


QCOM_CROSVM_GUNYAH_SRC_bb  ?= "git://${OSS_REPO}/clo/le/platform/vendor/qcom-opensource/crosvm-gunyah.git"
QCOM_CROSVM_GUNYAH_SRCBRANCH_bb  ?= "auto-android-core-sys.lnx.13.0.r23-rel"
QCOM_CROSVM_GUNYAH_SRCREV_bb  ?= "7b8ddf3cbaefc88b91cc98675ebcff4b26d34c31"

SRCREV_FORMAT = "crosvmgunyah_crosvm_minijail_androidlogger_simplelog_vmmvhost"
SRCREV_crosvmgunyah  = "${QCOM_CROSVM_GUNYAH_SRCREV_bb}"
SRCREV_crosvm  = "${QCOM_CROSVM_SRCREV_bb}"
SRCREV_minijail  = "${QCOM_MINIJAIL_SRCREV_bb}"
SRCREV_androidlogger  = "${QCOM_ANDROID_LOGGER_SRCREV_bb}"
SRCREV_simplelog  = "${QCOM_SIMPLELOG_SRCREV_bb}"
SRCREV_vmmvhost  = "${QCOM_VMM_VHOST_SRCREV_bb}"
SRC_URI = "\
    ${QCOM_CROSVM_GUNYAH_SRC_bb};branch=${QCOM_CROSVM_GUNYAH_SRCBRANCH_bb};name=crosvmgunyah;protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/crosvm-gunyah; \
    ${QCOM_CROSVM_SRC_bb};branch=${QCOM_CROSVM_SRCBRANCH_bb};name=crosvm;protocol=${OSS_PROTO};destsuffix=external/crosvm; \
    ${QCOM_MINIJAIL_SRC_bb};branch=${QCOM_MINIJAIL_SRCBRANCH_bb};name=minijail;protocol=${OSS_PROTO};destsuffix=external/minijail; \
    ${QCOM_ANDROID_LOGGER_SRC_bb};branch=${QCOM_ANDROID_LOGGER_SRCBRANCH_bb};name=androidlogger;protocol=${OSS_PROTO};destsuffix=external/rust/crates/android_logger; \
    ${QCOM_SIMPLELOG_SRC_bb};branch=${QCOM_SIMPLELOG_SRCBRANCH_bb};name=simplelog;protocol=${OSS_PROTO};destsuffix=external/rust/crates/simplelog; \
    ${QCOM_VMM_VHOST_SRC_bb};branch=${QCOM_VMM_VHOST_SRCBRANCH_bb};name=vmmvhost;protocol=${OSS_PROTO};destsuffix=external/rust/crates/vmm_vhost; \
"


S = "${WORKDIR}/vendor/qcom/opensource/crosvm-gunyah"

inherit cargo systemd cargo-update-recipe-crates

require ${BPN}-crates.inc

CFLAGS:append = " -Wno-error=stringop-overflow="

SYSTEMD_SERVICE:${PN} = "qcrosvm.service"
SYSTEMD_SERVICE:${PN}:append:sa7255-ivi = " qcrosvm_lv.service"
SYSTEMD_SERVICE:${PN}:append:sa8255-ivi = " qcrosvm_lv.service"

EXTRA_OECMAKE += "\
    -DENABLE_TARGET=${BASEMACHINE} \
"

VM_CONFIG_XML ?= "vm_config_la.xml"
VM_CONFIG_XML:sa8255-ivi = "vm_config_lalv.xml"
VM_CONFIG_XML:sa7255-ivi = "vm_config_lalv.xml"

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
    install -m 0644 ${S}/qcrosvm_lv.service ${D}/${systemd_unitdir}/system/qcrosvm_lv.service
}

do_install:append:sa8775-flex() {
    install -m 0644 ${S}/vm_config_xml/vm_config_la.xml ${D}${sysconfdir}/vm_config_la.xml
}


cargo_common_do_patch_paths() {
    :
}
