package appserver.server;

import appserver.comm.Message;
import appserver.comm.MessageTypes;
import appserver.lock.Lock;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * Author Christopher D. Whitney on May 1st, 2017
 *
 */
public class TransManager {

    private static int transCounter;
    private static ArrayList<Trans> transactions;

    TransManager(){
        transCounter = 0;
        transactions = new ArrayList();
    }

    /**
     * runTrans : Creates and runs a transactions
     *
     * @param client - Socket to the client
     */
    public void runTrans(Socket client){
        int TID = transCounter; // set TID to current count
        Trans trans = new Trans(client, TID); // create new transaction
        transCounter ++; //increment number of transactions
        transactions.add(trans); // add transaction to list of transactions
        trans.start(); // start transaction thread
    }

    /**
     *
     * InnerClass [Trans] : An instance of this class is created to handle all of the request from the client.
     *
     * Author: Christopher D. Whitney on May 1st, 2017
     *
     **/

    private class Trans extends Thread implements MessageTypes{

        private ArrayList <Lock> locks;
        private int TID;
        private Socket client;
        private ObjectInputStream readFromNet;
        private ObjectOutputStream writeToNet;
        private Message message;

        /**
         * ClassConstructor
         * @param client - Client socket
         * @param TID - Transactions ID
         */
        public Trans(Socket client, int TID){
            this.TID = TID;
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

                    // params are stored in the message as an array
                    ArrayList<Integer> params = (ArrayList<Integer>) message.getContent();
//                  // possible parameters in the message
                    int fromAccountID;
                    int toAccountID;
                    int amount;

                    // processing message
                    switch (message.getType()) {

                        case OPEN_TRANS:
                            writeToNet.writeObject(TID);

                        case CLOSE_TRANS:
                            transactions.remove(this);
                            transCounter--;
                            return;

                        case READ_REQUEST:
                            fromAccountID = params.get(0);
                            TransServer.dataManager.read(fromAccountID);

                        case WRITE_REQUEST:
                            toAccountID = params.get(0);
                            amount = params.get(1);
                            TransServer.dataManager.write(toAccountID,amount);

                        default:
                            System.err.println("[Trans.run] Warning: Message type not implemented");
                    }
                }

                catch (Exception e) {
                    System.err.println("[Trans.run] Message could not be read from object stream.");
                    e.printStackTrace();
                    System.exit(1);
                }

            }
        }
    }


}
