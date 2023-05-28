This is the case study "VlibTour".

To compile and install, execute the following command:
 mvn clean install

To create the glassfish docker image, start the container, test it, and stop and remove it:
 docker build -t glassfish6-tsp-csc - < dockerfile
 docker run -itd --name glassfish -p 1527:1527 -p 3700:3700 -p 4848:4848 -p 8080:8080 -p 8181:8181 glassfish6-tsp-csc
 docker ps
CONTAINER ID   IMAGE ...
225d1149c706   glassfish6-tsp-csc ...

 docker exec glassfish asadmin --help
 docker stop glassfish
 docker rm glassfish


To run the scenario of the demonstrator:
 ./run_scenario_w_mapviewer.sh

Here follows some explanations on the content.
The shell script of the scenario is 'run_scenario_w_mapviewer.sh'. It uses the
shell scripts of the directory 'Scripts'.
The Maven modules are the following ones,
mainly in the order of their compilation:
- root: the VLibTour project;
- vlibtour-libraries: the libraries for VLibTour:
  - Geocalc: external libraries without any Maven repository, thus explaining
    why the source code is included here;
  - vlibtour-common: the VLibTour library for common classes;
- vlibtour-tour-management-system: The VLibTour Tour Management
  (EJB technology):
  - vlibtour-tour-management-api: the VLibTour Tour Management API,
  - vlibtour-tour-management-entity: the VLibTour Tour Management entity;
  - vlibtour-tour-management-bean: the VLibTour Tour Management bean server;
  - vlibtour-tour-management-admin-client: the VLibTour Tour Management client
    (to populate the data base for the EJB entities);
- vlibtour-lobby-room-system: the VLibTour Lobby Room System (AMQP technology):
  - vlibtour-lobby-room-api: the API of the VLibTour lobby room;
  - vlibtour-lobby-room-server: the VLibTour lobby room server;
  - vlibtour-lobby-room-proxy: the VLibTour proxy of the lobby room system;
- vlibtour-visit-emulation-system: the VLibTour Visit Emulation System
  (REST technology)
  - vlibtour-visit-emulation-server: the REST server of the VLibTour emulation
    of a visit in a graph positions (Bikestation services and
    POIs [points of interest])
  - vlibtour-visit-emulation-proxy: the VLibTour proxy of visit emulation
    system;
- vlibtour-group-communication-system: the VLibTour Group Communication
  System (AMQP technology)
  - vlibtour-client-group-communication-proxy: the VLibTour proxy
    of the group communication system;
- vlibtour-scenario: the VLibTour Scenario module, which includes the client
  application VLibTourVisitTouristApplication and the classes to manage an
  OpenStreetMap map viewer.

The directory structure of these Maven modules is as follows:
$ tree
.
├── LICENSE
├── pom.xml
├── README.txt
├── run_scenario_w_mapviewer.sh
├── Scripts
├── vlibtour-bikestation
├── vlibtour-group-communication-system
|   ├── pom.xml
|   └── vlibtour-group-communication-proxy
├── vlibtour-libraries
|   ├── pom.xml
|   ├── geocalc
|   └── vlibtour-common
├── vlibtour-lobby-room-system
|   ├── pom.xml
|   ├── vlibtour-lobby-room-api
|   ├── vlibtour-lobby-room-proxy
|   └── vlibtour-lobby-room-server
├── vlibtour-scenario
├── vlibtour-tour-management-system
|   ├── pom.xml
|   ├── vlibtour-tour-management-admin-client
|   ├── vlibtour-tour-management-api
|   ├── vlibtour-tour-management-bean
|   └── vlibtour-tour-management-entity
└── vlibtour-visit-emulation-system
    ├── pom.xml
    ├── vlibtour-visit-emulation-proxy
    └── vlibtour-visit-emulation-server

The dependencies of the Maven modules is as follows (we detail only the
dependencies upon modules of functional components, hence ignoring dependencies
upon modules of the technologies used [EJB, AMQP, REST, etc.]):
$ mvn -B dependency:tree
* vlibtour-tour-management-api
  depends upon
  vlibtour-tour-management-entity
* vlibtour-tour-management-bean
  depends upon
  vlibtour-tour-management-api
* vlibtour-tour-management-admin-client
  depends upon
  vlibtour-tour-management-entity
  vlibtour-tour-management-api
* vlibtour-lobby-room-server
  depends upon
  vlibtour-lobby-room-api
* vlibtour-lobby-room-proxy
  depends upon
  vlibtour-lobby-room-api
  vlibtour-lobby-room-server
* vlibtour-group-communication-proxy
  depends upon
  vlibtour-lobby-room-api
* vlibtour-visit-emulation-server
  depends upon 
  vlibtour-common
* vlibtour-visit-emulation-proxy
  depends upon
  vlibtour-common
* vlibtour-scenario
  depends upon
  vlibtour-common
  vlibtour-tour-management-entity
  vlibtour-tour-management-api
  vlibtour-lobby-room-server
  vlibtour-lobby-room-proxy
  vlibtour-group-communication-proxy
  vlibtour-visit-emulation-proxy
