FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI:append = " \
             ${@bb.utils.contains('DISTRO_FEATURES', 'early_init', 'file://0034-systemd-add-handover-support-for-early-service.patch', '', d)} \
             file://power-switch.rules \
             file://0036-systemd-journald-optimize-kmsg-reading-performance.patch \
             file://0001-systemd-udev-trigger-delay-1s-for-better-bootkpi.patch \
             ${@bb.utils.contains('MACHINE_FEATURES', 'early-ramdisk-init', 'file://0001-systemd-add-vfio-script-wait-in-systemd-init-process.patch', '', d)} \
             ${@bb.utils.contains_any('MACHINE_FEATURES', 'qti-umd', '', 'file://qti_sleep.sh', d)} \
             ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', 'file://0037-systemd-Add-wdt_ping-in-dispatch_runqueue.patch', '', d)} \
             ${@bb.utils.contains('DISTRO_FEATURES', 'qti-rumi', 'file://0031-udev-trigger-only-enable-must-part-while-leave-other.patch', '', d)} \
             ${@bb.utils.contains('MACHINE_FEATURES', 'qti-hypervisor', 'file://0001-systemd-sleep-change-suspend-state-list.patch', '', d)} \
             ${@bb.utils.contains('MACHINE_FEATURES', 'deepsleep', 'file://0002-systemd-add-deepsleep-support.patch', '', d)} \
             file://0035-systemd-Change-job-print-method-to-Normal-way.patch \
             ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', 'file://0001-systemd-shutdown-shorten-file-sync-timeout.patch', '', d)} \
             ${@bb.utils.contains('MACHINE_FEATURES', 'qti-umd', 'file://0001-systemd-Remove-systemd-watchdog-ping-condition.patch', '', d)} \
             file://0001-systemd-sleep-ping-watchdog-before-writing-suspend-s.patch \
             file://0001-systemd-add-restorecon-operation.patch \
             ${@bb.utils.contains('MACHINE_FEATURES', 'early-ramdisk-init', 'file://0001-systemd-add-i2c-module-wait-in-systemd-init-process.patch', '', d)} \
"

SRC_URI:append:sa7255 = " \
             file://0001-systemd-assign-prime-core-to-manager_dispatch_load_q.patch \
"

SRC_URI:append:gen5 = " \
             ${@bb.utils.contains('MACHINE_FEATURES', 'early-ramdisk-init', 'file://0001-systemd-add-mm-vfio-script-wait-in-systemd-init-proc.patch', '', d)} \
"

# bus-objects.c fail-cleanup block uses stale type names from before the rename
# commit (struct vtable_member->BusVTableMember, Hashmap*->Set*, hashmap_*/set_*).
do_compile:prepend() {
    f="${S}/src/libsystemd/sd-bus/bus-objects.c"
    sed -i 's/\bstruct vtable_member\b/BusVTableMember/g' "$f"
    sed -i 's/\bHashmap \*map;/Set *map;/' "$f"
    sed -i 's/\bhashmap_get(map,/set_get(map,/g' "$f"
    sed -i 's/\bhashmap_remove(map,/set_remove(map,/g' "$f"
}

do_install:append() {
   if ${@bb.utils.contains_any('MACHINE_FEATURES', 'qti-umd', 'false', 'true', d)} ; then
      install -d ${D}/${base_libdir}/systemd/system-sleep
      install -m 0755 ${UNPACKDIR}/qti_sleep.sh -D ${D}/${base_libdir}/systemd/system-sleep/qti_sleep.sh
   fi
}
