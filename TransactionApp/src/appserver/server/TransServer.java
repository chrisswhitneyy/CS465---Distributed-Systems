package appserver.server;

import appserver.data.DataManager;
import appserver.lock.LockManager;
import utils.PropertyHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;


/**
 *  Class [TransServer] : Server that waits for connections and threads them out to the TransactionManger
 *
 *  Author Christopher D. Whitney on May 1st, 2017
 *
 */
public class TransServer {

    // Singleton objects
    public static TransManager transManager;
    public static DataManager dataManager;
    public static LockManager lockManager;

    // server socket
    static ServerSocket serverSocket;
    
    // define server properties
    private String host;
    private int port;
    private Properties properties;

    /**
     * ClassConstructor
     * @param serverPropertiesFile - Path to the server property file
     */
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

        // create trans manager
        transManager = new TransManager();

        // create data manager
        dataManager = new DataManager();

        // create lock manager
        lockManager = new LockManager();

    }

    public void run() {
    // start serving clients in server loop ...
    // server loop: infinitely loops and accepting clients
        while (true) {
            System.out.println("[Server.run] Waiting to accept a client on port " + port + "... ");
            try{
                // accept client socket and run transaction
                Socket socket = serverSocket.accept();
                transManager.runTrans(socket);
            }catch (IOException e) {
                System.err.println("[Server] Error: " + e);
                e.printStackTrace();
            }
        }
    }


    // main()
    public static void main(String[] args) {
        // start the application server
        TransServer transServer;
        if(args.length == 1) {
            transServer = new TransServer(args[0]);
        } else {
            transServer = new TransServer("../../../config/Server.properties");
        }
        transServer.run();
    }
}
