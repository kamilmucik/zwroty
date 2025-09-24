#!/bin/sh

# --------------------------------------------------------------------------
# Troubleshooting Options (uncomment as needed)
# --------------------------------------------------------------------------
if [ "${DEBUG_APP}" != "true" ]; then
  echo "Uruchamiam Zwrot Paczek"
else
  echo "Uruchamiam Zwrot Paczek w trybie debug JPDA"
#CATALINA_OPTS="$CATALINA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000"
fi

#export CATALINA_OPTS

# Setting umask so the group can write on the same files/folders by default
UMASK="0002"
export UMASK
echo "Using CATALINA_OPTS: $CATALINA_OPTS"

