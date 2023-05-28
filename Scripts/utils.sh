#!/bin/bash

# in this shell script, only idempotent assignments of shell variables for
# the Java class-path

# variable VLIBTOURDIR defined in ../run_scenario_w_mapviewer.sh

export MAVEN_REPOS=${HOME}/.m2/repository

export PATHSEP=':'

export TOURADMINCLIENTTARGETDIR=${VLIBTOURDIR}/vlibtour-tour-management-system/vlibtour-tour-management-admin-client/target

export LOBBYROOMSERVERTARGETDIR=${VLIBTOURDIR}/vlibtour-lobby-room-system/vlibtour-lobby-room-server/target

export VISITEMULATIONSERVERTARGETDIR=${VLIBTOURDIR}/vlibtour-visit-emulation-system/vlibtour-visit-emulation-server/target

export SCENARIOTARGETDIR=${VLIBTOURDIR}/vlibtour-scenario/target

