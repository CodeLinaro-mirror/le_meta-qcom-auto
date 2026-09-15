FILESBBAPPENDPATH := "${THISDIR}"
FILESEXTRAPATHS =. "${FILESBBAPPENDPATH}/${BP}:${FILESBBAPPENDPATH}/${BPN}:"

SRC_URI:append = " \
    file://0001-systemd-avoid-active-seat-change-to-NULL.patch \
    file://60-misc.rules \
    file://0001-systemd-config-linger-for-root-user.patch \
    file://0001-sd-bus-drop-half-registered-vtable-members-on-failur.patch \
"

SRC_URI:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'qti-hypervisor', 'file://0033-systemd-Make-root-s-home-directory-configurable-2.patch', '', d)}"

SRC_URI:append:gvm-gen5 = " file://60-vblk.rules"
SRC_URI:append:qclinux-gvm-gen5 = " file://60-vblk.rules"
SRC_URI:append:gvm-gen4-5 = " file://60-vblk.rules"
SRC_URI:append:sa81x5 = " file://0001-systemd-add-slotselect-support-in-fstab.patch"

SRC_URI:append = " ${@bb.utils.contains("PREFERRED_VERSION_linux-msm", "6.12", "file://linux-msm-6.12_modules_load.conf", "", d)}"
SRC_URI:append:qclinux-gvm-gen5 = " ${@bb.utils.contains("PREFERRED_VERSION_linux-qcom-custom-rt", "6.6", "file://linux-qcom-gvm-6.6_modules_load.conf", "", d)}"

SRC_URI:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', 'file://0035-systemd-Make-systemd-init-run-in-high-priority.patch', '', d)} "

# Remove backlight - Loads/Saves Screen Backlight Brightness, not required.
RUMI_PACKAGECONFIG = "\
    ${@bb.utils.filter('DISTRO_FEATURES', 'acl audit efi ldconfig pam selinux smack usrmerge polkit seccomp', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wifi', 'rfkill', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'xkbcommon', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', '', 'link-udev-shared', d)} \
    backlight \
    binfmt \
    gshadow \
    hibernate \
    hostnamed \
    idn \
    ima \
    localed \
    logind \
    machined \
    myhostname \
    networkd \
    nss \
    nss-mymachines \
    nss-resolve \
    quotacheck \
    randomseed \
    resolved \
    set-time-epoch \
    sysusers \
    sysvinit \
    timedated \
    timesyncd \
    userdb \
    utmp \
    vconsole \
    wheel-group \
    zstd \
"
PACKAGECONFIG:remove = "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'qti-rumi', '${RUMI_PACKAGECONFIG}', '', d)} \
"
PACKAGECONFIG:remove = "backlight"

#Disable systemd-timesyncd which not used in project.
PACKAGECONFIG:remove = "timesyncd "

#Enable coredump by default
PACKAGECONFIG:append:sa8775 = " coredump"
PACKAGECONFIG:append:gen5 = " coredump"
PACKAGECONFIG:append:sa7255 = " coredump"

# slotselect patch (sa81x5) uses strlcat, provided natively by glibc >= 2.38 (2.43 in use).
# Previously glib-2.0 (-DUSE_GLIB / g_strlcat) supplied it, but that global -lglib-2.0
# polluted systemd-boot's static-pie linker probe (src/boot) and broke do_configure.
CFLAGS:append = " -fPIC"

# In aarch64 targets systemd is not booting with -finline-functions -finline-limit=64 optimizations
# So temporarily revert to default optimizations for systemd.
FULL_OPTIMIZATION = "-O2 -fexpensive-optimizations -frename-registers -fomit-frame-pointer -ftree-vectorize"

do_install:append:sa81x5() {
    # workaroud for suspend, suspend is not allowd in systemd-sleep, LA will trigger suspend
    if ${@bb.utils.contains('DISTRO_FEATURES', 'qti-lxc', 'true', 'false', d)}; then
        sed -i 's/#AllowSuspend=yes/AllowSuspend=no/' ${D}${sysconfdir}/systemd/sleep.conf
    fi
}

