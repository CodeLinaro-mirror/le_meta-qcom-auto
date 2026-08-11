# Remove bash-dependent sub-packages to comply with INCOMPATIBLE_LICENSE GPL-3.0*.
# alsaconf, alsa-info, and alsabat are shell scripts that RDEPEND on bash (GPLv3).
# populate_packages:prepend in the base recipe injects these into RDEPENDS:alsa-utils
# via d.setVar(), so the normal :remove override cannot strip them — we must do it
# here, after the dynamic packaging step, by appending to the same task.

python populate_packages:append() {
    pn = d.getVar("PN")
    bash_pkgs = ["alsa-utils-alsaconf", "alsa-utils-alsa-info", "alsa-utils-alsabat"]
    rdep_var = "RDEPENDS:" + pn
    rdeps = (d.getVar(rdep_var) or "").split()
    filtered = [p for p in rdeps if p not in bash_pkgs]
    d.setVar(rdep_var, " ".join(filtered))
}
