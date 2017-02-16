
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

      String message = null;

      while ((message = clientReader.readLine()) != null) {
          System.out.println("Connection "+ _num_connected_nodes + " said: " + message);
          writeToSockets(message);
      }


    }catch (IOException e){

    }
  }
  public synchronized void writeToSockets(String message){
    try{

      for (int i = 0; i<super._sockets.size(); i++){
        this.clientWriter = new DataOutputStream(super._sockets.get(i).getOutputStream());
        clientWriter.writeBytes(message);
      }

    }catch (IOException e){

    }
  }
}
