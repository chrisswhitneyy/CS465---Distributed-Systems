package appserver.client;

import appserver.comm.Message;
import appserver.comm.MessageTypes;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by cdubs on 4/27/17.
 */
public class TransServerProxy {

    private String host;
    private int port;

    // interface to the server for the client
    public TransServerProxy(String host, int port){
        this.host = host;
        this.port = port;
    }

    public int openTrans() throws IOException, ClassNotFoundException{
        int id = 0;

        // create socket
        Socket socket = new Socket(this.host,this.port);

        // setting up object streams
        ObjectInputStream readFromNet = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream writeToNet = new ObjectOutputStream(socket.getOutputStream());

        // create message with message type
        Message message = new Message();
        message.setType(MessageTypes.OPEN_TRANS);
        // write object to stream
        writeToNet.writeObject(message); // send message
        id = readFromNet.read(); // read balance back

        return id; // return balance
    }
    public void closeTrans() throws IOException, ClassNotFoundException{

        // create socket
        Socket socket = new Socket(this.host,this.port);

        // setting up object streams
        ObjectOutputStream writeToNet = new ObjectOutputStream(socket.getOutputStream());

        // create message with message type
        Message message = new Message();
        message.setType(MessageTypes.CLOSE_TRANS);

        // write object to stream
        writeToNet.writeObject(message); // send message

    }
    public int read(int accountID) throws IOException, ClassNotFoundException{
        // intilize balance
        int balance = 0;

        // create socket
        Socket socket = new Socket(this.host,this.port);

        // setting up object streams
        ObjectInputStream readFromNet = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream writeToNet = new ObjectOutputStream(socket.getOutputStream());

        // create message with the accountID and message type
        Message message = new Message();
        message.setType(MessageTypes.READ_REQUEST);
        message.setContent(accountID);

        // write object to stream
        writeToNet.writeObject(message); // send message
        balance = readFromNet.read(); // read balance back

        return balance; // return balance

    }

    public int write (int accountID, int amount) throws IOException, ClassNotFoundException{
        // intilize varaibles
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
