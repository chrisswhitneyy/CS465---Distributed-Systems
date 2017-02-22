import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;
import java.util.Scanner;

class Node {
  String user_name;
  List<String> ips;
  int  port = 2594;
  ServerSocket serverSocket;

  public Node(String user_name){
    this.user_name = user_name;
    this.ips = new ArrayList<String>();
  }
  public synchronized void writeMessageToAllIps(Node node, String from_ip, String message){

    try{

      Socket socket = new Socket(from_ip, this.port);
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out.println(message);

    }catch (IOException e){
      System.err.println("IOException: " + e);
    }


  }

  public void startServer(Node node){
    try{
      node.serverSocket = new ServerSocket(node.port);
    }catch (IOException e){
      System.err.println("IOExpection:" + e);
    }
    System.out.println(node.user_name + " is running...");
    // server loop
    // accepts new sockets and adds their ips to node.ips list
    while (true){
      try{
         Socket socket = serverSocket.accept();
         String ip = socket.getRemoteSocketAddress().toString().replace("/","");
         ip = ip.split(":")[0];

         if (!node.ips.contains(ip)){
            node.ips.add(ip);
         }
         (new Thread(new SocketHandler(socket,node))).start();
       }catch (IOException error){
         System.out.println( "Unable to accept connection. Error: "+ error);
       }
    }
  }
}
