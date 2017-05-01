package appserver.server;

import appserver.comm.Message;
import appserver.comm.MessageTypes;
import appserver.comm.Params;
import appserver.lock.Lock;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class [TransManager] : Manages all of the transactions and contains the transaction  as an inner class
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
        System.out.println("[TransManager].runTrans() called.");
        int TID = transCounter; // set TID to current count
        transCounter ++; //increment number of transactions
        Trans trans = new Trans(client, TID); // create new transaction
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
            System.out.println("[TransManager][Trans].constructor() called.");
            this.TID = TID;
            this.client = client;
        }

        @Override
        public void run(){
            System.out.println("[TransManager][Trans].run() called.");

            while(true){
                // set up objects streams and message message
                try {
                    // setting up object streams
                    writeToNet = new ObjectOutputStream(client.getOutputStream());
                    readFromNet = new ObjectInputStream(client.getInputStream());

                    // reading message
                    message = (Message) readFromNet.readObject();

                    // params are stored in the message as an array
                    Params params = (Params) message.getContent();
                    // possible parameters in the message
                    int fromAccountID;
                    int toAccountID;
                    int amount;
                    int balance;

                    // processing message
                    switch (message.getType()) {

                        case OPEN_TRANS:
                            writeToNet.writeObject(TID);
                            System.out.println("[TransManager][Trans].run() OPEN_TRANS "+ TID +".");
                            break;

                        case CLOSE_TRANS:
                            transactions.remove(this);
                            transCounter--;
                            System.out.println("[TransManager][Trans].run() CLOSE_TRANS "+ TID +".");
                            break;

                        case READ_REQUEST:
                            fromAccountID = (int) params.arg1;
                            balance = TransServer.dataManager.read(fromAccountID,TID);
                            writeToNet.writeObject(balance);
                            System.out.println("[TransManager][Trans].run() READ_REQUEST -> account " + fromAccountID + ": $" + balance + ".");
                            break;

                        case WRITE_REQUEST:
                            toAccountID = (int) params.arg1;
                            amount = (int) params.arg2;
                            TransServer.dataManager.write(toAccountID,TID,amount);
                            System.out.println("[TransManager][Trans].run() WRITE_REQUEST to account " + toAccountID + ": $" + amount + ".");
                            break;

                        default:
                            System.err.println("[TransManager][Trans].run() Warning: Message type not implemented");
                    }
                }
                catch (Exception e) {
                    System.err.println("[TransManager][Trans].run() Message could not be read from object stream.");
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }


}
