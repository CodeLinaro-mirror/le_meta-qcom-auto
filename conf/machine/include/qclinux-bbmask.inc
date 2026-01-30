# mask QCLINUX origin kernel recipes, use automotive's in qclinux1.0
BBMASK:append = " meta-qti-auto-kernel/recipes-products/images/qti-image-ark-boot.bbappend"
BBMASK:append = " meta-qcom-hwe/dynamic-layers/openembedded-layer/recipes-devtools/rsyslog/rsyslog_%.bbappend"
BBMASK:append = " meta-qcom-hwe/recipes-bsp/ab-update/qcom-abctl_1.0.bb"
BBMASK:append = " meta-qcom-hwe/recipes-bsp/ab-update/qcom-libgpt_1.0.bb"
BBMASK:append = " meta-qcom-hwe/recipes-bsp/ss-restart"
BBMASK:append = " meta-qcom-hwe/recipes-bsp/u-boot/u-boot-tools_2025.01.bb"
BBMASK:append = " meta-qcom-hwe/recipes-connectivity/bluez5/"
BBMASK:append = " meta-qcom-hwe/recipes-core/uutils-coreutils/uutils-coreutils_%.bbappend"
BBMASK:append = " meta-qcom-hwe/recipes-kernel"
BBMASK:append = " meta-qcom-hwe/recipes-firmware/firmware"
BBMASK:append = " meta-qcom-hwe/recipes-multimedia/fastrpc/fastrpc_git.bbappend"
BBMASK:append = " meta-qcom-hwe/recipes-support/pd-mapper/pd-mapper_git.bbappend"
BBMASK:append = " meta-qti-internal"
BBMASK:append = " meta-qti-realtime/recipes-core/packagegroups/packagegroup-qcom-initscripts.bbappend"
BBMASK:append = " meta-qti-realtime/recipes-kernel/images/linux-qcom-uki.bbappend"
BBMASK:append = " meta-qti-realtime/recipes-products/images/qcom-guestvm-image.bbappend"

