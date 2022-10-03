#!/bin/bash

# killall java # to think about the possibility of processes from old executions!

export VLIBTOURDIR=${PWD}

# create directory to save process numbers (in order to kill them at the end)
if [ -d ~/.vlibtour ]; then
    rm -f ~/.vlibtour/*
else
    mkdir -p ~/.vlibtour
fi

if netstat -nlp 2> /dev/null | grep -q "[[:space:]]8080"; then
    docker stop glassfish
    docker rm glassfish
fi
if netstat -nlp 2> /dev/null | grep -q "[[:space:]]4848"; then
    docker stop glassfish
    docker rm glassfish
fi
if netstat -nlp 2> /dev/null | grep -q "[[:space:]]8181"; then
    docker stop glassfish
    docker rm glassfish
fi

echo -e "\e[1;44m  \e[1;33m ========================================================== \e[0m "
echo -e "\e[1;44m  \e[1;33m =          Start docker container for glassfish,         = \e[0m "
echo -e "\e[1;44m  \e[1;33m = undeploy previous vlibtour-tour-management application = \e[0m "
echo -e "\e[1;44m  \e[1;33m =          and start again glassfish and derby           = \e[0m "
echo -e "\e[1;44m  \e[1;33m ========================================================== \e[0m "
docker run -itd --name glassfish -p 3700:3700 -p 8080:8080 -p 4848:4848 -p 8181:8181 -v $PWD/vlibtour-tour-management-system:/root/vlibtour-tour-management-system glassfish6-tsp-csc
docker exec glassfish asadmin undeploy vlibtour-tour-management-bean
docker exec glassfish asadmin stop-database
docker exec glassfish asadmin stop-domain domain1
docker exec glassfish asadmin start-domain domain1
docker exec glassfish asadmin start-database
docker exec glassfish asadmin deploy vlibtour-tour-management-system/vlibtour-tour-management-bean/target/vlibtour-tour-management-bean.jar
sleep 1

# populate the database with the POIs and the tours
if [ -z "$GLASSFISH_HOME" ]; then
    echo "Glassfish is not installed: the client to populate the database with the POIs and the tours cannot be executed"
else
    ./Scripts/tour_management_admin_client.sh populate toursAndPOIs
    # no process to stop at the end of the scenario
    echo -e "\e[1;44m  \e[1;33m ========================================================== \e[0m "
    echo -e "\e[1;44m  \e[1;33m =       Derby database populated with POIs and tours     = \e[0m "
    echo -e "\e[1;44m  \e[1;33m ========================================================== \e[0m "
fi
sleep 1
# start the rabbitmq server
if netstat -nlp 2> /dev/null | grep -q "[[:space:]]5672"; then
    docker stop rabbitmq
    docker rm rabbitmq
fi
sleep 1
docker run -itd --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.10-management
echo -e "\e[1;44m  \e[1;33m ========================================================== \e[0m "
echo -e "\e[1;44m  \e[1;33m =          Start docker container for rabbitmq           = \e[0m "
echo -e "\e[1;44m  \e[1;33m ========================================================== \e[0m "
sleep 10

# start the lobby room server
./Scripts/start_lobby_room_server.sh
# pid to kill at the end in ~/.vlibtour/lobby_room_server
sleep 3

# start the visit emulation server
if [ -z "$GLASSFISH_HOME" ]; then
    echo "Glassfish is not installed: the visit emulation server cannot be executed"
else
    procNumber="$(netstat -nlp | grep 127.0.0.1:8888 | cut -d"N" -f2 | cut -d"/" -f1)"
    if [ -n "$procNumber" ]; then
        echo "There is an old visit emulation server running; remove proc $procNumber"
        kill -9 "$procNumber"
    fi
    ./Scripts/start_visit_emulation_server.sh
    # pid to kill at the end in ~/.vlibtour/visit_emulation_server
    sleep 3
fi

# start the tourist applications
if [ -z "$GLASSFISH_HOME" ]; then
    echo "Glassfish is not installed: the tourist application cannot be executed"
else
    ./Scripts/start_tourist_application_w_emulated_location.sh Joe
    # pid to kill at the end in ~/.vlibtour/tourist_applications
    sleep 1
    ./Scripts/start_tourist_application_w_emulated_location.sh Avrel
    # pid to kill at the end in ~/.vlibtour/tourist_applications
    sleep 1
fi

echo -e "\e[1;44m  \e[1;33m ========================================================== \e[0m "
echo -e "\e[1;44m  \e[1;33m =          Hit return to end the demonstration           = \e[0m "
echo -e "\e[1;44m  \e[1;33m ========================================================== \e[0m "
read x

# kill the tourist applications, just in case
while read pid; do
    kill -9 $pid
done < ~/.vlibtour/tourist_applications
# kill the lobby room server
kill -9 $(cat ~/.vlibtour/lobby_room_server)
# kill the visit emulation server
kill -9 $(cat ~/.vlibtour/visit_emulation_server)
# stop the rabbitmq server
docker stop rabbitmq
docker rm rabbitmq

echo -e "\e[1;44m  \e[1;33m ========================================================== \e[0m "
echo -e "\e[1;44m  \e[1;33m =                 Stop rabbitmq docker container         = \e[0m "
echo -e "\e[1;44m  \e[1;33m ========================================================== \e[0m "

# empty the database, undeploy the bean, and stop the database and the domain
if [ -n "$GLASSFISH_HOME" ]; then
    ./Scripts/tour_management_admin_client.sh empty toursAndPOIs
    docker exec glassfish asadmin undeploy vlibtour-tour-management-bean
    docker exec glassfish asadmin stop-database
    docker exec glassfish asadmin stop-domain domain1
    docker stop glassfish
    docker rm glassfish
echo -e "\e[1;44m  \e[1;33m ========================================================== \e[0m "
echo -e "\e[1;44m  \e[1;33m =        Stop and clean glassfish docker container       = \e[0m "
echo -e "\e[1;44m  \e[1;33m ========================================================== \e[0m "
fi
