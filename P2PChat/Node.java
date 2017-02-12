import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class Node {
  ServerSocket _serve_socket;
  List<Socket> _sockets;
  int _node_id;
  int _port = 2594;
  int _num_connected_nodes;

  // Node constructor
  public void Node(int node_id){
    this._node_id = node_id;
    this._num_connected_nodes = 0;
    this._sockets = new ArrayList<Socket>(3);
  }

  public void start(){
    try{
      this._serve_socket = new ServerSocket(this._port);
    }
    catch (IOException error){
      System.out.println( "Server socket was unable to be created. Error: "+ error);
    }

    System.out.println( "Peer node " + this._node_id + " is running...");

    // server loop: infinitely loops and accepting clients
    while (true) {
      try{
        // nesting of instantiation makes it impossible for race conditions
        (new Thread(new SocketHandlerThread(_serve_socket.accept(),_num_connected_nodes))).start();
        _num_connected_nodes++;
      }
      catch (IOException error){
        System.out.println( "Unable to accept connection. Error: "+ error);
      }

    }

  }


}
