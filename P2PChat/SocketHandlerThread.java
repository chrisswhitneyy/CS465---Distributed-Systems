import java.io.*;
import java.net.*;
import java.util.*;

public class SocketHandlerThread extends Node implements Runnable{
  // class level variables for the socket of the client, a buffer to read in
  // the clients message and an output stream to send a message to the client
  Socket client;
  BufferedReader clientReader;
  Node nodeObject;
  String ip;

  public SocketHandlerThread(Socket client, Node nodeObject) {
    this.client = client;
    this.nodeObject = nodeObject;
    ip = client.getLocalAddress().getHostAddress();
    System.out.println("\nConnection accpeted " + ip);
  }

  //@override
  public void run(){

    try{
      this.clientReader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
      String message = null;
      String ip = this.client.getLocalAddress().getHostAddress();
      while ((message = clientReader.readLine()) != null) {
          super.writeMessageToSockets(ip,message,nodeObject);
      }
      System.out.println("Message : " + message);

    }catch (IOException e){ }

  }

}
