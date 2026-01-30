SUMMARY = "Enable Qualcomm systemd services to use specific slices for cgroup management."
DESCRIPTION = "systemd slices are a systemd abstraction for cgroups. \
               This package enables Qualcomm systemd services to use different slices, \
               allowing them to choose the appropriate cgroup for each service."

HOMEPAGE = "https://git.codelinaro.org"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a"

DEPENDS += "systemd"



SRCPROJECT  = "git://${OSS_REPO}/clo/le/platform/vendor/qcom-opensource/safelinux-system-cfg.git"
SRCBRANCH  = "safe-services.lnx.1.0.r18-rel"
SRCREV  = "d8d2e9564aeef8be70ce0ae6bc40271722e41dd2"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};protocol=${OSS_PROTO};destsuffix=vendor/qcom/opensource/safelinux-system-cfg;"
S = "${WORKDIR}/vendor/qcom/opensource/safelinux-system-cfg/platform-config"

EXTRA_OECMAKE:append:sa8775-flex = " -D PVM_CPUS:STRING=0-3 -D GVM_CPUS:STRING=4-7"
EXTRA_OECMAKE:append:gen5 = " -D PVM_CPUS:STRING=0-5 -D GVM_CPUS:STRING=6-17"
EXTRA_OECMAKE:append:sa8255-ivi = " -D PVM_CPUS:STRING=0-1 -D GVM_CPUS:STRING=2-7"

inherit systemd cmake pkgconfig

SYSTEMD_SERVICE:${PN} = "\
    gvm.slice \
    pvm.slice \
    "
FILES:${PN} += "\
    ${systemd_system_unitdir}/* \
    "

