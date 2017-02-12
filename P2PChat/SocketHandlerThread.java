
import java.io.*;
import java.net.*;
import java.util.*;

class SocketHandlerThread extends Node implements Runnable{
  // class level variables for the socket of the client, a buffer to read in
  // the clients message and an output stream to send a message to the client
  Socket client;
  int   _num_connected_nodes;
  BufferedReader clientReader;
  DataOutputStream clientWriter;

  public SocketHandlerThread(Socket client,int _num_connected_nodes) {
    super._sockets.add(client);
    this.client = client;
    this._num_connected_nodes = _num_connected_nodes;
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
