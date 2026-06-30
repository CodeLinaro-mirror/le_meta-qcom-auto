DEPENDS:append:gen5 = " kernel-basedevicetree"
do_make_dtb[depends] += "${@bb.utils.contains_any('SOC_FAMILY', 'gen5', 'kernel-basedevicetree:do_deploy', '', d)}"
