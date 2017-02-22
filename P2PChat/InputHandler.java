import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;
import java.util.Scanner;

class InputHandler implements Runnable{
  Node node;
  ServerSocket serverSocket;

  public InputHandler(Node node){
    this.node = node;
  }
  public String messagePrompt(){
    /// Prompt the user for user_name
    Scanner scanner = new Scanner( System.in );
    System.out.print( "Message: " );
    // Read a line of text from the user.
    String input = scanner.nextLine();
    return input;
  }

  //@override
  public void run(){

    while (true){
      System.out.println("Node " + node.user_name +" is running...");
      try{
         serverSocket = new ServerSocket(node.port);
         Socket socket = serverSocket.accept();
         // get ip from the socket
         String ip = socket.getRemoteSocketAddress().toString().replace("/","");
         ip = ip.split(":")[0]; // removes the port

         if (!node.ips.contains(ip)){
            node.ips.add(ip);
         }

         System.out.println("IP: " + ip + " was accepted.");

         String message;
         BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         while ((message = reader.readLine()) != null) {
             //parent.writeMessageToAllIps(parent,this.ip,message);
             System.out.println(message);
             //writeToSingleIp(ip,message);
         }
       }catch (IOException error){
         System.out.println( "Unable to accept connection. Error: "+ error);
       }
       messagePrompt();
    }
  }
}
