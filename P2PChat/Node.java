import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;
import java.util.Scanner;

class Node {
  protected String user_name;
  protected List<String> ips;
  protected int  port = 2594;

  public Node(String user_name){
    this.user_name = user_name;
    this.ips = new ArrayList<String>();
  }

  public void writeToSingleIp(String ip, String message){
    try {
      // creates a socket instance
      Socket socket = new Socket(ip, this.port);
      // creates a PrintWriter instance
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      // creates a BufferedReader instance
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out.println(message);
    } catch (UnknownHostException e) {
      System.err.println("Couldn't find host: " + ip);
    } catch (IOException e) {
      System.err.println("Couldn't connection to: " + ip);
    }
  }
  public void writeToAllIps (Node node, String message){
    
  }
} // end of Node class
