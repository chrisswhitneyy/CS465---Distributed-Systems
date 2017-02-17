
import java.io.*;
import java.net.*;
import java.util.*;

public class SocketHandlerThread extends Node implements Runnable{
  // class level variables for the socket of the client, a buffer to read in
  // the clients message and an output stream to send a message to the client
  Socket client;
  BufferedReader clientReader;
  Node nodeObject;

  public SocketHandlerThread(Socket client, Node nodeObject) {
    this.client = client;
    this.nodeObject = nodeObject;
    System.out.println("Connection accpeted " + client);
  }

  //@override
  public void run(){

    try{
      this.clientReader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
      String message = null;
      while ((message = clientReader.readLine()) != null) {
          super.writeMessageToSockets(message,nodeObject);
      }

    }catch (IOException e){ }

  }

}
