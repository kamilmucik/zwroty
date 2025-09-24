#!/bin/bash
#Tworzy plik abb, który mozna wrzucic do android studio przez google console
# https://www.youtube.com/watch?v=5tgcogEoIiQ&list=PL8kfZyp--gEXs4YsSLtB3KqDtdOFHMjWZ&index=38
# https://reactnative.dev/docs/signed-apk-android
# generowanie bundlu i polityki
# https://www.youtube.com/watch?v=A3--3Ozxz6o
#

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.0.16.1.jdk/Contents/Home

# kamilmuc@MacBook-Pro-kamilmuc versionapp2 % ./localrun.sh 
# /Users/kamilmuc/ws/test/zwr/20250913/zwroty/versionapp2
# Apache Maven 3.8.8 (4c87b05d9aedce574290d1acc98575ed5eb6cd39)
# Maven home: /Users/kamilmuc/ws/app/apache-maven-3.8.8
# Java version: 11.0.16.1, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-11.0.16.1.jdk/Contents/Home
# Default locale: pl_PL, platform encoding: UTF-8
# OS name: "mac os x", version: "15.5", arch: "aarch64", family: "mac"
# ./localrun.sh: line 11: gradle: command not found
# v16.20.2
# 8.19.4



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
echo 'scp '$SCRIPT_DIR'/android/app/build/outputs/apk/release/app-release.apk ubuntu@e-strix.pl:/home/ubuntu/wp/releases/megapack/2.5.0/version-release.apk'

sshpass -p 'xxx' scp $SCRIPT_DIR/android/app/build/outputs/apk/release/app-release.apk ubuntu@e-strix.pl:/home/ubuntu/wp/releases/megapack/2.5.0/version-mega.apk
# sshpass -p 'xxx' scp $SCRIPT_DIR/android/app/build/outputs/apk/release/app-release.apk ubuntu@e-strix.pl:/home/ubuntu/wp/releases/megapack/2.5.0/version-rc.apk


echo "Wrzutka zakończona"



# uruchamia apliakcję na emulatorze w modzie release
# npx react-native build-android --mode=release