# BBMASK drops some recipe that checksum file not found
BBMASK:append = " ${MISSING_RECIPE_SRCLIST}"

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
    meta-qcom-auto/recipes-data/hsi2s/hsi2s_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hasting_git.bb \
    meta-qcom-auto/recipes-display/displaydlkm/umd-power.bb \
    meta-qcom-auto/recipes-wlan/hs20-osu-client/hs20-osu-client_git.bb \
    meta-qcom-auto/recipes-wlan/wlan-platform-dlkm/wlan-platform-dlkm_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hasting-cnss2_git.bb \
    meta-qcom-auto/recipes-data/hsi2s/hsi2s-qmi-test_git.bb \
    meta-qcom-auto/recipes-display/displaydlkm/displaydlkm_git.bb \
    meta-qcom-auto/recipes-multimedia/codec2/codec2_git.bb \
    meta-qcom-auto/recipes-connectivity/synergy-opensource/csrspp-tty_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-genoa_git.bb \
    meta-qcom-auto/recipes-aosp/binder/binder_git.bb \
    meta-qcom-auto/recipes-audio/system-media/system-media_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hsp_git.bb \
    meta-qcom-auto/recipes-wlan/wlan-sigma-dut/wlan-sigma-dut_git.bb \
    meta-qcom-auto/recipes-data/hsi2s/hsi2s-test_0.1.bb \
    meta-qcom-auto/recipes-kernel/linux/linux/kernel-basedevicetree.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hmt_git.bb \
    meta-qcom-auto/recipes-data/aurix-can/aurix-can_git.bb \
    meta-qcom-auto/recipes-data/open-avb/open-avb_0.1.bb \
    meta-qcom-auto/recipes-connectivity/synergy-opensource/synergy-opensource_git.bb \
    meta-qcom-auto/recipes-connectivity/bt-dlkm/bt-dlkm_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-hasting-cnss0_git.bb \
    meta-qcom-auto/recipes-wlan/wlan/qcacld32-ll-rome_git.bb \
    meta-qcom-auto/recipes-wlan/hostap-daemon/hostap-daemon-qcacld_git.bb \
    meta-qcom-auto/recipes-wlan/device/wlan-conf_git.bb \
    meta-qcom-auto/recipes-rideware/qcnode/qcnode_2.0.bb \
    meta-qcom-auto/recipes-security/security-modules/open-dice_git.bb \
    meta-qcom-hwe/recipes-connectivity/ims/imsdpl_git.bb \
    meta-qcom-hwe/recipes-connectivity/ims/imsrtp.bb \
    meta-qcom-hwe/recipes-connectivity/wlan-proprietary/qcom-ftm_git.bb \
    meta-qcom-hwe/recipes-connectivity/wlan-proprietary/qcom-wlan-tools_git.bb \
    meta-qcom-hwe/recipes-connectivity/wlan-proprietary/qcom-ath11k-fwtest_git.bb \
    meta-qcom-hwe/recipes-connectivity/wlan-proprietary/qcom-ath6kl-utils.bb \
    meta-qcom-hwe/recipes-firmware/firmware/qcom-video-firmware_1.0.bb \
    meta-qcom-hwe/recipes-multimedia/adsprpc/securedsp-example_1.0.qcom.bb \
    meta-qcom-hwe/recipes-multimedia/adsprpc/dspservices-headers_15.0.qcom[+]really1.0.1.bb \
    meta-qcom-hwe/recipes-multimedia/adsprpc/remote-debug-agent_15.0.qcom[+]really1.0.1.bb \
    meta-qcom-hwe/recipes-multimedia/fastcv-noship/qcom-fastcv-binaries_1.8.0.bb \
    meta-qcom-hwe/recipes-multimedia/fastrpc/fastrpc_15.0.qcom.bb \
    meta-qcom-hwe/recipes-multimedia/sensors/qcom-sensors-test-apps_1.0.bb \
    meta-qcom-hwe/recipes-multimedia/sensors/qcom-sensors-registry_1.0.bb \
    meta-qcom-hwe/recipes-multimedia/sensors/qcom-sensors-test-core_1.0.bb \
    meta-qcom-hwe/recipes-multimedia/sensors/qcom-sensors-api_1.0.bb \
    meta-qcom-hwe/recipes-multimedia/sensors/qcom-sensors-services_1.0.bb \
    meta-qcom-hwe/recipes-multimedia/sensors/qcom-sensors-test-utils_1.0.bb \
    meta-qcom-hwe/recipes-multimedia/sensors/qcom-sensors-lookup_1.0.bb \
    meta-qcom-hwe/recipes-multimedia/sensors/qcom-sensors-core_1.0.bb \
    meta-qcom-hwe/recipes-multimedia/sensors/qcom-sensors-utils_1.0.bb \
    meta-qcom-hwe/recipes-multimedia/audio/qcom-audio-expander_git.bb \
    meta-qcom-hwe/recipes-multimedia/audio/qcom-sva-eai_2.0.bb \
    meta-qcom-hwe/recipes-multimedia/audio/qcom-mercuryflasher_git.bb \
    meta-qcom-hwe/recipes-multimedia/audio/qcom-audio-mercury_git.bb \
    meta-qcom-hwe/recipes-multimedia/audio/qcom-audio-systems_git.bb \
    meta-qcom-hwe/recipes-multimedia/audio/qcom-audio-dac_git.bb \
    meta-qcom-hwe/recipes-multimedia/audio/qcom-audiolisten_git.bb \
    meta-qcom-hwe/recipes-multimedia/audio/qcom-capiv2-headers_git.bb \
    meta-qcom-hwe/recipes-multimedia/camx/camxapi_1.0.qcom.bb \
    meta-qcom-hwe/recipes-multimedia/camx/camxlib_1.0.qcom.bb \
    meta-qcom-hwe/recipes-multimedia/camx/camxcommon_1.0.qcom.bb \
    meta-qcom-hwe/recipes-multimedia/camx/camxapi-kt_1.0.qcom.bb \
    meta-qcom-hwe/recipes-multimedia/camx/camx-kt_1.0.qcom.bb \
    meta-qcom-hwe/recipes-multimedia/camx/chicdk-kt_1.0.qcom.bb \
    meta-qcom-hwe/recipes-multimedia/camx/camxlib-kt_1.0.qcom.bb \
    meta-qcom-hwe/recipes-multimedia/camx/camx_1.0.qcom.bb \
    meta-qcom-hwe/recipes-multimedia/camx/chicdk_1.0.qcom.bb \
    meta-qcom-hwe/recipes-multimedia/display/qcom-display-color-linux_1.0.bb \
    meta-qcom-hwe/recipes-multimedia/display/qcom-display-extn-linux_1.0.bb \
    meta-qcom-hwe/recipes-bsp/perf-hal/qcom-perf-hal_1.0.0.bb \
    meta-qcom-hwe/recipes-bsp/time-services/time-services_15.0[+]really1.0.1.bb \
    meta-qcom-hwe/recipes-bsp/diag/diag_15.0.qcom[+]really1.0.1.bb \
    meta-qcom-hwe/recipes-bsp/diag/diag-router_15.0[+]really1.0.1.bb \
    meta-qcom-hwe/recipes-bsp/tftp-server/tftp-server_15.0[+]really1.0.1.bb \
    meta-qcom-hwe/recipes-bsp/time-genoff/time-genoff_15.0[+]really1.0.1.bb \
    meta-qcom-hwe/recipes-bsp/qmi-framework/qmi-framework_15.0[+]really1.0.0.bb \
    meta-qcom-hwe/recipes-bsp/thermal-engine/thermal-engine_14.0.bb \
    meta-qcom-hwe/recipes-security/securemsm-features/securemsm-features_1.0.bb \
    meta-qcom-hwe/recipes-security/minkipc/minkipc_1.0.bb \
    meta-qcom-hwe/recipes-security/securemsm/securemsm_1.0.bb \
    meta-qcom-hwe/recipes-security/securemsm-headers/securemsm-headers_1.0.bb \
    meta-qcom-hwe/recipes-graphics/adreno/qcom-adreno_1.808.11.bb \
    meta-qcom-hwe/recipes-core/video-ctrl/qcom-video-ctrl.bb \
    meta-qcom-hwe/dynamic-layers/openembedded-layer/recipes-devtools/rsyslog/rsyslog_%.bbappend \
    meta-qcom-hwe/recipes-graphics/wayland/weston_13.0.1.bbappend  \
    meta-qcom-hwe/recipes-multimedia/audio/alsa-state.bbappend \
    meta-qcom-hwe/recipes-multimedia/audio/alsa-utils_%.bbappend \
    meta-qcom-hwe/recipes-multimedia/audio/qcom-audio-systems_1.0.0.bb \
    meta-qcom-hwe/recipes-connectivity/wlan-proprietary/qcom-ctrl-app-dut_git.bb \
    meta-qcom-hwe/recipes-multimedia/fastcv-noship/qcom-fastcv-binaries_1.8.3.bb \
    meta-qcom-hwe/recipes-multimedia/audio/qcom-audiolisten_1.0.0.bb \
    meta-qcom-hwe/recipes-graphics/adreno/qcom-adreno_1.838.3.bb \
    meta-qcom-hwe/recipes-multimedia/adsprpc/dspservices-headers_15.0.qcom[+]really1.0.1.bb \
    meta-qcom-hwe/recipes-multimedia/adsprpc/remote-debug-agent_15.0.qcom[+]really1.0.1.bb \
    meta-qcom-hwe/recipes-multimedia/audio/qcom-audio-expander_1.0.1.bb \
    meta-qcom-hwe/recipes-multimedia/audio/qcom-audio-mercury_1.0.0.bb \
    meta-qcom-hwe/recipes-multimedia/audio/qcom-audio-dac_1.0.0.bb \
    meta-qcom-hwe/recipes-multimedia/audio/qcom-capiv2-headers_1.0.0.bb \
    meta-qcom-hwe/recipes-multimedia/camx/chicdk-autogen_1.0.qcom.bb \
    meta-qcom-hwe/recipes-multimedia/camx/camx-autogen_1.0.qcom.bb \
    meta-qcom-hwe/recipes-bsp/time-services/time-services_15.0[+]really1.0.1.bb \
    meta-qcom-hwe/recipes-bsp/diag/diag_15.0.qcom[+]really1.0.1.bb \
    meta-qcom-hwe/recipes-bsp/diag/diag-router_15.0[+]really1.0.1.bb \
    meta-qcom-hwe/recipes-bsp/tftp-server/tftp-server_15.0[+]really1.0.1.bb \
    meta-qcom-hwe/recipes-bsp/time-genoff/time-genoff_15.0[+]really1.0.1.bb \
    meta-qcom-hwe/recipes-bsp/qmi-framework/qmi-framework_15.0[+]really1.0.0.bb \
"
