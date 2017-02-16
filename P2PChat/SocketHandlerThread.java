
import java.io.*;
import java.net.*;
import java.util.*;

public class SocketHandlerThread extends Node implements Runnable{
  // class level variables for the socket of the client, a buffer to read in
  // the clients message and an output stream to send a message to the client
  Socket client;
  BufferedReader clientReader;
  DataOutputStream clientWriter;

  public SocketHandlerThread(Socket client) {
    super._sockets.add(client);
    this.client = client;
  }

  //@override
  public void run(){

    try{

      this.clientReader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
      this.clientWriter = new DataOutputStream(this.client.getOutputStream());

      String message = null;

      while ((message = clientReader.readLine()) != null) {

          System.out.println("Connection "+ _num_connected_nodes + " said: " + message);

          clientWriter.writeBytes(message);

      }


    }catch (IOException e){

    }
  }
}
