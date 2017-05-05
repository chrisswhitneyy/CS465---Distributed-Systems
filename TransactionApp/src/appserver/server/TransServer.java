package appserver.server;

import appserver.data.Account;
import appserver.data.DataManager;
import appserver.lock.Lock;
import appserver.lock.LockManager;
import utils.PropertyHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;


/**
 *  Class [TransServer] : Server that waits for connections and threads them out to the TransactionManger
 *
 *  Author Christopher D. Whitney on May 1st, 2017
 *
 */
public class TransServer extends Thread{

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
            System.out.println("[TransServer] Host: " + host);
            port = Integer.parseInt(properties.getProperty("PORT"));
            System.out.println("[TransServer] Port: " + port);
            
            // create server socket
            serverSocket = new ServerSocket(port);
        
        } catch (Exception e) {
            System.err.println("[TransServer] Error: " + e);
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
            System.out.println("[TransServer].run() Waiting to accept a client on port " + port + "... ");
            try{
                // accept client socket and run transaction
                transManager.runTrans(serverSocket.accept());
                System.out.println("[TransServer].run() Socket accepted.");

            }catch (IOException e) {
                System.err.println("[TransServer].run() Error: " + e);
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
        transServer.start();

        new Thread() {
            public void run(){
                try{
                    Thread.sleep(10000);
                }  catch (InterruptedException e){
                    System.out.println("[TransServer][SupperThread].run()  Error : " + e);
                }
                Lock tempLock;
                ArrayList<Integer> waitingTIDs;
                HashMap<Account, Lock> locks  = lockManager.getLocks();
                // iterates through all the locks and release the locks that contain TID
                Iterator iterator = locks.entrySet().iterator();
                System.out.println("============ DEAD LOCK ============");
                while (iterator.hasNext()){
                    tempLock = (Lock) ((HashMap.Entry) iterator.next()).getValue(); // get locks from hash map
                    waitingTIDs = tempLock.getWaitingTIDs(); // get TIDs held by the lock
                    for (int i=0; i< waitingTIDs.size(); i++){
                        System.out.print("TID: " + waitingTIDs.get(i));
                    }
                    System.out.print("\n");
                    iterator.remove(); // remove entry from iterator
                }
            }


        }.start();
    }
}
