package appserver.server;

import appserver.comm.Message;
import appserver.comm.MessageTypes;
import appserver.data.DataManager;
import appserver.lock.LockManager;
import appserver.server.TransManager;
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
public class TransServer {

    // Singleton objects - there is only one of them. For simplicity, this is not enforced though ...
    static DataManager dataManager = new DataManager();
    static LockManager lockManager = new LockManager();
    static TransManager transManager = new TransMananger();

    static ServerSocket serverSocket = null;
    
    // define server properties
    private String host = null;
    private int port;
    private Properties properties;

    public TransServer(String serverPropertiesFile) {
        
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

        // create data manager

        // intilize account ballances

        // create lock manager

        // create trans manager


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


    // main()
    public static void main(String[] args) {
        // start the application server
        TransServer transServer = null;
        if(args.length == 1) {
            transServer = new TransServer(args[0]);
        } else {
            transServer = new TransServer("../../config/Server.properties");
        }
        transServer.run();
    }
}
