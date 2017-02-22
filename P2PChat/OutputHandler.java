import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;
import java.util.Scanner;

class OutputHandler implements Runnable{
  Node node;

  public OutputHandler(Node node){
    this.node = node;
  }

  public String messagePrompt(){
    // Prompt the user for user_name
    Scanner scanner = new Scanner( System.in );
    System.out.print( "Message: " );
    // Read a line of text from the user.
    String input = scanner.nextLine();
    return input;
  }

  public void writeMessageToAllIps(String message){
    // loops through each ip in the nodes ip list
    for (String ip : this.node.ips){
      try {
        // attempts to create a socket with the ip and node port
        Socket socket = new Socket(ip, this.node.port);
        // create a print writer instance using socket output stream
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        // write message to the writer
        out.println(message);
        // close socket
        socket.close();

      } catch (IOException e) {
        System.out.println("IOException:" + e);
      }
    }
  }
  // @override
  public void run(){
    String message;
    while (true){
      message = messagePrompt();
      writeMessageToAllIps(message);
    }
  }
} // end of output handler
