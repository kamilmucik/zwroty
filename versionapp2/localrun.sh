#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.0.16.1.jdk/Contents/Home
mkdir -p "$HOME/.tmp/metro-cache"
export TMPDIR="$HOME/.tmp"

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

echo $SCRIPT_DIR

# nvm use 16

mvn -v
# gradle -v
node -v
npm -v

rm -rf ~/.gradle/caches/kotlin
rm -rf android/.gradle/
# rm -rf android/build/kotlin/sessions
rm -rf android/build
rm -rf android/app/build

# exit
mvn clean

watchman watch-del-all
watchman watch-del-all || true
rm -rf $TMPDIR/metro-* $TMPDIR/metro-cache || true
rm -rf node_modules/.cache/metro

rm -rf node_modules 
rm -rf package-lock.json 

cd android/
./gradlew clean
# ./gradlew assembleDebug
./gradlew build --refresh-dependencies
pkill -f kotlin
rm -rf build/kotlin/sessions

npm install --reset-cache

react-native run-android

# cd ios
# pod install
# react-native run-ios
