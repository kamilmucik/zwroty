#!/bin/bash 

keytool -genkey -v -keystore lkequiz.keystore -alias lkequiz -keyalg RSA -keysize 2048 -validity 10000
# CN=Kamil Mucik, OU=e-Strix Kamil Mucik, O=e-Strix Kamil Mucik, L=Lodz, ST=Lodz, C=PL

mv lkequiz.keystore android/app


keytool -genkey -v -keystore upload-keystore.jks -alias lkequiz -keyalg RSA -keysize 2048 -validity 10000

keytool -export -rfc -keystore lkequiz.keystore -alias lkequiz -file upload_certificate.pem