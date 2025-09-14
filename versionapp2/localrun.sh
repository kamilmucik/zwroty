#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.0.16.1.jdk/Contents/Home


SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

echo $SCRIPT_DIR

mvn -v
gradle -v
node -v
npm -v

mvn clean

watchman watch-del-all

rm -rf node_modules && npm install --reset-cache

react-native run-android

# cd ios
# pod install
# react-native run-ios
