import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;
import java.util.Scanner;

class InputHandler implements Runnable{
  Node node;
  ServerSocket serverSocket;

  public InputHandler(Node node){
    this.node = node;
  }

  //@override
  public void run(){

    while (true){
      System.out.println("Node " + node.user_name +" is running...");
      try{
         serverSocket = new ServerSocket(node.port);
         Socket socket = serverSocket.accept();
         // get ip from the socket
         String ip = socket.getRemoteSocketAddress().toString().replace("/","");
         ip = ip.split(":")[0]; // removes the port

         node.addNode(ip);

         System.out.println("IP: " + ip + " was accepted.");

         String message;
         BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         while ((message = reader.readLine()) != null) {
             System.out.println(message);
         }
       }catch (IOException error){
         System.out.println( "Unable to accept connection. Error: "+ error);
       }
    }
  }
}
