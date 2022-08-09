# --------------------------------------------------------------------------
# Required Settings
# --------------------------------------------------------------------------

# Set D3S application options
CATALINA_OPTS="-Xmx1g"
CATALINA_OPTS="$CATALINA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000"

# --------------------------------------------------------------------------
# Troubleshooting Options (uncomment as needed)
# --------------------------------------------------------------------------

# Enable JPDA Suspend
# JPDA_SUSPEND=y

# Dump JAX-WS SOAP request/response
# CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.xml.ws.transport.http.HttpAdapter.dump=true"

# Enable JMX local monitoring
#CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.management.jmxremote"

export CATALINA_OPTS

# Setting umask so the group can write on the same files/folders by default
UMASK="0002"
export UMASK

echo "Using CATALINA_OPTS: $CATALINA_OPTS"
echo "Using umask $UMASK"
