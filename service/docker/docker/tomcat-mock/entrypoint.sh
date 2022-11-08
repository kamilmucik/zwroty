#!/bin/bash

/opt/node_exporter &

/opt/promtail-linux-amd64 -config.file  /opt/config-promtail.yml &

rm -rf /opt/tomcat/webapps/*
cp /opt/ROOT.war /opt/tomcat/webapps/ROOT.war

/opt/tomcat/bin/catalina.sh run
