#!/usr/bin/env python3
# Copyright (c) Qualcomm Technologies, Inc. and/or its subsidiaries.
# SPDX-License-Identifier: GPL-2.0-only

"""Fix gpu_parent_remove() return type int->void in qcom_io_mem.c (kernel 7.x compat)."""
import re, sys

f = sys.argv[1]
src = open(f).read()

# platform_driver.remove: int -> void, remove return 0
src = re.sub(
    r'static int (gpu_parent_remove\(struct platform_device \*pdev\))',
    r'static void \1',
    src)

# Remove 'return 0;' inside gpu_parent_remove body
# Match the function body between the signature and next static/module declaration
src = re.sub(
    r'(static void gpu_parent_remove[^{]+\{[^}]*)\n\treturn 0;\n(\})',
    r'\1\n\2',
    src, flags=re.DOTALL)

open(f, 'w').write(src)
