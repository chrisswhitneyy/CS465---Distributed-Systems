# CS465 Application Server Run Instructions 
The following is the command line instructions to run the satellites (executes the jobs), the sever (load managers and registers satellites), the clients (request the jobs), and the web server (servers the job classes). 

Note: Inorder to insure that the job implementation classes don't load locally move the comiplied class files to the docRoot with the same subfolders as when built.

## Start Satellites

java appserver.satellite.Satellite  ../../config/Satellite.Earth.properties ../../config/ClassLoader.properties ../../config/Server.properties 

java appserver.satellite.Satellite  ../../config/Satellite.Venus.properties ../../config/ClassLoader.properties ../../config/Server.properties 

java appserver.satellite.Satellite  ../../config/Satellite.Mercury.properties ../../config/ClassLoader.properties ../../config/Server.properties 

## Server:

java appserver.server.Server ../../config/Server.properties

## Start Clients: 

java appserver.client.PlusOneClient ../../config/Server.properties

java appserver.client.FibClient ../../config/Server.properties 

## WebServer: 

java web.SimpleWebServer ../../config/WebServer.properties