do_install:append () {
    if ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', 'true', 'false', d)}; then
       echo "DefaultLimitNOFILE=infinity" >> ${D}${sysconfdir}/systemd/system.conf
       echo "DefaultLimitMSGQUEUE=infinity" >> ${D}${sysconfdir}/systemd/system.conf
       echo "DefaultTimeoutStopSec=10s" >> ${D}${sysconfdir}/systemd/system.conf
    fi

    # Use kernel rules for network iface name
    sed -i  's/^NamePolicy.*/NamePolicy=kernel/g' ${D}${systemd_unitdir}/network/99-default.link

    # Create a symlink to the touchscreen input device via USB1
    if ${@bb.utils.contains('MACHINE_FEATURES', 'qti-vmm', 'true', 'false', d)}; then
        echo '# Create a symlink to the touchscreen input device or the mouse input device via USB1' >> ${D}${sysconfdir}/udev/rules.d/touchscreen.rules
        echo 'SUBSYSTEM=="input", KERNEL=="event[0-9]*", ENV{ID_INPUT_TOUCHSCREEN}!="1", ENV{ID_INPUT_MOUSE}=="1", ENV{ID_BUS}=="usb", DEVPATH=="*usb1*", SYMLINK+="input/usb1_touchscreen0", TAG+="systemd", ATTRS{name}!="* UNKNOWN", TAG+="touchscreen"' >> ${D}${sysconfdir}/udev/rules.d/touchscreen.rules
        echo 'SUBSYSTEM=="input", KERNEL=="event[0-9]*", ENV{ID_INPUT_TOUCHSCREEN}=="1", ENV{ID_INPUT_MOUSE}!="1", ENV{ID_BUS}=="usb", DEVPATH=="*usb1*", SYMLINK+="input/usb1_touchscreen0", TAG+="systemd", ATTRS{name}!="* UNKNOWN", TAG+="touchscreen"' >> ${D}${sysconfdir}/udev/rules.d/touchscreen.rules
    fi

    #Remove privatetmp=true from hostname service
    if ${@bb.utils.contains('DISTRO_FEATURES', 'qti-rumi', 'false', 'true', d)}; then
        sed -i  '/^PrivateTmp.*/d' ${D}${systemd_system_unitdir}/systemd-hostnamed.service
    fi

    # Remove orignal 60-persistent-v4l.rules which is not applicable for QTI video
    rm ${D}${nonarch_base_libdir}/udev/rules.d/60-persistent-v4l.rules

    # Add platform_load.conf to /etc/modules-load.d/, systemd will load modules in this file.

    # Create by-partlabel symlink for la/lv/bluetooth/modem/dsp devices in disksymlink-service service, remove these operations from plain udev rules
    # Note: udev rules live under ${nonarch_base_libdir}/udev in systemd 259.5;
    # the old ${rootlibexecdir} no longer resolves (var dropped upstream), so use nonarch_base_libdir.
    sed -i 's#ENV{ID_PART_ENTRY_SCHEME}=="gpt", ENV{ID_PART_ENTRY_NAME}=="?\*", SYMLINK+="disk/by-partlabel/$env{ID_PART_ENTRY_NAME}"#ENV{ID_PART_ENTRY_SCHEME}=="gpt", ENV{ID_PART_ENTRY_NAME}=="?\*", ENV{ID_PART_ENTRY_NAME}!="la_*", ENV{ID_PART_ENTRY_NAME}!="lv_*", ENV{ID_PART_ENTRY_NAME}!="bluetooth*", ENV{ID_PART_ENTRY_NAME}!="modem*", ENV{ID_PART_ENTRY_NAME}!="dsp*", SYMLINK+="disk/by-partlabel/$env{ID_PART_ENTRY_NAME}"#' ${D}${nonarch_base_libdir}/udev/rules.d/60-persistent-storage.rules

    if ${@bb.utils.contains("PREFERRED_VERSION_linux-msm", "6.12", "true", "false", d)}; then
        install -m 0664 ${UNPACKDIR}/linux-msm-6.12_modules_load.conf ${D}${sysconfdir}/modules-load.d/
    fi

    # Mask serial-getty on hvc0 to prevent auto getty on hypervisor console
    ln -sf /dev/null ${D}${sysconfdir}/systemd/system/serial-getty@hvc0.service

    # Set OOMScoreAdjust=0 on serial-getty (moved from systemd-serialgetty.bbappend:
    # serial-getty@.service is installed by this main systemd package, not systemd-serialgetty)
    sed -i -e '/SendSIGHUP=yes/a OOMScoreAdjust=0' ${D}${systemd_system_unitdir}/serial-getty@.service
}

do_install:append:gvm-gen5() {
    install -m 0644 ${UNPACKDIR}/60-vblk.rules ${D}${sysconfdir}/udev/rules.d/
}

do_install:append:qclinux-gvm-gen5() {
    install -m 0644 ${UNPACKDIR}/60-vblk.rules ${D}${sysconfdir}/udev/rules.d/
    install -m 0664 ${UNPACKDIR}/linux-qcom-gvm-6.6_modules_load.conf ${D}${sysconfdir}/modules-load.d/
}

do_install:append:gvm-gen4-5() {
    install -m 0644 ${UNPACKDIR}/60-vblk.rules ${D}${sysconfdir}/udev/rules.d/
}
