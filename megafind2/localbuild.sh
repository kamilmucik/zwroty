#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

echo $SCRIPT_DIR

mvn clean

npm install

rm -rf android/app/src/main/res/drawable-*

npx mkdirp android/app/src/main/assets/ && react-native bundle --platform android --dev false --entry-file index.js --bundle-output android/app/src/main/assets/index.android.bundle --assets-dest android/app/src/main/res/

cd $SCRIPT_DIR/android/  

$SCRIPT_DIR/android/gradlew assembleDebug

cd $SCRIPT_DIR
scp $SCRIPT_DIR/android/app/build/outputs/apk/debug/app-debug.apk ubuntu@e-strix.pl:/var/www/e-strix.pl/public_html/megapack/Mega_1.1.1.apk

# npx react-native info
