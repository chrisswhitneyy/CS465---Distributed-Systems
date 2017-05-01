package appserver.client;

import appserver.comm.Message;
import appserver.comm.MessageTypes;
import appserver.comm.Params;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * Class [TransServerProxy] : An instance of this class is created within the client to communicate with the transaction
 * server. This class simply takes the high-level calls suchh as openTrans(), closeTrans(), read/write and converts them
 * down to lower level network calls. It uses object streams to communicate to the server, these object streams pass a
 * message object to server where the parameters are stored as the content of that message.
 *
 * Author: Christopher D. Whitney on May 1st, 2017
 *
 */
public class TransServerProxy implements MessageTypes{

    private String host;
    private int port;
    private Socket socket;

    /** Class constructor
     *
     * @param host - a string that contains the name for the server
     * @param port - a integer that contains the port for the server connection
     */
    public TransServerProxy(String host, int port) throws IOException{
        this.host = host;
        this.port = port;
        this.socket = new Socket(this.host,this.port);
    }

    /** openTrans : Opens transaction on the server
     *
     * @return TID - the id for the transaction that is opened
     * @throws IOException - thrown if object streams can't be opened
     */
    public int openTrans() throws IOException,ClassNotFoundException{
        int id;

        // setting up object streams
        ObjectOutputStream writeToNet = new ObjectOutputStream(this.socket.getOutputStream());
        ObjectInputStream readFromNet = new ObjectInputStream(this.socket.getInputStream());

        // create message with message type
        Message message = new Message();
        message.setType(OPEN_TRANS);
        // write object to stream
        writeToNet.writeObject(message); // send message
        id = (int) readFromNet.readObject(); // read TID back

        System.out.println("[TransServerProxy].openTrans() trans # " + id + ".");

        return id; // return balance
    }

    /** closeTrans : Closes transaction on the server
     *
     * @throws IOException - thrown if object streams can't be opened
     */
    public void closeTrans() throws IOException, ClassNotFoundException{

        // setting up object streams
        ObjectOutputStream writeToNet = new ObjectOutputStream(this.socket.getOutputStream());

        // create message with message type
        Message message = new Message();
        message.setType(CLOSE_TRANS);

        // write object to stream
        writeToNet.writeObject(message); // send message

    }

    /** read: Reads the balance on the account
     *
     * @param accountID - the ID of the account to read from
     * @return balance - the balance of the account
     * @throws IOException - thrown if object streams can't be opened
     */
    public int read(int accountID) throws IOException, ClassNotFoundException{



        // initialize balance
        int balance = 0;

        // setting up object streams
        ObjectInputStream readFromNet = new ObjectInputStream(this.socket.getInputStream());
        ObjectOutputStream writeToNet = new ObjectOutputStream(this.socket.getOutputStream());

        // create message with the accountID and message type
        Message message = new Message();
        message.setType(READ_REQUEST);
        Params param = new Params();
        param.arg1 = accountID;
        message.setContent(param);

        // write object to stream
        writeToNet.writeObject(message); // send message
        balance = (int) readFromNet.readObject(); // read balance back

        System.out.println("[TransServerProxy].read() Account" + accountID + ": $" + balance + ".");

        return balance; // return balance

    }

    /** write : Writes the given amount to the given account
     *
     * @param accountID - ID of the account to write to
     * @param amount - the amount written to the account
     * @return balance - the balance of the account after writing
     * @throws IOException - thrown if object streams can't be opened
     */
    public int write (int accountID, int amount) throws IOException, ClassNotFoundException{

        System.out.println("[TransServerProxy].write() called.");

        // initialize variables
        int balance;

        // setting up object streams
        ObjectInputStream readFromNet = new ObjectInputStream(this.socket.getInputStream());
        ObjectOutputStream writeToNet = new ObjectOutputStream(this.socket.getOutputStream());

        // create params
        Params param = new Params();
        param.arg1 = accountID;
        param.arg2 = amount;

        // create message with the content and message type
        Message message = new Message();
        message.setType(WRITE_REQUEST);
        message.setContent(param); // add content to message

        // write object to stream
        writeToNet.writeObject(message); // send message
        balance = (int) readFromNet.readObject(); // read balance back

        return balance; // return balance

    }

}
