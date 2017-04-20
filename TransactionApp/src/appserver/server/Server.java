package appserver.server;

import appserver.comm.Message;
import static appserver.comm.MessageTypes.OPEN_TRANS;
import static appserver.comm.MessageTypes.CLOSE_TRANS;
import static appserver.comm.MessageTypes.READ;
import static appserver.comm.MessageTypes.WRITE;
import server.DataManager;
import server.LockManager;
import server.TransManager;
import utils.PropertyHandler;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;



/**
 *
 * @author Dr.-Ing. Wolf-Dieter Otte
 */
public class Server {

    // Singleton objects - there is only one of them. For simplicity, this is not enforced though ...
    static DataManager dataManager = new DataManager();
    static LockManager lockManager = new LockManager();
    static TransManager transManager = new TransMananger();

    static ServerSocket serverSocket = null;
    
    // define server properties
    String host = null;
    int port;
    Properties properties;

    public Server(String serverPropertiesFile) {
        
        // read properties and create server socket
        try {
            // read server port from server properties file
            properties = new PropertyHandler(serverPropertiesFile);
            host = properties.getProperty("HOST");
            System.out.println("[Server] Host: " + host);
            port = Integer.parseInt(properties.getProperty("PORT"));
            System.out.println("[Server] Port: " + port);
            
            // create server socket
            serverSocket = new ServerSocket(port);
        
        } catch (Exception e) {
            System.err.println("[Server] Error: " + e);
            e.printStackTrace();
        }
    }

    public void run() {
    // start serving clients in server loop ...
    // server loop: infinitely loops and accepting clients
        while (true) {
            System.out.println("[Server.run] Waiting to accept a request on port " + port + "... ");
            try{
                // nesting of instantiation makes it impossible for race conditions
                (new Thread(new ServerThread(serverSocket.accept()))).start();
            }catch (IOException e) {
                System.err.println("[Server] Error: " + e);
                e.printStackTrace();
            }
        }
    }

    // objects of this helper class communicate with clients
    private class ServerThread extends Thread {

        Socket client = null;
        ObjectInputStream readFromNet = null;
        ObjectOutputStream writeToNet = null;
        Message message = null;

        private ServerThread(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            // set up objects streams and message message
            try {
                // setting up object streams
                readFromNet = new ObjectInputStream(client.getInputStream());
                writeToNet = new ObjectOutputStream(client.getOutputStream());
                
                // reading message
                message = (Message) readFromNet.readObject();
            } catch (Exception e) {
                System.err.println("[ServerThread.run] Message could not be read from object stream.");
                e.printStackTrace();
                System.exit(1);
            }

            // processing message
            ConnectivityInfo satelliteInfo = null;
            switch (message.getType()) {
                case REGISTER_SATELLITE:
                    // read satellite info
                    satelliteInfo = (ConnectivityInfo) message.getContent();
                    
                    System.out.println("[ServerThread] Name: " + satelliteInfo.getName() );
                    
                    // register satellite
                    synchronized (Server.satelliteManager) {
                        satelliteManager.registerSatellite(satelliteInfo);
                    }

                    // add satellite to loadManager
                    synchronized (Server.loadManager) {
                        loadManager.satelliteAdded(satelliteInfo.getName());
                    }

                    break;

                case JOB_REQUEST:
                    System.err.println("\n[ServerThread.run] Received job request");

                    String satelliteName = null;
                    synchronized (Server.loadManager) {
                        try{
                            // get next satellite from load manager
                            satelliteName = loadManager.nextSatellite();
                        
                            // get connectivity info for next satellite from satellite manager
                            satelliteInfo = satelliteManager.getSatelliteForName(satelliteName);
                            System.out.println("[ServerThread.run] Satellite " + satelliteInfo.getName() + " selected.");
                        }catch (Exception e) {
                            System.err.println("[ServerThread.run] Error: " + e);
                            e.printStackTrace();
                            System.exit(1);
                        }
                        
                    }

                    Socket satellite = null;
                    ObjectInputStream sattilleteReadFromNet;
                    ObjectOutputStream sattilleteWriteFromNet;
                    
                    try{
                        // connect to satellite
                        satellite = new Socket(satelliteInfo.getHost(),satelliteInfo.getPort());
                        System.out.println("[ServerThread.run] Connected to satellite.");

                        // open object streams,
                        // setting up object streams
                        sattilleteWriteFromNet = new ObjectOutputStream(satellite.getOutputStream());
                        sattilleteReadFromNet = new ObjectInputStream(satellite.getInputStream());
                        
                        System.out.println("[ServerThread.run] Streams to satellite created.");
                        
                        // forward message (as is) to satellite,
                        sattilleteWriteFromNet.writeObject(message);
                        System.out.println("[ServerThread.run] Job sent to satellite " + satelliteInfo.getName() );
                        // receive result from satellite
                        Object result  = sattilleteReadFromNet.readObject();
                        System.out.println("[ServerThread.run] Job result recieved from satellite " + satelliteInfo.getName() );
                         
                        // write result back to client
                        writeToNet.writeObject(result);
                        System.out.println("[ServerThread.run] Job forwarded to client ");
                         
                        
                    }catch (Exception e) {
                            System.err.println("[ServerThread.run] Error: " + e);
                            e.printStackTrace();
                            System.exit(1);
                     }
                    
                    break;

                default:
                    System.err.println("[ServerThread.run] Warning: Message type not implemented");
            }
        }
    }

    // main()
    public static void main(String[] args) {
        // start the application server
        Server server = null;
        if(args.length == 1) {
            server = new Server(args[0]);
        } else {
            server = new Server("../../config/Server.properties");
        }
        server.run();
    }
}
