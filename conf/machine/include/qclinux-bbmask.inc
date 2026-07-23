# mask QCLINUX origin kernel recipes, use automotive's in qclinux1.0
BBMASK:append = " meta-qcom"
BBMASK:append = " meta-qti-auto-kernel/recipes-products/images/qti-image-ark-boot.bbappend"
BBMASK:append = " meta-qti-bsp/dynamic-layers/openembedded-layer/recipes-devtools/rsyslog/rsyslog_%.bbappend"
BBMASK:append = " meta-qti-bsp/recipes-bsp/ab-update/qcom-abctl_1.0.bb"
BBMASK:append = " meta-qti-bsp/recipes-bsp/ab-update/qcom-libgpt_1.0.bb"
BBMASK:append = " meta-qti-bsp/recipes-bsp/ss-restart"
BBMASK:append = " meta-qti-bsp/recipes-bsp/u-boot/u-boot-tools_2025.01.bb"
BBMASK:append = " meta-qti-bsp/recipes-connectivity/bluez5/"
BBMASK:append = " meta-qti-bsp/recipes-core/uutils-coreutils/uutils-coreutils_%.bbappend"
BBMASK:append = " meta-qti-bsp/recipes-kernel"
BBMASK:append = " meta-qti-bsp/recipes-firmware/firmware"
BBMASK:append = " meta-qti-bsp/recipes-multimedia/fastrpc/fastrpc_git.bbappend"
BBMASK:append = " meta-qti-bsp/recipes-support/pd-mapper/pd-mapper_git.bbappend"
BBMASK:append = " meta-qti-internal"
BBMASK:append = " meta-qti-realtime/recipes-core/packagegroups/packagegroup-qcom-initscripts.bbappend"
BBMASK:append = " meta-qti-realtime/recipes-kernel/images/linux-qcom-uki.bbappend"
BBMASK:append = " meta-qti-realtime/recipes-products/images/qcom-guestvm-image.bbappend"

# BBMASK drops some recipe that checksum file not found
BBMASK:append = " ${MISSING_RECIPE_SRCLIST}"

# List of recipes to be masked for sa8797dc
LEDC10_MASK_RECIPES += "\
                       meta-qti-bsp/recipes-kernel/perf/perf.bbappend \
                       meta-qcom-auto/recipes-security/ \
"

BBMASK:append:sa8797dc = " ${LEDC10_MASK_RECIPES} "

MISSING_RECIPE_SRCLIST:append:gen5 = " ${MISSING_RECIPE_SRCLIST_BASELINE_LY011}"
MISSING_RECIPE_SRCLIST:append:sa8775 = " ${MISSING_RECIPE_SRCLIST_BASELINE_LY020}"
MISSING_RECIPE_SRCLIST:append:sa7255 = " ${MISSING_RECIPE_SRCLIST_BASELINE_LY020}"

