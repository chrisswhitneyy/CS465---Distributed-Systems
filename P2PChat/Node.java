import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;
import java.util.Scanner;

public class Node {
  ServerSocket server_socket;
  List<Socket> sockets;
  String user_name;
  int _node_id;
  int _port = 2594;

  // Node constructor
  public Node(){
    this._node_id = 1;
    this.sockets = new ArrayList<Socket>();
  }

   public static void main (String[] args){
     System.out.println("======Peer-to-Peer Chat Application======");
     Node node = new Node();
     Scanner scanner = new Scanner( System.in );

     // Prompt the user
     System.out.print( "Enter user name: " );
     // Read a line of text from the user.
     String input = scanner.nextLine();
     node.user_name = input;
     int num;

     while (true){
       // Prompt the user
       System.out.print( "Enter 1.) To start a chat 2.) To join a chat: " );
       // Read a line of text from the user.
      num = scanner.nextInt();
       if (num == 1 || num == 2){
         break;
       }else{
         System.out.println("Input must be a 1 or 2, " + num + " is not valid.");
       }
     }
     if (num == 2){
       while (true){
         // Prompt the user
         System.out.print( "Enter hostname or ip: " );
         // Read a line of text from the user.
         String hostName = scanner.nextLine();

         try {
              // creates a socket instance
              Socket socket = new Socket(hostName , node._port);
              DataOutputStream clientWriter = new DataOutputStream(socket.getOutputStream());
              clientWriter.writeBytes("hello from " + node.user_name);
              node.sockets.add(socket);
              System.out.println("Client socket created. Sockets size: " + node.sockets.size());
          } catch (UnknownHostException e) {
              System.err.println("Couldn't find host  " + hostName);
          } catch (IOException e) {
              System.err.println("Couldn't connection to  " + hostName);
          }
       }

     }
     (new Thread(new TermalHandlerThread(node))).start();
     node.startServer();

   }

  public synchronized void writeMessageToSockets(String from, String message, Node node){
    try{

      for (int i = 0; i < node.sockets.size(); i++){

        Socket socket =  node.sockets.get(i);
        DataOutputStream clientWriter = new DataOutputStream(socket.getOutputStream());
        clientWriter.writeBytes(message);
        System.out.println("\n" + node.user_name + ":" + message);

      }
    }catch (IOException e){}
    return;

  }


  public void startServer(){
    try{
      this.server_socket = new ServerSocket(this._port);
    }catch (IOException error){
      System.out.println( "Unable to create server socket. Error: "+ error);
    }

    System.out.println( "Peer node " + this._node_id + " is running...");

    // server loop: infinitely loops and accepting clients
    while (true) {
      try{

        Socket socket = server_socket.accept();
        this.sockets.add(socket);
        (new Thread(new SocketHandlerThread(socket, this))).start();

      }
      catch (IOException error){
        System.out.println( "Unable to accept connection. Error: "+ error);
      }
    }
  }
}
