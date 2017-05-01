package appserver.client;

import appserver.comm.Message;
import appserver.comm.MessageTypes;
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
 * @Author Christopher D. Whitney on May 1st, 2017
 *
 */
public class TransServerProxy implements MessageTypes{

    private String host;
    private int port;

    /** Class constructor
     *
     * @param host - a string that contains the name for the server
     * @param port - a integer that contains the port for the server connection
     */
    public TransServerProxy(String host, int port){
        this.host = host;
        this.port = port;
    }

    /** openTrans : Opens transaction on the server
     *
     * @return TID - the id for the transaction that is opened
     * @throws IOException - thrown if object streams can't be opened
     */
    public int openTrans() throws IOException{
        int id = 0;

        // create socket
        Socket socket = new Socket(this.host,this.port);

        // setting up object streams
        ObjectInputStream readFromNet = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream writeToNet = new ObjectOutputStream(socket.getOutputStream());

        // create message with message type
        Message message = new Message();
        message.setType(OPEN_TRANS);
        // write object to stream
        writeToNet.writeObject(message); // send message
        id = readFromNet.read(); // read balance back

        return id; // return balance
    }

    /** closeTrans : Closes transaction on the server
     *
     * @throws IOException - thrown if object streams can't be opened
     */
    public void closeTrans() throws IOException, ClassNotFoundException{

        // create socket
        Socket socket = new Socket(this.host,this.port);

        // setting up object streams
        ObjectOutputStream writeToNet = new ObjectOutputStream(socket.getOutputStream());

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

        // create socket
        Socket socket = new Socket(this.host,this.port);

        // setting up object streams
        ObjectInputStream readFromNet = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream writeToNet = new ObjectOutputStream(socket.getOutputStream());

        // create message with the accountID and message type
        Message message = new Message();
        message.setType(READ_REQUEST);
        message.setContent(accountID);

        // write object to stream
        writeToNet.writeObject(message); // send message
        balance = readFromNet.read(); // read balance back

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
        // initialize variables
        int balance = 0;
        ArrayList<Integer> contnet = null;

        // create object socket
        Socket socket = new Socket(this.host,this.port);

        // setting up object streams
        ObjectInputStream readFromNet = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream writeToNet = new ObjectOutputStream(socket.getOutputStream());

        // create content array with id and amount
        contnet = new ArrayList<>(2);
        contnet.add(accountID); // add id
        contnet.add(amount); // add amount

        // create message with the content and message type
        Message message = new Message();
        message.setType(MessageTypes.WRITE_REQUEST);
        message.setContent(contnet); // add content to message

        // write object to stream
        writeToNet.writeObject(message); // send message
        balance = readFromNet.read(); // read balance back

        return balance; // return balance

    }

}
