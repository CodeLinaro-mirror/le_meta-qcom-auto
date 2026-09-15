SUMMARY = "GVM touch proxy simple Wayland client"
DESCRIPTION = "Builds the gvm-touch-proxy client from the Weston source tree as a standalone Yocto package."
HOMEPAGE = "https://git.codelinaro.org/"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a"

inherit meson pkgconfig

DEPENDS = "\
    libxml2 \
    wayland \
    wayland-native \
    wayland-protocols \
    wayland-ivi-extension \
    pkgconfig-native \
"

CODE_DIR = "vendor/qcom/opensource/display/weston-sdm-extension"
SRC_URI = "${PATH_TO_REPO}/${CODE_DIR}/.git;protocol=${PROTO};destsuffix=${CODE_DIR};usehead=1"
SRCREV = "${AUTOREV}"

PACKAGECONFIG ??= ""
PACKAGECONFIG[coverage] = "-Db_coverage=true,-Db_coverage=false,"

S = "${UNPACKDIR}/${CODE_DIR}/gvm-touch-proxy"
B = "${UNPACKDIR}/build"

do_configure:prepend() {
    install -m 0644 \
        ${STAGING_DATADIR}/wayland-protocols/stable/ivi-application/ivi-application.xml \
        ${UNPACKDIR}/${CODE_DIR}/protocol/ivi-application.xml

    # gvm-touch-proxy.c defines several functions that are missing from the internal
    # header, causing implicit-function-declaration errors in the test binary.
    h="${UNPACKDIR}/${CODE_DIR}/gvm-touch-proxy/gvm-touch-proxy-internal.h"
    grep -q 'apply_ivi_input_policy(' "$h" || \
        sed -i '/^void   apply_ivi_input_policy_for_port/i void   apply_ivi_input_policy(struct client_state *state);' "$h"
    grep -q 'void touch_down(' "$h" || \
        sed -i '/^#endif.*GVM_TOUCH_PROXY_INTERNAL/i void touch_down(void *data, struct wl_touch *wl_touch, uint32_t serial, uint32_t time, struct wl_surface *surface, int32_t id, wl_fixed_t x_w, wl_fixed_t y_w);' "$h"
    grep -q 'void touch_up(' "$h" || \
        sed -i '/^#endif.*GVM_TOUCH_PROXY_INTERNAL/i void touch_up(void *data, struct wl_touch *wl_touch, uint32_t serial, uint32_t time, int32_t id);' "$h"
    grep -q 'void touch_motion(' "$h" || \
        sed -i '/^#endif.*GVM_TOUCH_PROXY_INTERNAL/i void touch_motion(void *data, struct wl_touch *wl_touch, uint32_t time, int32_t id, wl_fixed_t x_w, wl_fixed_t y_w);' "$h"
    grep -q 'void touch_cancel(' "$h" || \
        sed -i '/^#endif.*GVM_TOUCH_PROXY_INTERNAL/i void touch_cancel(void *data, struct wl_touch *wl_touch);' "$h"
    grep -q 'void touch_frame(' "$h" || \
        sed -i '/^#endif.*GVM_TOUCH_PROXY_INTERNAL/i void touch_frame(void *data, struct wl_touch *wl_touch);' "$h"
    grep -q 'void output_handle_mode(' "$h" || \
        sed -i '/^#endif.*GVM_TOUCH_PROXY_INTERNAL/i void output_handle_mode(void *data, struct wl_output *wl_output, uint32_t flags, int width, int height, int refresh);' "$h"
    grep -q 'void output_handle_name(' "$h" || \
        sed -i '/^#endif.*GVM_TOUCH_PROXY_INTERNAL/i void output_handle_name(void *data, struct wl_output *wl_output, const char *name);' "$h"
    grep -q 'find_output_by_name(' "$h" || \
        sed -i '/^#endif.*GVM_TOUCH_PROXY_INTERNAL/i OutputInfo *find_output_by_name(struct client_state *state, const char *name);' "$h"
    grep -q 'find_output_for_port(' "$h" || \
        sed -i '/^#endif.*GVM_TOUCH_PROXY_INTERNAL/i OutputInfo *find_output_for_port(struct client_state *state, PortInfo *port);' "$h"
}

# pvm-touch-monitor.c uses clock_gettime(CLOCK_MONOTONIC, &ev.time) but
# input_event.time is struct __kernel_old_timeval (tv_sec/tv_usec), not struct timespec.
# Fix the call site to use a local timespec then convert.
do_compile:prepend() {
    f="${UNPACKDIR}/${CODE_DIR}/gvm-touch-proxy/test/pvm-touch-monitor.c"
    grep -q 'clock_gettime.*ev\.time' "$f" || return 0
    sed -i 's/clock_gettime(CLOCK_MONOTONIC, \&ev\.time);/{ struct timespec _ts; clock_gettime(CLOCK_MONOTONIC, \&_ts); ev.time.tv_sec = _ts.tv_sec; ev.time.tv_usec = _ts.tv_nsec \/ 1000; }/' "$f"
}

EXTRA_OEMESON += "${@bb.utils.contains('PACKAGECONFIG', 'coverage', '-Db_coverage=true', '-Db_coverage=false', d)}"

do_install:append() {
    install -d ${D}${bindir}
    install -m 0644 ${S}/shared_touch.ini ${D}${bindir}/shared_touch.ini

    install -d ${D}${sysconfdir}/udev/rules.d
    install -m 0644 ${S}/99-gvm-touch-proxy-seat.rules ${D}${sysconfdir}/udev/rules.d/99-gvm-touch-proxy-seat.rules
    install -m 0644 ${S}/test/99-pvm-touch-monitor.rules ${D}${sysconfdir}/udev/rules.d/99-pvm-touch-monitor.rules
}

FILES:${PN} += "\
    ${bindir}/gvm-touch-proxy \
    ${bindir}/pvm-touch-monitor \
    ${bindir}/shared_touch.ini \
    ${bindir}/gvm-touch-proxy-test \
    ${sysconfdir}/udev/rules.d/99-gvm-touch-proxy-seat.rules \
    ${sysconfdir}/udev/rules.d/99-pvm-touch-monitor.rules \
"
