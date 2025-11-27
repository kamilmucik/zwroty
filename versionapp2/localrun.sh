#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.0.16.1.jdk/Contents/Home


SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

echo $SCRIPT_DIR

mvn -v
gradle -v
node -v
npm -v

mvn clean

#cd $SCRIPT_DIR/android/
#$SCRIPT_DIR/android/gradlew clean
rm -rfv $SCRIPT_DIR/android/app/src/main/res/drawable-*
rm -rfv $SCRIPT_DIR/android/app/src/main/res/raw
cd $SCRIPT_DIR
watchman watch-del-all

#rm -rfv .bundle
rm -rfv node_modules
#rm -rf node_modules
#rm -rfv $SCRIPT_DIR/android/build
#rm -rfv $SCRIPT_DIR/android/app/build
#rm -rfv $SCRIPT_DIR/android/.gradle
#sudo chown -R $USER .

#rm package-lock.json


npm install

#sudo ls -lath /var/folders/zz/zyxvpxvq6csfxvn_n0000000000000/T/metro-cache/00
#sudo rm -rfv /var/folders/zz/zyxvpxvq6csfxvn_n0000000000000/T/metro-cache/00
#sudo mkdir -p /var/folders/zz/zyxvpxvq6csfxvn_n0000000000000/T/metro-cache/00
#
#sudo chmod -R 777 /var/folders/zz/zyxvpxvq6csfxvn_n0000000000000/T/metro-cache/
#sudo chown -R "$USER" /var/folders/zz/zyxvpxvq6csfxvn_n0000000000000/T/metro-cache
#
#sudo chown -R `whoami` ~/.npm
#sudo chown -R `whoami` /usr/local/lib/node_modules
#sudo chown -R `whoami` /var/folders/zz/zyxvpxvq6csfxvn_n0000000000000/T/metro-cache
#sudo chown -R `whoami` /var/folders/zz/zyxvpxvq6csfxvn_n0000000000000/T/



npx react-native start
#sudo npx react-native start
# cd ios
# pod install
# react-native run-ios
