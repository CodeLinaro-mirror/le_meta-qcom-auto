FILESEXTRAPATHS:prepend:qclinux-gvm-gen5 := "${THISDIR}/files:"

SRC_URI:append:qclinux-gvm-gen5 = " file://0013-net-stmmac-dwmac-qcom-ethqos-Enable-SCMI-ETH.patch"
