#!/bin/bash
#Tworzy plik abb, który mozna wrzucic do android studio przez google console
# https://www.youtube.com/watch?v=5tgcogEoIiQ&list=PL8kfZyp--gEXs4YsSLtB3KqDtdOFHMjWZ&index=38
# https://reactnative.dev/docs/signed-apk-android
# generowanie bundlu i polityki
# https://www.youtube.com/watch?v=A3--3Ozxz6o
# 

# generujemy klucz
#sudo keytool -genkey -v -keystore lkequiz3.keystore -alias lkequiz3 -keyalg RSA -keysize 2048 -validity 10000
#pass: 123qweasdzxc
#CN=Kamil Mucik, OU=Developer, O=e-Strix Kamil Mucik, L=Lodz, ST=Lodzkie, C=PL

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

echo $SCRIPT_DIR

# mvn clean

# watchman watch-del-all

# rm -rf node_modules && npm install

cd $SCRIPT_DIR/android/

$SCRIPT_DIR/android/gradlew clean

rm -rfv $SCRIPT_DIR/android/app/src/main/res/drawable-*
rm -rfv $SCRIPT_DIR/android/app/src/main/res/raw

# tworzenie APK releasowe w [PATH]/lkequiz/mobileapp/android/app/build/outputs/apk/release
$SCRIPT_DIR/android/gradlew assembleRelease

# tworzenie ABB releasowe w [PATH]/lkequiz/mobileapp/android/app/build/outputs/bundle/release/app-release.aab
#kopiuj klucz aplikacji trust store do android/app
#podmiana warotsci hasla i plik truststore w gradle.properties
$SCRIPT_DIR/android/gradlew bundleRelease

# scp /Users/kamilmuc/ws/lkequiz/mobileapp/android/app/build/outputs/apk/release/app-release.apk
# cd $SCRIPT_DIR
echo 'scp '$SCRIPT_DIR'/android/app/build/outputs/apk/release/app-release.apk ubuntu@e-strix.pl:/var/www/e-strix.pl/public_html/megapack/LKEQuiz3.0.1-rc.apk'

sshpass -p 'syjAkywapy1' scp $SCRIPT_DIR/android/app/build/outputs/apk/release/app-release.apk ubuntu@e-strix.pl:/var/www/e-strix.pl/public_html/megapack/LKEQuiz3.0.1-rc.apk






# uruchamia apliakcję na emulatorze w modzie release
# npx react-native build-android --mode=release