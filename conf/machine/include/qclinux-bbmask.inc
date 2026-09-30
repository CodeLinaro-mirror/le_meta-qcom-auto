# mask only the base meta-qcom layer; do not mask meta-qcom-auto* layers
BBMASK:append = " meta-qcom/"
BBMASK:append = " meta-qcom-auto-kernel/recipes-products/images/qti-image-ark-boot.bbappend"
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
BBMASK:append = " meta-qcom-realtime/recipes-core/packagegroups/packagegroup-qcom-initscripts.bbappend"
BBMASK:append = " meta-qcom-realtime/recipes-kernel/images/linux-qcom-uki.bbappend"
BBMASK:append = " meta-qcom-hwe/recipes-kernel/images/initramfs-qcom-image.bbappend"
BBMASK:append = " meta-qcom-hwe/recipes-support/pd-mapper/pd-mapper_git.bbappend"
BBMASK:append = " meta-qcom-hwe/recipes-edgegateway/parser/parser_0.1.bb"
BBMASK:append = " meta-qcom-hwe/recipes-bsp/ab-update/qcom-abctl_1.0.bb"
BBMASK:append = " meta-qcom-hwe/recipes-bsp/ab-update/qcom-libgpt_1.0.bb"
BBMASK:append = " meta-qcom-hwe/recipes-bsp/u-boot/u-boot-tools_2025.01.bb"
BBMASK:append = " meta-qcom-hwe/recipes-connectivity/bluez5/"
BBMASK:append = " meta-qcom-hwe/recipes-core/uutils-coreutils/uutils-coreutils_%.bbappend"
BBMASK:append = " meta-qcom-hwe/recipes-kernel"
BBMASK:append = " meta-qcom-hwe/recipes-firmware/firmware"
BBMASK:append = " meta-qcom-hwe/recipes-multimedia/fastrpc/fastrpc_git.bbappend"
BBMASK:append = " meta-qcom-hwe/recipes-graphics/msm_be/msm_be.bb"
BBMASK:append = " meta-qcom-hwe/recipes-graphics/libgbm/gbm_21.1.1.bb"
BBMASK:append = " meta-qcom-hwe/recipes-graphics/adreno/qcom-adreno_1.855.2.bb"
BBMASK:append = " meta-qcom-hwe/dynamic-layers/openembedded-layer/recipes-devtools/rsyslog/rsyslog_%.bbappend"
# Mask all meta-qcom-hwe recipes except subsystem ramdump parity recipe.
BBMASK:append = " meta-qcom-hwe/(?!recipes-bsp/ss-restart/qcom-subsystem-ramdump_git\\.bb$).*(\\.bb|\\.bbappend)$"
BBMASK:append:gen5 = " meta-qcom-auto-extras/recipes-audio/ar-osal/ar-osal_git.bb"
BBMASK:append:gen5 = " meta-qcom-auto-extras/recipes-audio/ar-util/ar-util_git.bb"
BBMASK:append:gen5 = " meta-qcom-auto-extras/recipes-audio/ats/ats_git.bb"
BBMASK:append:gen5 = " meta-qcom-auto-extras/recipes-audio/gsl/gsl_git.bb"
BBMASK:append:gen5 = " meta-qcom-auto-extras/recipes-audio/audio-plugin-intf/audio-plugin-intf_git.bb"
BBMASK:append = " meta-qcom-hwe/recipes-connectivity/iw/iw_5.19.qcom.bb"
BBMASK:append:gen5 = " meta-qcom-hwe/recipes-graphics/wayland/weston-launch.bb"
BBMASK:append:sa8775 = " meta-qcom-hwe/recipes-graphics/wayland/weston-launch.bb"
BBMASK:append:gen5 = " meta-qcom-hwe/recipes-graphics/wayland/weston_13.0.1.bbappend"
BBMASK:append:sa8775 = " meta-qcom-hwe/recipes-graphics/wayland/weston_13.0.1.bbappend"

# BBMASK drops some recipe that checksum file not found
BBMASK:append = " ${MISSING_RECIPE_SRCLIST}"

