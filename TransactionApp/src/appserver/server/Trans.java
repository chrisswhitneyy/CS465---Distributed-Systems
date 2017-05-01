package appserver.server;


import appserver.comm.Message;
import appserver.comm.MessageTypes;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * Class [Trans] : An instance of this class is created every time a client creates a transaction. It then handles all
 * of the request from the client.
 *
 * Author: Christopher D. Whitney
 * Date Created: May 1st, 2017
 *
 **/
public class Trans extends  Thread {

    ArrayList <Locks> locks;
    int TID;
    Socket client = null;
    ObjectInputStream readFromNet = null;
    ObjectOutputStream writeToNet = null;
    Message message = null;


    public Trans(Socket client, int TID ){
        this.TID = id;
        this.client = client;
    }

    @Override
    public void run(){
        while(true){
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
            switch (message.getType()) {

                case MessageTypes.OPEN_TRANS:

                case MessageTypes.CLOSE_TRANS:

                case MessageTypes.READ_REQUEST:

                case MessageTypes.WRITE_REQUEST:

                default:
                    System.err.println("[TransServerThread.run] Warning: Message type not implemented");
            }


        }
    }
}
