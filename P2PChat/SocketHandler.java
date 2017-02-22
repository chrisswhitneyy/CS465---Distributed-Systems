import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;
import java.util.Scanner;

class SocketHandler implements Runnable {
  Node parent;
  Socket socket;
  String ip;
  BufferedReader reader;

  public SocketHandler(Socket socket, Node node){
    this.parent = node;
    this.socket = socket;
    this.ip = this.socket.getRemoteSocketAddress().toString().replace("/","");
    this.ip = this.ip.split(":")[0];

    System.out.println(this.ip + " has joined.");

  }

  //@override
  public void run (){

    String message = null;
    try{
      this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
      while ((message = reader.readLine()) != null) {
          parent.writeMessageToAllIps(parent,this.ip,message);
          System.out.println(message);
      }
    }catch (IOException e){
      System.err.println("IOExpection:" + e);
    }
  }


}
