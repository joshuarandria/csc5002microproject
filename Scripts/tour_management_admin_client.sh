#!/bin/bash

# configure variables
. "$(cd $(dirname "$0") && pwd)"/utils.sh

ARGS=$*

PATHSEP=':'

CLASSPATH=${TOURADMINCLIENTTARGETDIR}/dependency/*${PATHSEP}${TOURADMINCLIENTTARGETDIR}/classes

CLASS=vlibtour.vlibtour_tour_management_admin_client.VlibTourTourManagementAdminClient


# Start the client
CMD="java --add-opens=java.base/java.lang=ALL-UNNAMED -cp ${CLASSPATH} ${CLASS} ${ARGS}"
echo $CMD

$CMD
