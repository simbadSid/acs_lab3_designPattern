#!/bin/sh




currentDir=`pwd`
export CLASSPATH=$currentDir/bin

echo "======================="
echo $currentDir
echo $CLASSPATH
echo "======================="

java socket.TestServer

