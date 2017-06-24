
# This is an override to what JCC generates.
# By default, it seems to make two packages; we've combined it into one.
# If there are any problems, try going back to what it creates.

#
# BEGIN JCC CODE
#

import os, _cbstools

__dir__ = os.path.abspath(os.path.dirname(__file__))

class JavaError(Exception):
  def getJavaException(self):
    return self.args[0]
  def __str__(self):
    writer = StringWriter()
    self.getJavaException().printStackTrace(PrintWriter(writer))
    return "\n".join((super(JavaError, self).__str__(), "    Java stacktrace:", str(writer)))

class InvalidArgsError(Exception):
  pass

_cbstools._set_exception_types(JavaError, InvalidArgsError)
CLASSPATH = [os.path.join(__dir__, "cbstools.jar"), os.path.join(__dir__, "cbstools-lib.jar"), os.path.join(__dir__, "commons-math3-3.5.jar"), os.path.join(__dir__, "Jama-mipav.jar")]
CLASSPATH = os.pathsep.join(CLASSPATH)
_cbstools.CLASSPATH = CLASSPATH
_cbstools._set_function_self(_cbstools.initVM, _cbstools)

#
# END JCC CODE
#


# This bootstrap is from a previous attempt to JCC wrap cbstools.
# This loads the shared object file from the same directory as this file.

def __bootstrap__():
    global __bootstrap__, __loader__, __file__
    import sys, pkg_resources, imp
    __file__ = pkg_resources.resource_filename(__name__, '_cbstoolsjcc.so')
    __loader__ = None; del __bootstrap__, __loader__
    imp.load_dynamic(__name__,__file__)
__bootstrap__()
