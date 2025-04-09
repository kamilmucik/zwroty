#!/bin/bash

rm -rf /opt/tomcat/webapps/*
cp /opt/ROOT.war /opt/tomcat/webapps/ROOT.war

if [ -d "/opt/artifacts/bin" ]; then
  cp -ar /opt/artifacts/bin /opt/tomcat/
fi
if [ -d "/opt/artifacts/languages" ]; then
  cp -ar /opt/artifacts/languages/* /usr/share/tesseract-ocr/4.00/tessdata
fi

## manage debug options
#if [ "${DEBUG_APP}" != "true" ]; then
##  log INFO "Starting D3S"
#  RUN_CMD="run"
#else
##  log INFO "Starting D3S in debug mode"
#  # set a default value for JPDA_ADDRESS that is not tomcat's default (localhost:8000)
##  [ -z "${JPDA_ADDRESS}" ] && export JPDA_ADDRESS=8000
#-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:800
#  RUN_CMD="jpda run"
#fi
if [ "${DEBUG_APP}" != "true" ]; then
  echo "Starting D3S"
  RUN_CMD="run"
else
  echo "Starting D3S in debug mode"
CATALINA_OPTS="$CATALINA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000"
RUN_CMD="run"
fi

/opt/tomcat/bin/catalina.sh ${RUN_CMD}
