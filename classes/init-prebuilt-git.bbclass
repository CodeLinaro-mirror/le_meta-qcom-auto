python init_prebuilt_git_handler() {
    import os
    import subprocess
    src_dir_root = d.getVar('SRC_DIR_ROOT') or ''
    if not src_dir_root:
        return
    for variant in ['prebuilt_HY11', 'prebuilt_HY22']:
        prebuiltdir = os.path.join(src_dir_root, variant)
        if not os.path.isdir(prebuiltdir):
            continue
        if os.path.isdir(os.path.join(prebuiltdir, '.git')):
            continue
        bb.note('init-prebuilt-git: initializing git in %s' % prebuiltdir)
        subprocess.run(['git', 'init'], cwd=prebuiltdir, check=True)
        subprocess.run(['git', 'add', '.'], cwd=prebuiltdir, check=True)
        subprocess.run(['git', 'commit', '-m', 'Init new git project'],
                       cwd=prebuiltdir, check=True)
}

addhandler init_prebuilt_git_handler
init_prebuilt_git_handler[eventmask] = "bb.event.ConfigParsed"
