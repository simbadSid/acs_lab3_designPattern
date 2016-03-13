#!/bin/sh




currentDir=`pwd`
export CLASSPATH=$currentDir/bin


javac -d bin src/*/*
