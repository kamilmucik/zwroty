#!/bin/sh
# --------------------------------------------------------------------------
# Required Settings
# --------------------------------------------------------------------------
#D3S_HOME=/opt/app/d3s
##  Validate setup
#if [ -z "$D3S_HOME"  ]; then
#  echo "setenv:ERROR The D3S_HOME environment variable is not defined correctly"
#  echo "setenv:ERROR Please use the custom catalina script provided in the D3S"
#  echo "setenv:ERROR installation binary dir to startup and shutdown Catalina"
#  exit -1
#fi

# Set D3S application options
CATALINA_OPTS="-Xmx1g "
#CATALINA_OPTS="$CATALINA_OPTS -Dd3s.server.jel.ch.config.path=$D3S_HOME/conf/gr-configuration.xml"
#CATALINA_OPTS="$CATALINA_OPTS -Dd3s.server.jel.ch.enable=true"
#CATALINA_OPTS="$CATALINA_OPTS -Dd3s.home=$D3S_HOME"
#CATALINA_OPTS="$CATALINA_OPTS -Dd3s.log.dir=$CATALINA_BASE/logs"
#CATALINA_OPTS="$CATALINA_OPTS -Dd3s.tmp.dir=$CATALINA_BASE/temp"
#CATALINA_OPTS="$CATALINA_OPTS -Dlog4j.configurationFile=file:$D3S_HOME/conf/log4j2-config.xml"
#CATALINA_OPTS="$CATALINA_OPTS -Declipselink.configuration=file:$D3S_HOME/conf/db.properties"
#CATALINA_OPTS="$CATALINA_OPTS -Dd3s.server.jel.dumpws.path=/opt/app/d3s/data/ws_errors"
#CATALINA_OPTS="$CATALINA_OPTS -Dd3s.server.jel.dumpws.level=ERRORS"
#CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.xml.ws.fault.SOAPFaultBuilder.disableCaptureStackTrace=true"
#CATALINA_OPTS="$CATALINA_OPTS -Dd3s.server.maxcpuusage=60"
#CATALINA_OPTS="$CATALINA_OPTS -Dd3s.server.maxamqstoretemp=60"
#CATALINA_OPTS="$CATALINA_OPTS -Dd3s.server.maxamqstorepercent=60"
#CATALINA_OPTS="$CATALINA_OPTS -Dd3s.server.ismainnode=false"

# Thread dump before server crash
JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError"
JAVA_OPTS="$JAVA_OPTS -XX:HeapDumpPath=/opt/tomcat/logs/dumps.hprof"

# --------------------------------------------------------------------------
# Troubleshooting Options (uncomment as needed)
# --------------------------------------------------------------------------
if [ "${DEBUG_APP}" != "true" ]; then
  echo "Starting D3S"
else
  echo "Starting D3S in debug mode"
CATALINA_OPTS="$CATALINA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000"
fi

## Enable JMX remote monitoring
#CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.management.jmxremote.port=9204 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"

export CATALINA_OPTS

# Setting umask so the group can write on the same files/folders by default
#UMASK="0002"
#export UMASK
#
#echo "Using D3S_HOME: $D3S_HOME"
#echo "Using CATALINA_OPTS: $CATALINA_OPTS"
#echo "Using umask $(umask)"

