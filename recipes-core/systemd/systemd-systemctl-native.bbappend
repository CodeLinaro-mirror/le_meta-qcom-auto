do_install:prepend() {
  sed -i 's/\[^\%](%%)/[^\%]?(%%)/g' ${UNPACKDIR}/systemctl
}
