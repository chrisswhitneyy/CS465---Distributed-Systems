import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class Node {
  ServerSocket _server_socket;
  List<Socket> _sockets;

  int max_num_con = 4;
  int _node_id;
  int _port = 2594;
  int _num_connected_nodes;

  // Node constructor
  public Node(){
    //this._node_id = id;
    this._num_connected_nodes = 0;
    this._sockets = new ArrayList<Socket>(this.max_num_con);
  }
   public static void main (String[] args){

     System.out.println("Peer-to-Peer Chat Application");
     
     Node node = new Node();
     node.startServer();
   }

  public void startServer(){
    try{
      this._server_socket = new ServerSocket(this._port);
    }
    catch (IOException error){
      System.out.println( "Unable to create server socket. Error: "+ error);
    }

    System.out.println( "Peer node " + this._node_id + " is running...");

    // server loop: infinitely loops and accepting clients
    while (true) {
      try{
        // nesting of instantiation makes it impossible for race conditions
        (new Thread(new SocketHandlerThread( _server_socket.accept() ))).start();
        _num_connected_nodes++;
      }
      catch (IOException error){
        System.out.println( "Unable to accept connection. Error: "+ error);
      }

    }

  }


}
