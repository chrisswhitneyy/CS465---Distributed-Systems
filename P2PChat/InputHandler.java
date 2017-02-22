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

  //@override
  public void run(){

    System.out.println("Node " + node.user_name +" is running...");

    while (true){

      try{
          // waits to accept a socket
         serverSocket = new ServerSocket(node.port);
         Socket socket = serverSocket.accept();

         // get ip from the socket
         String ip = socket.getRemoteSocketAddress().toString().replace("/","");
         ip = ip.split(":")[0]; // removes the port
         node.addNode(ip); // adds ip to nodes list

         // thread out socket handler
         SingleSocketHandler handler = new SingleSocketHandler(socket);
         handler.start();

         System.out.println("IP: " + ip + " has joined.");
         socket.close();
         serverSocket.close();

       }catch (IOException error){
         System.out.println( "Unable to accept connection. Error: "+ error);
       }

    }
  }
  class SingleSocketHandler extends Thread{
    Socket socket;
    BufferedReader reader;

    public SingleSocketHandler(Socket socket){
      this.socket = socket;
    }
    public void run(){
      // checks to make sure that the buffer reader can be created
      try {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      } catch (IOException e) {
        // if an error accure return
        System.err.println("IOException:" + e);
        return;
      }

      while(true){
        try {
          String message = reader.readLine();
          if(message == null){
            break;
          }
          System.out.println(message);
        } catch (IOException e) {
          System.err.println("IOException:" + e);
        }
      }
    }// end of SingleSocketHandler run()
  } // end of SingleSocketHandler
} // end of InputHandler