# Missing recipes globally
MISSING_RECIPE_SRCLIST += "\
    meta-qcom-auto/recipes-bsp/abctl/abctl_1.0.bb \
    meta-qcom-auto/recipes-bsp/libgpt/libgpt_1.0.bb \
    meta-qcom-auto/recipes-audio/agm-alsa-plugin/agm-alsa-plugin_git.bb \
    meta-qcom-auto/recipes-audio/agm_client/agm-client_git.bb \
    meta-qcom-auto/recipes-audio/agm_server/agm-server_git.bb \
    meta-qcom-auto/recipes-audio/agm-sndparser/agm-sndparser_git.bb \
    meta-qcom-auto/recipes-audio/agm-test/agm-test_git.bb \
    meta-qcom-auto/recipes-audio/agm-tinyalsa-plugin/agm-tinyalsa-plugin_git.bb \
    meta-qcom-auto/recipes-audio/ar-audiodlkm/ar-audiodlkm_git.bb \
    meta-qcom-auto/recipes-audio/ar-pal/ar-pal_git.bb \
    meta-qcom-auto/recipes-audio/audiodlkm/audiodlkm_git.bb \
    meta-qcom-auto/recipes-audio/pal-control-plugin/pal-control-plugin_git.bb \
    meta-qcom-auto/recipes-audio/pulseaudio-module-acdb/pulseaudio-module-acdb_git.bb \
    meta-qcom-auto/recipes-audio/pulseaudio-module-codec-control/pulseaudio-module-codec-control_git.bb \
    meta-qcom-auto/recipes-audio/pulseaudio-module-codec-control/pulseaudio-module-codec-control-headers_git.bb \
    meta-qcom-auto/recipes-bsp/libsensors/libsensors_git.bb \
    meta-qcom-auto/recipes-camera/cameradlkm/cameradlkm_0.1.bb \
    meta-qcom-auto/recipes-camera/cameradlkm/cameradlkm_0.2.bb \
    meta-qcom-auto/recipes-core/reboot-daemon/reboot-daemon_2.0.bb \
    meta-qcom-auto/recipes-data/avb-utils/avb-utils_1.0.bb \
    meta-qcom-auto/recipes-data/dataeth-dlkm/dataeth-dlkm_git.bb \
    meta-qcom-auto/recipes-data/emac-dwc-eqos/emac-dwc-eqos_git.bb \
    meta-qcom-auto/recipes-data/ptp-virtual/ptp-virtual_git.bb \
    meta-qcom-auto/recipes-graphics/graphics-driver/graphicsdlkm_git.bb \
    meta-qcom-auto/recipes-multimedia/dsp-kernel/dsp-kernel_git.bb \
    meta-qcom-auto/recipes-multimedia/media/media_0.1.bb \
    meta-qcom-auto/recipes-multimedia/videodlkm/videodlkm_git.bb \
    meta-qcom-auto/recipes-security/securemsmdlkm/securemsmdlkm_git.bb \
"

# Missing recipes in baseline LY.AU.0.1.1 (used by gen5)
MISSING_RECIPE_SRCLIST_BASELINE_LY011 += "\
    meta-qcom-auto/recipes-wlan/wlan-platform-dlkm/wlan-platform-dlkm_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-genoa_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hasting-cnss0_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hasting-cnss2_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hasting_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hmt_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hsp_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-rome_git.bb \
    meta-qcom-auto/recipes-qcvirtio/ \
    meta-qcom-auto-extras/recipes-qcvirtio/ \
"

# Missing recipes in baseline LY.AU.0.2.0 (used by sa8775/sa7255)
MISSING_RECIPE_SRCLIST_BASELINE_LY020 += "\
    meta-qcom-auto/recipes-audio/agm/agm_git.bb \
    meta-qcom-auto/recipes-audio/acdb/acdb_git.bb \
    meta-qcom-auto/recipes-audio/ar-osal/ar-osal_git.bb \
    meta-qcom-auto/recipes-audio/ar-util/ar-util_git.bb \
    meta-qcom-auto/recipes-audio/ats/ats_git.bb \
    meta-qcom-auto/recipes-audio/audioreach-conf/audioreach-conf_git.bb \
    meta-qcom-auto/recipes-audio/gpr/gpr_git.bb \
    meta-qcom-auto/recipes-audio/gsl/gsl_git.bb \
    meta-qcom-auto/recipes-audio/spf/spf_git.bb \
    meta-qcom-auto/recipes-audio/ar-audio-init-service/ar-audio-init-service_git.bb \
    meta-qcom-auto/recipes-audio/ar-audio-service-utils/ar-audio-service-utils_git.bb \
    meta-qcom-auto/recipes-audio/ar-dev-plugin/ar-dev-plugin_git.bb \
    meta-qcom-auto/recipes-audio/ar-scmi/ar-scmi_git.bb \
    meta-qcom-auto/recipes-audio/ar2-audio-service/ar2-audio-service_git.bb \
    meta-qcom-auto/recipes-audio/audio-utils/audio-utils_git.bb \
    meta-qcom-auto/recipes-audio/audio-device-manager/audio-device-manager_git.bb \
    meta-qcom-auto/recipes-audio/audio-plugin-intf/audio-plugin-intf_git.bb \
    meta-qcom-auto/recipes-audio/sound-card-info-util/sound-card-info-util_git.bb \
    meta-qcom-auto/recipes-audio/sound-card-info-common-header/sound-card-info-common-header_git.bb \
    meta-qcom-auto/recipes-display/display-intf/display-intf-headers.bb \
    meta-qcom-auto/recipes-display/display-la/display-la-headers.bb \
    meta-qcom-auto/recipes-multimedia/codec2/codec2-app_git.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/drm-player-example_1.0.0.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugin-qvais_git.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-codec2_1.0.0.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-drmdecryptor_1.0.0.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-extpoolsink.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-qcarcamsrc_git.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-qscreencapsrc_1.0.0.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-qvblend_1.0.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-qvconv.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-qvdeinterlace_1.0.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-qvrate_git.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-vesdeliver_1.0.0.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/mm-vdec-omx-test-lite_0.1.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/mm-venc-omx-test-lite_0.1.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/secure-video-app_1.0.0.bb \
    meta-qcom-auto/recipes-rideware/qcnode/qcnode_2.0.bb \
"

