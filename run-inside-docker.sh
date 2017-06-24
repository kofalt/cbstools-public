#!/usr/bin/env bash
set -euo pipefail
unset CDPATH; cd "$( dirname "${BASH_SOURCE[0]}" )"; cd "$(pwd -P)"

# When you change which image this uses, be sure to also update .travis.yml!

docker run -v "`pwd`:/workspace" -it buildpack-deps:zesty bash -c 'cd workspace && ./setup.sh && ./build.sh'
