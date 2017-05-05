# CS465 Transactional Application
A simple transactional system which tracks accounts and uses locks to insure concurrency.

 * /appserver : contains the source for the application
     * /client : the client class which contains main and the proxy server which translates the high-level operations in client to low-level
     network calls.
     * /comm : the class used in communication between the server and client
     * /data : contains the data manager and account class
     * /lock : contains the lock type, lock, and lock manager
     * /server : contains
 * /utils : contains source to handle the property file
 * /config : contains the configuration file

# Run Instructions
Compile source to class files. Navigate to the output 'appserver' folder after build and run the following commands to
run both the client and server.

Note: Properties are hardcoded into client and server, however, a specific path can also be passed as well.

## Server:
java appserver.server.TransServer <optional property file path>

## Clients:
java appserver.client.Client <optional property file path>
