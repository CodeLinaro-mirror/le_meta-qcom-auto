require qcacld32-ll.inc

SUMMARY = "Qualcomm Technologies, Inc. WLAN Driver"
DESCRIPTION = "Qualcomm Technologies, Inc. WLAN CLD3.0 low latency driver for HastingsPrime WLAN chip.\
               It is a kernel extra module, which loaded by init_qti_wlan_auto.service \
               once the system bootup. And this WLAN host driver module name is qca6797.ko,\
               it create two interface by default, one is wlan0 and the other is wlan1. \
               Application can use the wireless interfaces as STA/AP/P2P mode in need. \"
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"

SRC_URI = "${CLO_LE_GIT}/platform/vendor/qcom-opensource/wlan/qcacld-3.0.git;branch=auto-wlan-cld3-driver.lnx.2.1.r37-rel;protocol=${OSS_PROTO};name=qcacld;destsuffix=wlan/qcacld-3.0 \
           ${CLO_LE_GIT}/platform/vendor/qcom-opensource/wlan/qca-wifi-host-cmn.git;branch=auto-wlan-cmn-driver.lnx.2.4.r21-rel;protocol=${OSS_PROTO};name=qca-wifi-host-cmn;destsuffix=wlan/qca-wifi-host-cmn \
           ${CLO_LE_GIT}/platform/vendor/qcom-opensource/wlan/fw-api.git;branch=auto-wlan-api.lnx.1.4.r21-rel;protocol=${OSS_PROTO};name=fw-api;destsuffix=wlan/fw-api/ \
           ${CLO_LA_GIT}/platform/vendor/qcom/wlan.git;branch=auto-wlan-service.lnx.1.4.r8-rel;protocol=${OSS_PROTO};name=wlan;destsuffix=device/qcom/wlan \
           "
SRCREV_qcacld = "a8efb16618257a91038c8b49427222332c4f7df4"
SRCREV_qca-wifi-host-cmn = "69be777fc7fdf55a139822cba4053f38a93c05b1"
SRCREV_fw-api = "913e7274d2d8219359bfff4132e90f3ec8239bec"
SRCREV_wlan = "2536a284a5ccc942c51507b53bcf1ec1ff6a0928"
SRCREV_FORMAT = "qcacld_cmn_fw_msm"

_MODNAME = "qca6797"
_WLAN_CTRL_NAME = "wlan"
FW_PATH_NAME = "kiwi"
FIRMWARE_PATH = "${D}${nonarch_base_libdir}/firmware/wlan/qca_cld/${_MODNAME}"

S1 = "${WORKDIR}/wlan/qca-wifi-host-cmn"
S = "${WORKDIR}/wlan/qcacld-3.0"

# Explicitly disable HL to enable LL as current WLAN driver is not having
# simultaneous support of HL and LL.
EXTRA_OEMAKE:append = " \
                       CONFIG_CLD_HL_SDIO_CORE=n \
                       CONFIG_CNSS_SDIO=n \
                       CONFIG_QCA_CLD_WLAN_PROFILE=kiwi_v2 \
                       DYNAMIC_SINGLE_CHIP=${_MODNAME} \
                       MODNAME=${_MODNAME} \
                       WLAN_CTRL_NAME=${_WLAN_CTRL_NAME} \
                       "

_WLAN_CFG_OVERRIDE = "\
                        LINUX_BUILD_TOP=${_LINUX_BUILD_TOP} \
                        CONFIG_WLAN_OPEN_P2P_INTERFACE=y \
                        CONFIG_SUPPORT_P2P_BY_ONE_INTF_WLAN=n \
                        CONFIG_WLAN_PLACEMARKER_PREFIX=108 \
                        CONFIG_CNSS_GENL=n \
                        CONFIG_QCOM_TDLS=n \
                        CONFIG_CFG_MAX_STA_VDEVS=4 \
                        CONFIG_CFG_BMISS_OFFLOAD_MAX_VDEV=4 \
                        CONFIG_BAND_6GHZ=y \
                        CONFIG_CONNECTION_ROAMING_CFG=n \
                        CONFIG_DBR_HOLD_LARGE_MEM=n \
                        CONFIG_DP_MULTIPASS_SUPPORT=n \
                        CONFIG_FEATURE_DELAYED_PEER_OBJ_DESTROY=n \
                        CONFIG_WLAN_FEATURE_MULTI_LINK_SAP=y \
                        "
EXTRA_OEMAKE:append = " WLAN_CFG_OVERRIDE=${_WLAN_CFG_OVERRIDE}"
EXTRA_OEMAKE:append:gen5 = " CONFIG_WLAN_MAX_CPUS=18"

do_install() {
    module_do_install

    install -d ${FIRMWARE_PATH}
    install -d ${D}${includedir}/qcacld/
    install -m 0644 ${S1}/utils/nlink/inc/wlan_nlink_common.h ${D}${includedir}/qcacld/

    install -D -m 0644 ${WORKDIR}/device/qcom/wlan/msm_auto/WCNSS_qcom_cfg_qca6797.ini ${FIRMWARE_PATH}/WCNSS_qcom_cfg.ini
    install -D -m 0644 ${WORKDIR}/device/qcom/wlan/msm_auto/wlan_mac.bin ${FIRMWARE_PATH}/wlan_mac.bin

    ln -sf /firmware/image/${FW_PATH_NAME} ${D}${nonarch_base_libdir}/firmware/${FW_PATH_NAME}

}

# Disable idle shutdown for HGY
do_install:append() {
    sed -i "s/gInterfaceChangeWait=500/gInterfaceChangeWait=0/g" ${FIRMWARE_PATH}/WCNSS_qcom_cfg.ini
    sed -i "s/gSuspendMode=3/gSuspendMode=2/g" ${FIRMWARE_PATH}/WCNSS_qcom_cfg.ini
}
