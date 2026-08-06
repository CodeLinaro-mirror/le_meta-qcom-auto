
VOLATILE_BINDS = "\
/var/build.prop /etc/build.prop\n\
/var/data /etc/data/\n\
/var/usb /etc/usb/\n\
/var/misc/wifi /etc/misc/wifi/\n\
/var/bluetooth /etc/bluetooth/\n\
/var/allplay /etc/allplay/\n\
/var/gptp_cfg.ini /etc/gptp_cfg.ini\n\
/var/smack/accesses.d /etc/smack/accesses.d/\n\
"

# shikra-evk-auto is a bare EVK board with no AllPlay/build.prop/gptp/smack
# Android-automotive use case (unlike the nord/sa8797-ride-sx machines the
# override above targets) — stock meta-qcom's shikra-evk never carries this
# bbappend at all and instead gets oe-core volatile-binds.bb's own default
# VOLATILE_BINDS (var-volatile-lib/cache/spool/srv). Restore that same
# default here so shikra-evk-auto matches shikra-evk's actual bind-mount
# service set, without touching the override above that nord/sa8797-ride-sx
# still rely on.
VOLATILE_BINDS:shikra-evk-auto = "\
${localstatedir}/volatile/lib ${localstatedir}/lib\n\
${localstatedir}/volatile/cache ${localstatedir}/cache\n\
${localstatedir}/volatile/spool ${localstatedir}/spool\n\
${localstatedir}/volatile/srv /srv\n\
"
