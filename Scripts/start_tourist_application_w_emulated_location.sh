#!/bin/bash

# configure variables
. "$(cd $(dirname "$0") && pwd)"/utils.sh

ARGS=$*

CLASSPATH=${CLASSPATH}${PATHSEP}${SCENARIOTARGETDIR}/dependency/*${PATHSEP}${SCENARIOTARGETDIR}/classes

CLASS=vlibtour.vlibtour_scenario.VLibTourVisitTouristApplication

# Start the client
CMD="java --add-opens=java.base/java.lang=ALL-UNNAMED -cp ${CLASSPATH} ${CLASS} ${ARGS}"

# this script is launched by sourcing => & and export
$CMD &
echo "$!" >> ~/.vlibtour/tourist_applications
