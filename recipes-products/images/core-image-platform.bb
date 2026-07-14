SUMMARY = "Core platform image"
DESCRIPTION = "Build the full core image depend on different parameters"

DEPENDS += "mkbootimg-native"

inherit core-image

# Minimal systemd initramfs
PACKAGE_INSTALL = "\
    busybox \
    systemd \
    udev \
    safelinux-cfg-modules \
    safelinux-system-cfg \
    libkiumd \
    kernel-modules \
"

EXTRA_IMAGECMD:ext4 = "-i 4096 -b 4096"

# debug-tweaks removed in wrynose oe-core; replaced by individual features
IMAGE_FEATURES:append = " ${@bb.utils.contains('VARIANT', 'debug', 'allow-empty-password allow-root-login empty-root-password ssh-server-openssh', '', d)}"

do_rootfs[postfuncs] += "prune_busybox_unused"

prune_busybox_unused () {
    ROOT="${IMAGE_ROOTFS}"
    BB_DIRS="usr/lib/busybox/usr/bin usr/lib/busybox/usr/sbin"
    PATHS="usr/bin usr/sbin"

    for d in $BB_DIRS; do
        for f in "${ROOT}/${d}"/*; do
            [ -e "$f" ] || continue
            name=$(basename "$f")
            used="no"

            for p in $PATHS; do
                tgt="${ROOT}/${p}/${name}"
                [ -L "$tgt" ] || continue
                r=$(readlink "$tgt" || true)
                case "$r" in */busybox*|/usr/lib/busybox/*) used="yes";; esac
            done

            [ "$used" = "yes" ] || { echo "DELETE unused busybox applet: $d/$name"; rm -f "$f"; }
        done
    done
}
