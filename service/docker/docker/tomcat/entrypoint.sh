#!/bin/bash

rm -rf /opt/tomcat/webapps/*
cp /opt/ROOT.war /opt/tomcat/webapps/ROOT.war

if [ -d "/opt/artifacts/bin" ]; then
  cp -ar /opt/artifacts/bin /opt/tomcat/
fi
if [ -d "/opt/artifacts/languages" ]; then
  cp -ar /opt/artifacts/languages/* /usr/share/tesseract-ocr/4.00/tessdata
fi

/opt/tomcat/bin/catalina.sh run
