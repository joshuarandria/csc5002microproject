#!/bin/bash

# configure variables
. "$(cd $(dirname "$0") && pwd)"/utils.sh

ARGS=$*

CLASSPATH=${CLASSPATH}${PATHSEP}${LOBBYROOMSERVERTARGETDIR}/dependency/*${PATHSEP}${LOBBYROOMSERVERTARGETDIR}/classes

CLASS=vlibtour.vlibtour_lobby_room_server.VLibTourLobbyServer

# Start the client
CMD="java --add-opens=java.base/java.lang=ALL-UNNAMED -cp ${CLASSPATH} ${CLASS} ${ARGS}"

# this script is launched by sourcing => & and export
$CMD &
echo "$!" > ~/.vlibtour/lobby_room_server
