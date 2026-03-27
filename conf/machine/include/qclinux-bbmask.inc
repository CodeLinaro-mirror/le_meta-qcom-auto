# mask QCLINUX origin kernel recipes, use automotive's in qclinux1.0
BBMASK:append = " meta-qti-auto-kernel/recipes-products/images/qti-image-ark-boot.bbappend"
BBMASK:append = " meta-qcom-auto-hwe/dynamic-layers/openembedded-layer/recipes-devtools/rsyslog/rsyslog_%.bbappend"
BBMASK:append = " meta-qcom-auto-hwe/recipes-bsp/ab-update/qcom-abctl_1.0.bb"
BBMASK:append = " meta-qcom-auto-hwe/recipes-bsp/ab-update/qcom-libgpt_1.0.bb"
BBMASK:append = " meta-qcom-auto-hwe/recipes-bsp/ss-restart"
BBMASK:append = " meta-qcom-auto-hwe/recipes-bsp/u-boot/u-boot-tools_2025.01.bb"
BBMASK:append = " meta-qcom-auto-hwe/recipes-connectivity/bluez5/"
BBMASK:append = " meta-qcom-auto-hwe/recipes-core/uutils-coreutils/uutils-coreutils_%.bbappend"
BBMASK:append = " meta-qcom-auto-hwe/recipes-kernel"
BBMASK:append = " meta-qcom-auto-hwe/recipes-firmware/firmware"
BBMASK:append = " meta-qcom-auto-hwe/recipes-multimedia/fastrpc/fastrpc_git.bbappend"
BBMASK:append = " meta-qcom-auto-hwe/recipes-support/pd-mapper/pd-mapper_git.bbappend"
BBMASK:append = " meta-qti-internal"
BBMASK:append = " meta-qti-realtime/recipes-core/packagegroups/packagegroup-qcom-initscripts.bbappend"
BBMASK:append = " meta-qti-realtime/recipes-kernel/images/linux-qcom-uki.bbappend"
BBMASK:append = " meta-qti-realtime/recipes-products/images/qcom-guestvm-image.bbappend"
BBMASK:append = " meta-qcom-realtime/recipes-core/packagegroups/packagegroup-qcom-initscripts.bbappend"
BBMASK:append = " meta-qcom-realtime/recipes-kernel/images/linux-qcom-uki.bbappend"
BBMASK:append = " meta-qcom-realtime/recipes-products/images/qcom-guestvm-image.bbappend"


# BBMASK drops some recipe that checksum file not found
BBMASK:append = " ${MISSING_RECIPE_SRCLIST}"

# List of recipes to be masked for sa8797dc
LEDC10_MASK_RECIPES += "\
                       meta-qcom-auto-hwe/recipes-kernel/perf/perf.bbappend \
                       meta-qcom-auto/recipes-security/ \
"

BBMASK:append:sa8797dc = " ${LEDC10_MASK_RECIPES} "

MISSING_RECIPE_SRCLIST:append:gen5 = " ${MISSING_RECIPE_SRCLIST_BASELINE_LY011}"
MISSING_RECIPE_SRCLIST:append:sa8775 = " ${MISSING_RECIPE_SRCLIST_BASELINE_LY020}"
MISSING_RECIPE_SRCLIST:append:sa7255 = " ${MISSING_RECIPE_SRCLIST_BASELINE_LY020}"

# Missing recipes globally
MISSING_RECIPE_SRCLIST += "\
    meta-qcom-auto/recipes-audio/agm/agm_git.bb \
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
    meta-qcom-hwe \
"

# Missing recipes in baseline LY.AU.0.1.1 (used by gen5)
MISSING_RECIPE_SRCLIST_BASELINE_LY011 += "\
    meta-qcom-auto/recipes-connectivity/synergy-opensource/csrspp-tty_git.bb \
    meta-qcom-auto/recipes-connectivity/synergy-opensource/synergy-opensource_git.bb \
    meta-qcom-auto/recipes-wlan/wlan-platform-dlkm/wlan-platform-dlkm_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-genoa_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hasting-cnss0_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hasting-cnss2_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hasting_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hmt_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hsp_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-rome_git.bb \
    meta-qcom-auto/recipes-audio/system-media/system-media_git.bb \
    meta-qcom-auto/recipes-core/notify_aop/notify-aop_git.bb \
    meta-qcom-auto/recipes-data/aurix-can/aurix-can_git.bb \
    meta-qcom-auto/recipes-data/hsi2s/hsi2s-qmi-test_git.bb \
    meta-qcom-auto/recipes-data/hsi2s/hsi2s-test_0.1.bb \
    meta-qcom-auto/recipes-data/hsi2s/hsi2s_git.bb \
    meta-qcom-auto/recipes-data/open-avb/open-avb_0.1.bb \
    meta-qcom-auto/recipes-display/displaydlkm/displaydlkm_git.bb \
    meta-qcom-auto/recipes-display/displaydlkm/umd-power.bb \
    meta-qcom-auto/recipes-kiumd/gvm-net-config/gvm-net-config_git.bb \
    meta-qcom-auto/recipes-multimedia/codec2/codec2-app_git.bb \
    meta-qcom-auto/recipes-multimedia/codec2/codec2_git.bb \
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
    meta-qcom-auto/recipes-security/securemsmdlkm/securemsmdlkm_git.bb \
    meta-qcom-auto/recipes-wlan/device/wlan-conf_git.bb \
    meta-qcom-auto/recipes-wlan/hostap-daemon/hostap-daemon-qcacld_git.bb \
    meta-qcom-auto/recipes-wlan/hs20-osu-client/hs20-osu-client_git.bb \
    meta-qcom-auto/recipes-wlan/wlan-sigma-dut/wlan-sigma-dut_git.bb \
    meta-qcom-auto/recipes-wlan/wpa-supplicant/wpa-supplicant_git.bb \
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
"


