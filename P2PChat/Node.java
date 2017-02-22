import java.io.*;
import java.net.*;
import java.util.*;

class Node {
  protected String user_name;
  protected List<String> ips;
  protected int  port = 2594;
  protected boolean isConnected;

  public Node(String user_name){
    this.user_name = user_name;
    this.ips = new ArrayList<String>();
    this.isConnected = false;
  }

  public synchronized void addNode(String ip){
    if (!this.ips.contains(ip)){
       this.ips.add(ip);
    }
  }

} // end of Node class