# List of recipes to be masked for sa8797dc
LEDC10_MASK_RECIPES += "\
                       meta-qti-bsp/recipes-kernel/perf/perf.bbappend \
                       meta-qcom-hwe/recipes-kernel/perf/perf.bbappend \
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
    meta-qcom-auto/recipes-audio/ar-pal/ar-pal_git.bb \
    meta-qcom-auto/recipes-audio/ar-audiodlkm/ar-audiodlkm_git.bb \
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
    meta-qcom-auto/recipes-audio/agl-audio-plugin/agl-audio-plugin_0.1.bb \
    meta-qcom-auto/recipes-audio/agl-audio-plugin/agl-audio-plugin_0.1.bbappend \
    meta-qcom-auto/recipes-audio/msm-virtio-snd/msm-virtio-snd.bb \
    meta-qcom-auto/recipes-bsp/platformdlkm/platformdlkm_git.bb \
    meta-qcom-auto/recipes-connectivity/bt-dlkm/bt-dlkm-secondary_git.bb \
    meta-qcom-auto/recipes-display/display-devicetree/display-devicetree_1.0.bb \
    meta-qcom-auto/recipes-display/displaydlkm/msm-ext-display_git.bb \
    meta-qcom-auto/recipes-graphics/graphics-hgsl/graphics-hgsldlkm_git.bb \
    meta-qcom-auto/recipes-kernel/dtc/dtc-aosp.bb \
    meta-qcom-auto/recipes-kernel/edk2/edk2_5.0.bb \
    meta-qcom-auto/recipes-kernel/linux-msm/linux-ack-headers_6.12.bb \
    meta-qcom-auto/recipes-kernel/linux-msm/linux-ack_6.12.bb \
    meta-qcom-auto/recipes-kernel/linux-msm/linux-msm-headers_6.12.bb \
    meta-qcom-auto/recipes-kernel/linux-msm/linux-msm_6.12.bb \
    meta-qcom-auto/recipes-kernel/linux-msm/qcom-devicetree_6.12.bb \
    meta-qcom-auto/recipes-kernel/linux-msm/qcom-dtc-native.bb \
    meta-qcom-auto/recipes-kernel/linux-msm/soc-modules_6.12.bb \
    meta-qcom-auto/recipes-kernel/linux/soc-repo.bb \
    meta-qcom-auto/recipes-aosp/libdmabufheap/libdmabufheap_git.bb \
    meta-qcom-auto-extras/recipes-multimedia/adsprpc/adsprpc_git.bb \
"

