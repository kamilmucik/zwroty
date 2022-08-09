#!/bin/bash

#cp /opt/d3s_home/tmp/nl-activemq.xml /opt/d3s_home/conf/nl-activemq.xml
#cp /opt/d3s_home/tmp/setenv.sh /opt/d3s_home/catalina/bin/setenv.sh
/opt/node_exporter &

/opt/tomcat/bin/catalina.sh run
