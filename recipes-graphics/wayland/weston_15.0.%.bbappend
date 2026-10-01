FILESEXTRAPATHS:append := " :${THISDIR}/weston:"

SRC_URI += "file://0001-weston-15-install-sdm-backend-helper-interfaces.patch \
            file://0002-weston-15-port-qti-sdm-gbm-interfaces.patch \
"

do_install:append() {
    install -d ${D}${datadir}/weston
}

FILES:${PN}-dev:remove = "${libdir}/lib*${SOLIBSDEV}"
FILES:${PN}-dev += "${libdir}/libweston-${WESTON_MAJOR_VERSION}${SOLIBSDEV}"

FILES:libweston-${WESTON_MAJOR_VERSION} += "${libdir}/libsession-helper${SOLIBSDEV} \
                                            ${libdir}/liblibinput-backend${SOLIBSDEV} \
                                            ${libdir}/libbacklight${SOLIBSDEV}"
