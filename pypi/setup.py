
# Always prefer setuptools over distutils
from setuptools import setup, find_packages
from os import path

here = path.abspath(path.dirname(__file__))
long_description = ''

setup(
    name='cbstools',
    version='1.1.0',
    description='CBS High Resolution Image Processing Tools in Python',
    long_description=long_description,
    url='https://github.com/piloubazin/cbstools-public',
    author='Pierre-Louis Bazin, Julia Huntenburg, Nathaniel Kofalt',
    author_email='ju.huntenburg@gmail.com',
    license='CC BY-SA 4.0',
    classifiers=['Development Status :: 3 - Alpha',
                 'Intended Audience :: Researchers',
                 'Topic :: High Resolution Image Processing ',
                 'License :: Creative Commons Attribution-ShareAlike 4.0 International License :: CC BY-SA 4.0',
                 'Programming Language :: Python :: 2.7',
                 ],
    keywords='MRI high-resolution laminar',
    package_data={'cbstools': ['_cbstools.so',
                                  'cbstools.jar',
                                  'cbstools-lib.jar',
                                  'commons-math3-3.5.jar',
                                  'Jama-mipav.jar']},
    packages=find_packages(),
)
