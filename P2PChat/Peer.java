import java.net.*;
import java.io.*;

public class Peer {
  private int _peer_id;
  private ServerSocket _next_peer;
  private ServerSocket _prev_peer;
  private int _next_port;
  private int _prev_port;

  public void Peer(){
    try{
      this._next_peer = new ServerSocket(this._next_port);
      this._prev_peer = new ServerSocket(this._prev_port);
    }catch(IOException error){
      System.out.println("Unable to create sockets. Error: " + error);
    }

  }

  public boolean join(String hostname){
    return true;
  }

  public boolean exit(){
    return true;
  }

  public boolean initilize_chat(){
    this._peer_id = 0;

    return true;
  }

  public Socket listen(){
    return new Socket();
  }

  public void disp_message(Message data){

  }

  public boolean broadcast(Message data){
    return true;
  }

}
