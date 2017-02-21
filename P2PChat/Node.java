import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;
import java.util.Scanner;

public class Node {
  ServerSocket server_socket;
  List<String> ips;
  String user_name;
  String ip;
  int port = 2594;

  // Node constructor
  public Node(){
    this.ips = new ArrayList<String>();
    try{
      InetAddress localIp = InetAddress.getLocalHost();
      this.ip = localIp.getHostAddress();
    }catch(UnknownHostException e){
      System.out.println("Unknown Host Exception: " + e);
    }

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
              Socket socket = new Socket(hostName , node.port);
              node.ips.add(hostName);
              socket.close();
              break;
          } catch (UnknownHostException e) {
              System.err.println("Couldn't find host  " + hostName);
          } catch (IOException e) {
              System.err.println("Couldn't connection to  " + hostName);
          }
       }
     }
     // thread out terminal listerner
     (new Thread(new TermalHandlerThread(node))).start();
     // start server
     node.startServer(node);
   }

   public synchronized void writeMessageToSockets(String from, String message, Node node){

     System.out.println("writeMessageToSockets ips size: " + node.ips.size());

     for (int i = 0; i < node.ips.size(); i++){
       String ip =  node.ips.get(i);

       if ( ip == node.ip  ){
         continue;
       }
       try{
         Socket socket = new Socket(ip , node.port);
         DataOutputStream clientWriter = new DataOutputStream(socket.getOutputStream());
         clientWriter.writeBytes(message);
         System.out.println(node.user_name + ":" + message + "\n");
         socket.close();
       }catch (IOException e){
         System.out.println("IOException was thrown." + e);
       }
     }
   }


   public void startServer(Node node){
     try{
       this.server_socket = new ServerSocket(this.port);
     }catch (IOException error){
       System.out.println( "Unable to create server socket. Error: "+ error);
     }

     System.out.println( "Peer node " + this.user_name + " is running...");

     // server loop: infinitely loops and accepting clients
     while (true) {
       try{
         Socket socket = server_socket.accept();
         String ip = socket.getLocalAddress().getHostAddress();
         node.ips.add(ip);
         (new Thread(new SocketHandlerThread(socket, node))).start();
       }catch (IOException error){
         System.out.println( "Unable to accept connection. Error: "+ error);
       }
     }
   }
 }
