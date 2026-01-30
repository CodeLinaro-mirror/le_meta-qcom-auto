SUMMARY = "Configure network for GVM"
DESCRIPTION = "Install the systemd service gvm_net_config.service to setup the network bridge for gvm."
HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${QTI_LICENSE_DIR}/${LICENSE};md5=b796c0007db682166a1721da80267bb2"

SYSTEMD_SERVICE:${PN} = "gvm_net_config.service"
SYSTEMD_SERVICE:${PN}:append:sa7255-ivi = " gvm_net_config_lvgvm.service"
SYSTEMD_SERVICE:${PN}:append:sa8255-ivi = " gvm_net_config_lvgvm.service"



SRCPROJECT  = "git://${OSS_REPO}/clo/le/platform/vendor/qcom-opensource/kiumd.git"
SRCBRANCH  = "safe-services.lnx.1.0.r18-rel"
SRCREV  = "90079a9bd5e3abe24cb234ce62fc84c7754d77b1"

SRC_URI = "\
    ${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/kiumd; \
"

S = "${WORKDIR}/vendor/qcom/opensource/kiumd/gvm_net_config"

inherit systemd

do_compile[noexec] = "1"

do_install:append() {
    install -d -p ${D}/usr/local/bin
    install -m 0755 ${S}/gvm_net_config.sh -D ${D}/usr/local/bin
    install -m 0644 ${S}/gvm_net_config.service -D ${D}${systemd_system_unitdir}/gvm_net_config.service
}

do_install:append:sa7255-ivi() {
    install -d -p ${D}/usr/local/bin

    install -m 0755 ${S}/gvm_net_config_lvgvm.sh -D ${D}/usr/local/bin
    install -m 0644 ${S}/gvm_net_config_lvgvm.service -D ${D}${systemd_system_unitdir}/gvm_net_config_lvgvm.service
}

do_install:append:sa8255-ivi() {
    install -d -p ${D}/usr/local/bin

    install -m 0755 ${S}/gvm_net_config_lvgvm.sh -D ${D}/usr/local/bin
    install -m 0644 ${S}/gvm_net_config_lvgvm.service -D ${D}${systemd_system_unitdir}/gvm_net_config_lvgvm.service
}

FILES:${PN} += "/usr/local/bin/*"