# Missing recipes in baseline LY.AU.0.1.1 (used by gen5)
MISSING_RECIPE_SRCLIST_BASELINE_LY011 += "\
    meta-qcom-auto/recipes-kernel/kernel-aosp-tools/kernel-aosp-tools-native.bb \
    meta-qcom-auto-extras/recipes-qcvirtio/qcvirtio-devices/qcvirtio-devices_git.bb \
    meta-qcom-auto/recipes-audio/ar-pal/ar-pal_git.bb \
    meta-qcom-auto/recipes-audio/audiodlkm/audiodlkm_git.bb \
    meta-qcom-auto/recipes-audio/init-audio/init-audio_git.bb \
    meta-qcom-auto/recipes-audio/pal-control-plugin/pal-control-plugin_git.bb \
    meta-qcom-auto/recipes-audio/pulseaudio-module-acdb/pulseaudio-module-acdb_git.bb \
    meta-qcom-auto/recipes-audio/pulseaudio-module-codec-control/pulseaudio-module-codec-control-headers_git.bb \
    meta-qcom-auto/recipes-audio/pulseaudio-module-codec-control/pulseaudio-module-codec-control_git.bb \
    meta-qcom-auto/recipes-audio/system-media/system-media_git.bb \
    meta-qcom-auto/recipes-audio/tinyalsa/tinyalsa_git.bb \
    meta-qcom-auto/recipes-bsp/libsensors/libsensors_git.bb \
    meta-qcom-auto/recipes-camera/cameradlkm/cameradlkm_0.1.bb \
    meta-qcom-auto/recipes-camera/cameradlkm/cameradlkm_0.2.bb \
    meta-qcom-auto/recipes-connectivity/setup-network/setup-network_1.0.bb \
    meta-qcom-auto/recipes-core/early-init/early-init.bb \
    meta-qcom-auto/recipes-core/reboot-daemon/reboot-daemon_2.0.bb \
    meta-qcom-auto/recipes-core/resize-service/resize-service_1.0.bb \
    meta-qcom-auto/recipes-core/sec-config/sec-config_1.0.bb \
    meta-qcom-auto/recipes-data/aurix-can/aurix-can_git.bb \
    meta-qcom-auto/recipes-data/avb-utils/avb-utils_1.0.bb \
    meta-qcom-auto/recipes-data/dataeth-dlkm/dataeth-dlkm_git.bb \
    meta-qcom-auto/recipes-data/emac-dwc-eqos/emac-dwc-eqos_git.bb \
    meta-qcom-auto/recipes-data/hsi2s/hsi2s-qmi-test_git.bb \
    meta-qcom-auto/recipes-data/hsi2s/hsi2s-test_0.1.bb \
    meta-qcom-auto/recipes-data/hsi2s/hsi2s_git.bb \
    meta-qcom-auto/recipes-data/open-avb/open-avb_0.1.bb \
    meta-qcom-auto/recipes-data/ptp-virtual/ptp-virtual_git.bb \
    meta-qcom-auto/recipes-display/displaydlkm/displaydlkm_git.bb \
    meta-qcom-auto/recipes-display/displaydlkm/umd-power.bb \
    meta-qcom-auto/recipes-graphics/graphics-driver/graphicsdlkm_git.bb \
    meta-qcom-auto/recipes-graphics/wayland/weston-udev.bb \
    meta-qcom-auto/recipes-multimedia/codec2/codec2-app_git.bb \
    meta-qcom-auto/recipes-multimedia/codec2/codec2_git.bb \
    meta-qcom-auto/recipes-multimedia/dsp-kernel/dsp-kernel_git.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/drm-player-example_1.0.0.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugin-qvais_git.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-codec2_1.0.0.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-drmdecryptor_1.0.0.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-qscreencapsrc_1.0.0.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-qvblend_1.0.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-qvrate_git.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-vesdeliver_1.0.0.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/mm-vdec-omx-test-lite_0.1.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/mm-venc-omx-test-lite_0.1.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/secure-video-app_1.0.0.bb \
    meta-qcom-auto/recipes-multimedia/media/media_0.1.bb \
    meta-qcom-auto/recipes-multimedia/videodlkm/videodlkm_git.bb \
    meta-qcom-auto/recipes-qcvirtio/qcvirtio-rtc-host-driver-linux/qcvirtio-rtc-host-driver-linux-headers_git.bb \
    meta-qcom-auto/recipes-qcvirtio/qcvirtio-rtc-host-driver-linux/qcvirtio-rtc-host-driver-linux_git.bb \
    meta-qcom-auto/recipes-qcvirtio/qcvirtio-uclk-driver-linux/qcvirtio-uclk-driver-linux-headers_git.bb \
    meta-qcom-auto/recipes-qcvirtio/qcvirtio-uclk-driver-linux/qcvirtio-uclk-driver-linux_git.bb \
    meta-qcom-auto/recipes-qcvirtio/simple-can-simulator/simple-can-simulator_git.bb \
    meta-qcom-auto/recipes-qcvirtio/virglrenderer/virglrenderer_1.1.0.bb \
    meta-qcom-auto/recipes-security/securemsmdlkm/securemsmdlkm_git.bb \
    meta-qti-bsp/recipes-bsp/libvmmem/qcom-libvmmem_git.bb \
    meta-qti-bsp/recipes-multimedia/sensors/qcom-sensinghub_1.0.bb \
    meta-qcom-auto/recipes-qcvirtio/qcvirtio-vhost-usb-gadget-driver-linux/qcvirtio-vhost-usb-gadget-driver-linux_git.bb \
    meta-qcom-auto/recipes-qcvirtio/qcvirtio-vhost-usb-gadget-driver-linux/qcvirtio-vhost-usb-gadget-driver-linux-headers_git.bb \
    meta-qcom-auto/recipes-aosp/libion/libion_git.bb \
    meta-qcom-auto/recipes-security/securemsm-devicetree/securemsm-devicetree.bb \
"

