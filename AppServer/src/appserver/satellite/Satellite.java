package appserver.satellite;

import appserver.job.Job;
import appserver.comm.ConnectivityInfo;
import appserver.job.UnknownToolException;
import appserver.comm.Message;
import static appserver.comm.MessageTypes.JOB_REQUEST;
import static appserver.comm.MessageTypes.REGISTER_SATELLITE;
import appserver.job.Tool;
import java.io.*;
import java.net.*;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.PropertyHandler;

/**
 * Class [Satellite] Instances of this class represent computing nodes that execute jobs by
 * calling the callback method of tool implementation, loading the tools code dynamically over a network
 * or locally, if a tool got executed before.
 *
 * @author Dr.-Ing. Wolf-Dieter Otte
 */
public class Satellite extends Thread {
    private PropertyHandler satelliteProperties;
    private PropertyHandler classLoaderProperties;
    private PropertyHandler serverProperties;

    private ConnectivityInfo satelliteInfo = new ConnectivityInfo();
    private ConnectivityInfo serverInfo = new ConnectivityInfo();
    private HTTPClassLoader classLoader = null;
    private Hashtable toolsCache = null;

    public Satellite(String satellitePropertiesFile, String classLoaderPropertiesFile, String serverPropertiesFile) {

        // read the configuration information from the file name passed in
        // ---------------------------------------------------------------
        try {
            satelliteProperties = new PropertyHandler(satellitePropertiesFile);
            classLoaderProperties = new PropertyHandler(classLoaderPropertiesFile);
            serverProperties = new PropertyHandler(serverPropertiesFile);

        } catch (Exception e) {
            System.err.println("[Satellite] Error: " + e);
        }
       
        // create a socket info object that will be sent to the server
        satelliteInfo.setHost(satelliteProperties.getProperty("HOST"));
        satelliteInfo.setHost(satelliteProperties.getProperty("PORT"));
        

        // get connectivity information of the server
        serverInfo.setHost(serverProperties.getProperty("HOST"));
        serverInfo.setHost(serverProperties.getProperty("PORT"));


        // creates an instance of classLoader w/ classLoaderPropertiesFile
        // -------------------
        initClassLoader();


        // create tools cache
        // -------------------
        toolsCache = new Hashtable();

    }

    @Override
    public void run() {

        try{
            // register this satellite with the SatelliteManager on the server
            // ---------------------------------------------------------------
            Message message = new Message(3,satelliteInfo);
            Socket socket = new Socket(serverProperties.getProperty("HOST"),
                    Integer.parseInt(serverProperties.getProperty("PORT")));
            ObjectOutputStream writeToNet = new ObjectOutputStream(socket.getOutputStream());
            writeToNet.writeObject(message);
        }catch (IOException e) {
            System.err.println("[Satellite.run] Error: " + e);
        }
        

        // create server socket
        // ---------------------------------------------------------------
        ServerSocket serverSocket;

        // Get port from satelliteProperties
        String portString = satelliteProperties.getProperty("PORT");


        // start taking job requests in a server loop
        // ---------------------------------------------------------------
        try{
            // creates an instance of a server socket
            serverSocket = new ServerSocket(Integer.parseInt(portString));


            // server loop: infinitely loops and accepting clients
            while (true) {
                System.out.println("[Satellite.run] Waiting to accept a request on port " + portString + "... ");
                // nesting of instantiation makes it impossible for race conditions
               (new Thread(new SatelliteThread(serverSocket.accept() ,this))).start();
            }

        } catch (IOException e) {
            System.err.println("[Satellite.run] Error: " + e);
        }

    }

    // inner helper class that is instanciated in above server loop and processes job requests
    private class SatelliteThread extends Thread {

        Satellite satellite = null;
        Socket jobRequest = null;
        ObjectInputStream readFromNet = null;
        ObjectOutputStream writeToNet = null;
        DataInputStream readStream = null;
        PrintStream writeStream = null;
        Message message = null;


        SatelliteThread(Socket jobRequest, Satellite satellite) {
            this.jobRequest = jobRequest;
            this.satellite = satellite;
        }

        @Override
        public void run() {

            try{
                // setting up object streams! DO THIS ONLY ONCE!
                readFromNet = new ObjectInputStream(jobRequest.getInputStream());
                writeToNet = new ObjectOutputStream(jobRequest.getOutputStream());

                // reading message
               message = (Message) readFromNet.readObject();

                // processing message
                switch (message.getType()) {
                    case JOB_REQUEST:
                        // Gets job from contents of message
                        Job job = (Job) message.getContent();
                        // Finds tool object
                        Tool tool = getToolObject( job.getToolName() );
                        // Calculates result
                        Object result = tool.go(job.getParameters());
                        // sending results back
                        writeToNet.writeObject(result);

                        System.out.println("[SatelliteThread.run] Sent result back.");
                        break;

                    default:
                        System.err.println("[SatelliteThread.run] Warning: Message type not implemented");
                }


            } catch (IOException e) {
                System.err.println("[SatelliteThread.run] Error: Couldn't setup object stream. " + e);
            } catch (ClassNotFoundException e){
                System.err.println("[SatelliteThread.run] Error: Couldn't not read object stream. " + e);
            } catch (Exception e){
                System.err.println("[SatelliteThread.run] Error: " + e);
            }


        }
    }

    /**
     * Aux method to get a tool object, given the fully qualified class string
     *
     */
    public Tool getToolObject(String toolClassString) throws UnknownToolException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        Tool toolObject = null;

        // Gets tool if it's in the cache
        if ((toolObject = (Tool) toolsCache.get(toolClassString)) == null) {
            System.out.println("\" Tools Class: " + toolClassString);
            // Throw unknown tool expection
            if (toolClassString == null) {
                throw new UnknownToolException();
            }
            // Load tool from class loader
            Class toolClass = classLoader.loadClass(toolClassString);
            // New instance of the tool class and cast it to a tool
            toolObject = (Tool) toolClass.newInstance();
            // Puts tool into cache
            toolsCache.put(toolClassString, toolObject);
        } else {
            // Message showing tool already in the cachce
            System.out.println("Tool: \"" + toolClassString + "\" already in Cache");
        }

        return toolObject;
    }


    /**
     * Auxiliary method for initializing the class loader
     */
    private void initClassLoader() {

        String host = classLoaderProperties.getProperty("HOST");
        String portString = classLoaderProperties.getProperty("PORT");

        if ((host != null) && (portString != null)) {
            try {
                classLoader = new HTTPClassLoader(host, Integer.parseInt(portString));
            } catch (NumberFormatException e) {
                System.err.println("Wrong Portnumber, using Defaults");
            }
        } else {
            System.err.println("configuration data incomplete, using Defaults");
        }

        if (classLoader == null) {
            System.err.println("Could not create HTTPClassLoader, exiting ...");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        // start a satellite
        Satellite satellite = new Satellite(args[0], args[1], args[2]);
        satellite.run();

        //(new Satellite("Satellite.Earth.properties", "WebServer.properties")).start();
        //(new Satellite("Satellite.Venus.properties", "WebServer.properties")).start();
        //(new Satellite("Satellite.Mercury.properties", "WebServer.properties")).start();
    }

}
