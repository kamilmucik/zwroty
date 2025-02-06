#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

echo $SCRIPT_DIR

mvn clean

watchman watch-del-all

rm -rf node_modules && npm install --reset-cache

# npm install  --reset-cache

react-native run-android

# cd ios
# pod install
#react-native run-ios
