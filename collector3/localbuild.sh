#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

echo $SCRIPT_DIR

mvn clean

watchman watch-del-all

rm -rf node_modules && npm install

npm install @react-navigation/native
expo install react-native-screens react-native-safe-area-context
npm install @react-navigation/native-stack

#react-native run-android
rm -rfv $SCRIPT_DIR/android/app/src/main/res/drawable-*
rm -rfv $SCRIPT_DIR/android/app/src/main/res/raw

#cp -a ..app/assets/android/* …/android/app/src/main/res
mkdir -p $SCRIPT_DIR/android/app/src/main/assets

npx react-native bundle --platform android --dev false --entry-file index.js --bundle-output android/app/src/main/assets/index.android.bundle --assets-dest android/app/src/main/res

cd $SCRIPT_DIR/android/

$SCRIPT_DIR/android/gradlew clean assembleDebug

# cd $SCRIPT_DIR
echo 'scp '$SCRIPT_DIR'/android/app/build/outputs/apk/debug/app-debug.apk ubuntu@e-strix.pl:/var/www/e-strix.pl/public_html/megapack/AZwroty3.1.7.apk'

sshpass -p 'syjAkywapy1' scp $SCRIPT_DIR/android/app/build/outputs/apk/debug/app-debug.apk ubuntu@e-strix.pl:/var/www/e-strix.pl/public_html/megapack/AZwroty3.1.7.apk

