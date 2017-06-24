#!/usr/bin/env bash
set -euo pipefail
unset CDPATH; cd "$( dirname "${BASH_SOURCE[0]}" )"; cd "$(pwd -P)"

# Get system info
localOs=$( uname -s | tr '[:upper:]' '[:lower:]' )

if [[ "$localOs" == "linux" ]]; then
	apt-get update -y
	apt-get install -y wget tar default-jdk python python-pip
	apt-get install -y jcc

	# These are ran one by one, for fear of pip's lack of dependency ordering
	pip install --upgrade pip
	pip install --upgrade setuptools
	pip install --upgrade wheel


elif [[ "$localOs" == "darwin" ]]; then

	# Brew install X fails if X is already installed!
	# This is a silly workaround suggested by Travis

	brew outdated coreutils || brew upgrade coreutils
	brew install bash findutils gnu-sed

	# Load GNU coreutils, findutils, and sed into path
	suffix="libexec/gnubin"
	export PATH
	PATH="$(brew --prefix coreutils)/$suffix:$(brew --prefix findutils)/$suffix:$(brew --prefix gnu-sed)/$suffix:$PATH"

	# OSX has shasum. CentOS has sha1sum. Ubuntu has both.
	alias sha1sum="shasum -a 1"

	# These are ran one by one, for fear of pip's lack of dependency ordering
	pip install --upgrade pip
	pip install --upgrade setuptools
	pip install --upgrade wheel

	# No homebrew package for JCC; we need to set it up manually.
	test -d lib/jcc || (

		# I didn't see any downloads or specific releases
		# https://lucene.apache.org/pylucene/jcc/index.html
		brew install svn
		svn co https://svn.apache.org/repos/asf/lucene/pylucene/trunk/jcc lib/jcc
		cd lib/jcc

		echo "Build"
		python setup.py build

		echo "Install"
		python setup.py install
	)

	brew install tree


	env

	java -version
	javac -version

	tree $JAVAHOME

	ls -la $JAVAHOME


else
	echo "Platform $localOs is not scripted; port as desired!"
fi

