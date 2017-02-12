import java.io.*;
import java.net.*;
import java.lang.Thread;

public class Node {
  private Socket _serve_socket;
  private Socket [] _sockets;
  private int _node_id;
  private int _port = 2594;
  private int _num_connected_nodes;

  // Node constructor
  public void Node(int node_id){
    this._node_id = node_id;
    this._num_connected_nodes = 0;
  }

  public void start(){

    this._serve_socket = new ServerSocket(this._port);

    System.out.println( "Peer node " + this._node_id + " is running...");

    // server loop: infinitely loops and accepting clients
    while (true) {
      // nesting of instantiation makes it impossible for race conditions
      (new Thread(new EchoServerThread(serverSocket.accept(),_num_connected_nodes))).start();
      _num_connected_nodes++;
    }

  }


}