# Missing recipes in baseline LY.AU.0.2.0 (used by sa8775/sa7255)
MISSING_RECIPE_SRCLIST_BASELINE_LY020 += "\
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
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-vidc.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/mm-vdec-omx-test-lite_0.1.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/mm-venc-omx-test-lite_0.1.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/secure-video-app_1.0.0.bb \
    meta-qcom-auto/recipes-rideware/qcnode/qcnode_2.0.bb \
    meta-qcom-auto/recipes-audio/system-media/system-media_git.bb \
    meta-qcom-auto/recipes-data/aurix-can/aurix-can_git.bb \
    meta-qcom-auto/recipes-data/hsi2s/hsi2s-qmi-test_git.bb \
    meta-qcom-auto/recipes-data/hsi2s/hsi2s-test_0.1.bb \
    meta-qcom-auto/recipes-data/hsi2s/hsi2s_git.bb \
    meta-qcom-auto/recipes-data/open-avb/open-avb_0.1.bb \
    meta-qcom-auto/recipes-display/displaydlkm/displaydlkm_git.bb \
    meta-qcom-auto/recipes-display/displaydlkm/umd-power.bb \
    meta-qcom-auto/recipes-multimedia/codec2/codec2_git.bb \
    meta-qcom-auto/recipes-qcvirtio/qcvirtio-rtc-host-driver-linux/qcvirtio-rtc-host-driver-linux_git.bb \
    meta-qcom-auto/recipes-qcvirtio/qcvirtio-rtc-host-driver-linux/qcvirtio-rtc-host-driver-linux-headers_git.bb \
    meta-qcom-auto/recipes-qcvirtio/qcvirtio-uclk-driver-linux/qcvirtio-uclk-driver-linux_git.bb \
    meta-qcom-auto/recipes-qcvirtio/qcvirtio-uclk-driver-linux/qcvirtio-uclk-driver-linux-headers_git.bb \
    meta-qcom-auto/recipes-qcvirtio/virglrenderer/virglrenderer_1.1.0.bb \
    meta-qti-bsp/recipes-bsp/libvmmem/qcom-libvmmem_git.bb \
    meta-qti-bsp/recipes-multimedia/sensors/qcom-sensinghub_1.0.bb \
    meta-qcom-auto-internal/recipes/qt5/qtbase_%.bbappend \
    meta-qcom-auto-internal/recipes/qt5/qtmultimedia_%.bbappend \
    meta-qcom-auto-internal/recipes/qt5/qtwayland_%.bbappend \
    meta-qcom-auto-internal/recipes-graphics/adreno/adreno.bbappend \
    meta-qcom-auto-internal/recipes-graphics/dprx-ss/dprx-ss.bbappend \
    meta-qcom-auto-internal/recipes-multimedia/mm-frameworks/mm-frameworks_git.bbappend \
    meta-qcom-auto-internal/recipes-multimedia/mm-umd-hwfence/libhwfence_git.bbappend \
    meta-qcom-auto/recipes-qcvirtio/qcvirtio-vhost-usb-gadget-driver-linux/qcvirtio-vhost-usb-gadget-driver-linux-headers_git.bb \
    meta-qcom-auto/recipes-qcvirtio/qcvirtio-vhost-usb-gadget-driver-linux/qcvirtio-vhost-usb-gadget-driver-linux_git.bb \
    meta-qcom-auto/recipes-security/securemsm-devicetree/securemsm-devicetree.bb \
    meta-qcom-auto/recipes-aosp/libion/libion_git.bb \
"

# Keep non-Nord WLAN variants masked; Gen5 Nord uses HSP/HMT only.
BBMASK += " \
    meta-qcom-auto/recipes-wlan/hs20-osu-client/hs20-osu-client_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-genoa_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hasting-cnss0_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hasting-cnss2_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hasting_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-rome_git.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-codec2-lemans.bb \
    meta-qcom-auto/recipes-qcvirtio/adbd-linux/adbd-linux_git.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-codec2-monaco.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/drm-player-example.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-qvdeinterlace.bb \
    meta-qcom-auto/recipes-kernel/linux/vms-devicetree.bb \
    meta-qcom-auto/recipes-multimedia/virtio-video/virtio-video_git.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-vesdeliver.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-codec2-gen5.bb \
    meta-qcom-auto/recipes-multimedia/gstreamer/gstreamer1.0-plugins-drmdecryptor.bb \
"
