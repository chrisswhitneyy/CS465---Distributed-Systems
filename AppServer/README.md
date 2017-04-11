# CS465 Application Server Run Instructions 
The following is the command line instructions to run the satellites (execute the jobs), the sever (load managers and registers satellites), the clients (request the jobs), and the web server (which servers the requested job class). 

## Start Satellites

java appserver.satellite.Satellite  ../../config/Satellite.Earth.properties ../../config/ClassLoader.properties ../../config/Server.properties 

java appserver.satellite.Satellite  ../../config/Satellite.Venus.properties ../../config/ClassLoader.properties ../../config/Server.properties 

java appserver.satellite.Satellite  ../../config/Satellite.Mercury.properties ../../config/ClassLoader.properties ../../config/Server.properties 

## Server:

java appserver.server.Server ../../config/Server.properties

## Start Clients: 

java appserver.client.PlusOneClient \CocoaLigature0 ../../config/Server.properties

java appserver.client.FibClient ../../config/Server.properties 

## WebServer: 

java web.SimpleWebServer ../../config/WebServer.properties