# New recipe introduced by GVM
MISSING_RECIPE_SRCLIST += "\
    meta-qcom-auto-extras/recipes-bsp/platform-devicetree/platform-devicetree_git.bb \
    meta-qcom-auto/recipes-aosp/libdmabufheap/libdmabufheap_git.bb \
    meta-qcom-auto/recipes-kernel/edk2/edk2_5.0.bb \
    meta-qcom-auto-extras/recipes-android/libvmmem/libvmmem_git.bb \
    meta-qcom-auto-extras/recipes-android/libvmmem_headers/libvmmem-headers_git.bb \
    meta-qcom-auto/recipes-kernel/kernel-aosp-tools/kernel-aosp-tools-native.bb \
    meta-qcom-auto/recipes-kernel/linux-msm/linux-msm_6.12.bb \
    meta-qcom-auto/recipes-kernel/linux-msm/linux-msm-headers_6.12.bb \
    meta-qcom-auto-extras/recipes-kernel/guest-bootloader/guest-bootloader.bb \
    meta-qcom-auto/recipes-bsp/platformdlkm/platformdlkm_git.bb \
    meta-qcom-auto/recipes-products/images/qti-image-boot.bb \
"

# Missing recipes in baseline LV.AU.0.5.3 (used by gvm-gen5)
# Temporary writing, once all GVM projects are integrated into qclinux, will uniformly refined
BBMASK:remove:gvm-gen5 = "${MISSING_RECIPE_SRCLIST}"
BBMASK:append:gvm-gen5 = " \
    meta-qcom-auto/recipes-virt/vmm-boot-lcm/vmm-boot-lcm_git.bb \
    meta-qcom-auto/recipes-virt/vhost-user-scmi/vhost-user-scmi_git.bb \
    meta-qcom-auto/recipes-virt/vhost-user-q/vhost-user-q_git.bb \
    meta-qcom-auto/recipes-virt/vhost-user-lib/vhost-user-lib_git.bb \
    meta-qcom-auto/recipes-virt/vhost-device/vhost-device-ssr_0.1.0.bb \
    meta-qcom-auto/recipes-virt/vhost-device/vhost-device-i2c_0.1.0.bb \
    meta-qcom-auto/recipes-virt/vhost-device/vhost-device-gpio_0.1.0.bb \
    meta-qcom-auto/recipes-virt/safelinux-system-cfg/safelinux-system-cfg_git.bb \
    meta-qcom-auto/recipes-virt/qcrosvm/qcrosvm_git.bb \
    meta-qcom-auto/recipes-virt/msmhab/msmhab_git.bb \
    meta-qcom-auto/recipes-virt/hyp-udmabuf/hyp-udmabuf-test_git.bb \
    meta-qcom-auto/recipes-virt/hyp-udmabuf/hyp-udmabuf_git.bb \
    meta-qcom-auto/recipes-virt/gunyah-drivers/gunyah-drivers_git.bb \
    meta-qcom-auto/recipes-security/security-modules/safelinux-sec-modules_git.bb \
    meta-qcom-auto/recipes-safelinux-dbg-modules/safelinux-dbg-modules/safelinux-dbg-modules_git.bb \
    meta-qcom-auto/recipes-rideware/qcnode/qcnode_2.0.bb \
    meta-qcom-auto/recipes-qcvirtio/virglrenderer/virglrenderer_1.1.0.bb \
    meta-qcom-auto/recipes-multimedia/media/ \
    meta-qcom-auto/recipes-kiumd/safelinux-cfg-modules/safelinux-cfg-modules_git.bb \
    meta-qcom-auto/recipes-kiumd/kiumd-headers/kiumd-headers_git.bb \
    meta-qcom-auto/recipes-kiumd/gvm-net-config/gvm-net-config_git.bb \
    meta-qcom-auto/recipes-kiumd/dspfirmware-mount/dspfirmware-mount_git.bb \
    meta-qcom-auto/recipes-kernel/oot-dtbo/oot-dtbo.bb \
    meta-qcom-auto/recipes-kernel/edk2/edk2_git.bb \
    meta-qcom-auto/recipes-graphics/mm-auto-ksync/ksyncdlkm_git.bb \
    meta-qcom-auto/recipes-graphics/display-hal/display-hal-linux_git.bb \
    meta-qcom-auto/recipes-graphics/display-hal/display-hal-headers_git.bb \
    meta-qcom-auto/recipes-graphics/auto-gfx-app/auto-gfx-app.bb \
    meta-qcom-auto/recipes-extra/recipes-core/scmi-test/scmi-test_1.0.bb \
    meta-qcom-auto/recipes-data/emac-dwc-eqos \
    meta-qcom-auto/recipes-data/dataeth-dlkm \
    meta-qcom-auto/recipes-data/avb-utils/ \
    meta-qcom-auto/recipes-data/aurix-can/aurix-can_git.bb \
    meta-qcom-auto/recipes-core/reboot-daemon/ \
    meta-qcom-auto/recipes-core/power-utils/power-utils_git.bb \
    meta-qcom-auto/recipes-core/platform-config/platform-config_git.bb \
    meta-qcom-auto/recipes-core/notify_aop/notify-aop_git.bb \
    meta-qcom-auto/recipes-core/irqbalance/irqbalance_git.bbappend \
    meta-qcom-auto/recipes-core/early-service-infra/early-service-infra_git.bb \
    meta-qcom-auto/recipes-core/bootkpi-logging/bootkpi-logging.bb \
    meta-qcom-auto/recipes-camera/cameradlkm \
    meta-qcom-auto/recipes-bsp/libgpt/ \
    meta-qcom-auto/recipes-bsp/abctl/ \
    meta-qcom-auto/recipes-audio/spf/spf_git.bb \
    meta-qcom-auto/recipes-audio/gsl/gsl_git.bb \
    meta-qcom-auto/recipes-audio/gpr/gpr_git.bb \
    meta-qcom-auto/recipes-audio/audioreach-conf/audioreach-conf_git.bb \
    meta-qcom-auto/recipes-audio/audiolite-dlkm/audiolite-dlkm_git.bb \
    meta-qcom-auto/recipes-audio/audiolite-dlkm/audiolite-devicetree_git.bb \
    meta-qcom-auto/recipes-audio/ats/ats_git.bb \
    meta-qcom-auto/recipes-audio/ar-util/ar-util_git.bb \
    meta-qcom-auto/recipes-audio/ar-osal/ar-osal_git.bb \
    meta-qcom-auto/recipes-audio/acdb/acdb_git.bb \
    meta-qcom-auto-extras/recipes-virt/vmm-test/vmm-test.bb \
    meta-qcom-auto-extras/recipes-virt/vmm-ramdump/vmm-ramdump_git.bb \
    meta-qcom-auto-extras/recipes-virt/vmm-pwr-key/vmm-pwr-key.bb \
    meta-qcom-auto-extras/recipes-virt/vmm-lib/vmm-lib.bb \
    meta-qcom-auto-extras/recipes-virt/vmm-drv/vmm-drv_git.bb \
    meta-qcom-auto-extras/recipes-virt/vhost-user-glink-passthrough/vhost-user-glink-passthrough_git.bb \
    meta-qcom-auto-extras/recipes-tools/sysprofiler/sysprofiler_git.bb \
    meta-qcom-auto-extras/recipes-tools/subsys-ramdump-util/subsys-ramdump-util_git.bb \
    meta-qcom-auto-extras/recipes-tools/sio/siosocket_git.bb \
    meta-qcom-auto-extras/recipes-tools/sio/sio_git.bb \
    meta-qcom-auto-extras/recipes-tools/qualcomm-logging-framework/tpm_git.bb \
    meta-qcom-auto-extras/recipes-tools/qualcomm-logging-framework/tool-util_git.bb \
    meta-qcom-auto-extras/recipes-tools/qualcomm-logging-framework/sw-driver-qlf-ioctl-usage_git.bb \
    meta-qcom-auto-extras/recipes-tools/qualcomm-logging-framework/sw-driver-qlf-client-usage_git.bb \
    meta-qcom-auto-extras/recipes-tools/qualcomm-logging-framework/qlf-cloud-server_git.bb \
    meta-qcom-auto-extras/recipes-tools/qualcomm-logging-framework/qlf-client_git.bb \
    meta-qcom-auto-extras/recipes-tools/qualcomm-logging-framework/log-collector_git.bb \
    meta-qcom-auto-extras/recipes-tools/qualcomm-logging-framework/ana-syslog-mgr_git.bb \
    meta-qcom-auto-extras/recipes-tools/qualcomm-logging-framework/analyzer-parser_git.bb \
    meta-qcom-auto-extras/recipes-tools/qualcomm-logging-framework/analyzer-file-manager_git.bb \
    meta-qcom-auto-extras/recipes-tools/qdss-util/qdss-util_git.bb \
    meta-qcom-auto-extras/recipes-tools/qdss-sysfs/qdss-sysfs_git.bb \
    meta-qcom-auto-extras/recipes-tools/qdss-service/qdss-service_git.bb \
    meta-qcom-auto-extras/recipes-tools/libqdss-driver/libqdss-driver_git.bb \
    meta-qcom-auto-extras/recipes-tools/dsm/dsm_git.bb \
    meta-qcom-auto-extras/recipes-tools/diagservice/diagservice_git.bb \
    meta-qcom-auto-extras/recipes-tools/DiagPortChangeTest/diag-port-change-test_git.bb \
    meta-qcom-auto-extras/recipes-tools/diag-osal/diag-osal_git.bb \
    meta-qcom-auto-extras/recipes-tools/diag-ondevice/diag-ondevice_git.bb \
    meta-qcom-auto-extras/recipes-tools/diag-lsm/diag-lsm_git.bb \
    meta-qcom-auto-extras/recipes-tools/diag-dcm/diag-dcm_git.bb \
    meta-qcom-auto-extras/recipes-tools/analyzer-tools/analyzer-tools_git.bb \
    meta-qcom-auto-extras/recipes-safety/safe-xml-parser/safe-xml-parser_1.0.bb \
    meta-qcom-auto-extras/recipes-safety/safety_shared-service/safety-shared_git.bb \
    meta-qcom-auto-extras/recipes-safety/safetymonitor-service/safetymonitor_git.bb \
    meta-qcom-auto-extras/recipes-safety/safetylibs-service/safetylibs_git.bb \
    meta-qcom-auto-extras/recipes-safety/pfm/pfm_1.0.bb \
    meta-qcom-auto-extras/recipes-safety/apss-stl/apss-stl_1.0.bb \
    meta-qcom-auto-extras/recipes-rideware/ridehal/ridehal_1.0.bb \
    meta-qcom-auto-extras/recipes-qcvirtio/qcvirtio-devices/qcvirtio-devices_git.bb \
    meta-qcom-auto-extras/recipes-multimedia/vpp-service/vpp-service_git.bb \
    meta-qcom-auto-extras/recipes-multimedia/video-driver/video-driver_git.bb \
    meta-qcom-auto-extras/recipes-multimedia/video-driver/vidc-unit-test_git.bb \
    meta-qcom-auto-extras/recipes-multimedia/video-driver/vidc-test-app_git.bb \
    meta-qcom-auto-extras/recipes-multimedia/video-driver/vidc-enc-test_git.bb \
    meta-qcom-auto-extras/recipes-multimedia/video-driver/vidc-dec-test_git.bb \
    meta-qcom-auto-extras/recipes-multimedia/planedef/planedef_git.bb \
    meta-qcom-auto-extras/recipes-multimedia/mm-video-noship/mm-video-noship_0.1.bb \
    meta-qcom-auto-extras/recipes-multimedia/mm-video-noship/mmvideo-common-headers_git.bb \
    meta-qcom-auto-extras/recipes-multimedia/mm-vfio-devicetree/mm-vfio-devicetree.bb \
    meta-qcom-auto-extras/recipes-multimedia/mm-umd-hwfence/libhwfence_git.bb \
    meta-qcom-auto-extras/recipes-multimedia/media-noship/media-noship_git.bb \
    meta-qcom-auto-extras/recipes-multimedia/media-external/media-external_git.bb \
    meta-qcom-auto-extras/recipes-multimedia/media-codec2/media-codec2_git.bb \
    meta-qcom-auto-extras/recipes-multimedia/media-codec2/codec2-service_git.bb \
    meta-qcom-auto-extras/recipes-multimedia/libkiumd/libkiumd_git.bb \
    meta-qcom-auto-extras/recipes-multimedia/apdf/apdf_1.0.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/vhost-user-frpc/vhost-user-frpc_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/syscache/syscache-rm_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/ssr-rm/ssr-rm_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/ssctl-service/ssctl-service_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/rpcmem/rpcmem_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/pil-rm/pil-rm_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/nsp-drv/nsp-drv_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/libtzbsp/libtzbsp_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/libstd/libstd_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/libssr-client/libssr-client_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/libssloader/libssloader_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/libsmp2p/libsmp2p_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/libsmem/libsmem_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/libqmi-framework/libqmi-framework_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/libpil-client/libpil-client_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/libipc-router/libipc-router_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/libipcc/libipcc_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/libglink-core/libglink-core_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/libglink-client/libglink-client_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/ipcc-err-handler/ipcc-err-handler_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/glink-service-lrm/glink-service-lrm_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/glink-passthrough-service/glink-passthrough-service_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/fastrpc-rm/fastrpc-rm_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/fastrpc/fastrpc_git.bb \
    meta-qcom-auto-extras/recipes-mproc-umd/ecc-err-handler/ecc-err-handler_git.bb \
    meta-qcom-auto-extras/recipes-mmframeworks/synx-service/synx-service_git.bb \
    meta-qcom-auto-extras/recipes-mmframeworks/mm-frameworks-ship/mm-frameworks-ship_git.bb \
    meta-qcom-auto-extras/recipes-mmframeworks/mm-frameworks/mm-frameworks_git.bb \
    meta-qcom-auto-extras/recipes-kernel/kernel-devicetree/kernel-devicetree.bb \
    meta-qcom-auto-extras/recipes-graphics/owfds-ship/owfds-ship.bb \
    meta-qcom-auto-extras/recipes-graphics/owfds/owfds.bb \
    meta-qcom-auto-extras/recipes-graphics/owfds/openwfd-client.bb \
    meta-qcom-auto-extras/recipes-graphics/owfds/display-drvconfig.bb \
    meta-qcom-auto-extras/recipes-graphics/libgbm/gbm_git \
    meta-qcom-auto-extras/recipes-graphics/dprx-ss/dprx-ss.bb \
    meta-qcom-auto-extras/recipes-graphics/display-noship/display-noship-linux_git.bb \
    meta-qcom-auto-extras/recipes-graphics/display-be/display-be.bb \
    meta-qcom-auto-extras/recipes-graphics/adreno/adreno.bb \
    meta-qcom-auto-extras/recipes-graphics/adreno/adreno_0.1.bb \
    meta-qcom-auto-extras/recipes-data/safegptp/safegptp.bb \
    meta-qcom-auto-extras/recipes-data/qavb/qavb_1.0.bb \
    meta-qcom-auto-extras/recipes-data/aurix-can-umd/aurix-can-umd_git.bb \
    meta-qcom-auto-extras/recipes-cv/sv-auto-test/sv-auto-test.bb \
    meta-qcom-auto-extras/recipes-cv/sv-auto/sv-auto.bb \
    meta-qcom-auto-extras/recipes-cv/sv-auto-noship/sv-auto-noship.bb \
    meta-qcom-auto-extras/recipes-cv/qml-noship/qml-noship_1.0.bb \
    meta-qcom-auto-extras/recipes-cv/qblaze-noship/qblaze-noship_git.bb \
    meta-qcom-auto-extras/recipes-cv/qadlibs-x/qadlibs-x.bb \
    meta-qcom-auto-extras/recipes-cv/qadlibs-x-noship/qadlibs-x-noship.bb \
    meta-qcom-auto-extras/recipes-cv/fadas-noship/fadas-noship_git.bb \
    meta-qcom-auto-extras/recipes-cv/fadas/fadas_git.bb \
    meta-qcom-auto-extras/recipes-cv/eva-auto-noship/eva-auto-noship_git.bb \
    meta-qcom-auto-extras/recipes-cv/eva-auto/eva-auto_1.0.bb \
    meta-qcom-auto-extras/recipes-cv/cvp-noship/cvp-noship_git.bb \
    meta-qcom-auto-extras/recipes-cv/cv-apps/cv-apps_1.0.bb \
    meta-qcom-auto-extras/recipes-core/libvirtdiag/libvirtdiag_git.bb \
    meta-qcom-auto-extras/recipes-core/gearvm-util/gearvm-logs_git.bb \
    meta-qcom-auto-extras/recipes-compute-platform/powercyclemgr/powercyclemgr_1.0.bb \
    meta-qcom-auto-extras/recipes-compute-platform/pcie-sm/pcie-sm.bb \
    meta-qcom-auto-extras/recipes-compute-platform/pcie-c2c/pcie-c2c.bb \
    meta-qcom-auto-extras/recipes-compute-platform/nsp-stl/nsp-stl_1.0.bb \
    meta-qcom-auto-extras/recipes-compute-platform/nsp-service/nsp-service_1.0.bb \
    meta-qcom-auto-extras/recipes-compute-platform/nsp-rsm/nsp-rsm_1.0.bb \
    meta-qcom-auto-extras/recipes-compute-platform/nsp-drv/nsp-drv_git.bb \
    meta-qcom-auto-extras/recipes-compute-platform/nsp-config/nsp-config_1.0.bb \
    meta-qcom-auto-extras/recipes-compute-platform/hetero-sched/hetero-sched_1.0.bb \
    meta-qcom-auto-extras/recipes-compute-platform/compute-ressched/compute-ressched_1.0.bb \
    meta-qcom-auto-extras/recipes-compute-platform/compute-resmon/compute-resmon_1.0.bb \
    meta-qcom-auto-extras/recipes-compute-platform/compute-resmgr/compute-resmgr_1.0.bb \
    meta-qcom-auto-extras/recipes-compute-platform/compute-osal/compute-osal_1.0.bb \
    meta-qcom-auto-extras/recipes-compute-platform/compute-cape/compute-cape_1.0.bb \
    meta-qcom-auto-extras/recipes-camera/cdk/chi-cdk_0.1.bb \
    meta-qcom-auto-extras/recipes-camera/camx-lib/camx-lib_0.1.bb \
    meta-qcom-auto-extras/recipes-camera/camx/camx_0.1.bb \
    meta-qcom-auto-extras/recipes-camera/camera-devicetree/camera-devicetree_git.bb \
    meta-qcom-auto-extras/recipes-camera/ais/ais_0.1.bb \
    meta-qcom-auto-extras/recipes-bsp/sail_mailbox/sail-mailbox_git.bb \
    meta-qcom-auto-extras/recipes-bsp/platform-utils/syslog-interposer.bb \
    meta-qcom-auto-extras/recipes-bsp/platform-utils/qc-pm_git.bb \
    meta-qcom-auto-extras/recipes-bsp/platform-utils/libtpp.bb \
    meta-qcom-auto-extras/recipes-bsp/platform-utils/libsoftsku.bb \
    meta-qcom-auto-extras/recipes-bsp/c2c/softsku-daemon_git.bb \
    meta-qcom-auto-extras/recipes-audio/sound-card-info-util/sound-card-info-util_git.bb \
    meta-qcom-auto-extras/recipes-audio/shared-memory-test/shared-memory-test_git.bb \
    meta-qcom-auto-extras/recipes-audio/pcm-logging-util/pcm-logging-util_git.bb \
    meta-qcom-auto-extras/recipes-audio/mcm/mcm_git.bb \
    meta-qcom-auto-extras/recipes-audio/csd2-server/csd2-server_git.bb \
    meta-qcom-auto-extras/recipes-audio/csd2-oem-plugin/csd2-oem-plugin_git.bb \
    meta-qcom-auto-extras/recipes-audio/csd2/csd2_git.bb \
    meta-qcom-auto-extras/recipes-audio/csd2-client/csd2-client_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-utils/awe-utils_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-user-version/awe-user-version_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-tools/awe-tools_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-tinyalsa-plugin/awe-tinyalsa-plugin_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-test/awe-test.bb \
    meta-qcom-auto-extras/recipes-audio/awe-sound-card-info-util/awe-sound-card-info-util_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-sndparser/awe-sndparser_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-server/awe-server_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-scmi/awe-scmi_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-packetservice/awe-packetservice_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-packetserver/awe-packetserver_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-packetclient/awe-packetclient_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-osal/awe-osal_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-oem-tunnel/awe-oem-tunnel_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-manager/awe-manager_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-linuxproxy/awe-linuxproxy_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-headers-export/awe-headers-export_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-dsp-service/awe-dsp-service_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-dev-plugin/awe-dev-plugin_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-device-manager/awe-device-manager_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-controller/awe-controller_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-config/awe-config_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-command/awe-command_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-client/awe-client_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-chime/awe-chime_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-audio-service/awe-audio-service_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-audio-plugin-intf/awe-audio-plugin-intf_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-audiolite-osal/awe-audiolite-osal_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-alsa-plugin/awe-alsa-plugin_git.bb \
    meta-qcom-auto-extras/recipes-audio/awe-alsa/awe-alsa_git.bb \
    meta-qcom-auto-extras/recipes-audio/avmm/avmm_git.bb \
    meta-qcom-auto-extras/recipes-audio/auto-casa-xml/auto-casa-xml_git.bb \
    meta-qcom-auto-extras/recipes-audio/audio-utils/audio-utils_git.bb \
    meta-qcom-auto-extras/recipes-audio/audio-service/audio-service_git.bb \
    meta-qcom-auto-extras/recipes-audio/audio-plugin-intf/audio-plugin-intf_git.bb \
    meta-qcom-auto-extras/recipes-audio/audio-oem-conf/audio-oem-conf_git.bb \
    meta-qcom-auto-extras/recipes-audio/audio-log-util/audio-log-util_git.bb \
    meta-qcom-auto-extras/recipes-audio/audiolite/audiolite-app_git.bb \
    meta-qcom-auto-extras/recipes-audio/audio-headers-export/audio-headers-export_git.bb \
    meta-qcom-auto-extras/recipes-audio/audio-device-manager/audio-device-manager_git.bb \
    meta-qcom-auto-extras/recipes-audio/audio-chime/audio-chime_git.bb \
    meta-qcom-auto-extras/recipes-audio/aud-fastrpcd/aud-fastrpcd_git.bb \
    meta-qcom-auto-extras/recipes-audio/ar-scmi/ar-scmi_git.bb \
    meta-qcom-auto-extras/recipes-audio/ar-dev-plugin/ar-dev-plugin_git.bb \
    meta-qcom-auto-extras/recipes-audio/ar-audio-service-utils/ar-audio-service-utils_git.bb \
    meta-qcom-auto-extras/recipes-audio/ar-audio-init-service/ar-audio-init-service_git.bb \
    meta-qcom-auto-extras/recipes-audio/ar2-audio-service/ar2-audio-service_git.bb \
    meta-qcom-auto-extras/recipes-audio/ams2-test/ams2-test_git.bb \
    meta-qcom-auto-extras/recipes-audio/ams2/ams2_git.bb \
    meta-qcom-auto-extras/recipes-audio/amfs2/amfs2_git.bb \
    meta-qti-automotive-internal/recipes/qstrawberry/qstrawberry.bb \
    meta-qti-automotive-internal/recipes/qml-internal/qml-internal_git.bb \
    meta-qti-automotive-internal/recipes/qblaze-internal/qblaze-internal_git.bb \
    meta-qti-automotive-internal/recipes/qadlibs-x-internal/qadlibs-x-internal.bb \
    meta-qti-automotive-internal/recipes-multimedia/mm-umd-hwfence/libhwfence-test_git.bb \
    meta-qti-automotive-internal/recipes-multimedia/mm-frameworks-compat/mm-frameworks-compat_git.bb \
    meta-qti-automotive-internal/recipes/kernel-tests/kernel-qc-tests.bb \
    meta-qti-automotive-internal/recipes/fadas-internal/fadas-internal_git.bb \
    meta-qti-automotive-internal/recipes/eva-auto-internal/eva-auto-internal.bb \
    meta-qti-automotive-internal/recipes-cv/sv-auto-internal/sv-auto-internal.bb \
    meta-qti-automotive-internal/recipes/cv-internal/cvp-test_git.bb \
    meta-qti-automotive-internal/recipes-compute-platform/qcipc/qcipc_1.0.bb \
    meta-qti-automotive-internal/recipes/apdf-internal/apdf-internal.bb \
"
