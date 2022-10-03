#!/bin/bash


export VLIBTOURDIR=${PWD}

asadmin undeploy vlibtour-tour-management-bean
asadmin stop-database
asadmin stop-domain domain1
asadmin start-domain domain1
asadmin start-database
asadmin deploy vlibtour-tour-management-bean/target/vlibtour-tour-management-bean.jar
sleep 1
echo "hello" > 1
../Scripts/tour_management_admin_client.sh
