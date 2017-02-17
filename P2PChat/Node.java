import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class Node {
  ServerSocket server_socket;
  List<Socket> sockets;

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

     (new Thread(new TermalHandlerThread(node))).start();

     node.startServer();
   }

  public void writeMessageToSockets(String message, Node node){
    try{

      for (int i = 0; i < node.sockets.size(); i++){

        Socket socket =  node.sockets.get(i);
        DataOutputStream clientWriter = new DataOutputStream(socket.getOutputStream());
        clientWriter.writeBytes(message);

        System.out.println("Message "+ message + " to " + socket);
      }
    }catch (IOException e){}

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
