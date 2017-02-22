import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;
import java.util.Scanner;

class PeerToPeerChat{

  public static void main(String [] args){
    System.out.println("======Peer-to-Peer Chat Application======");

    // Prompt the user for user_name
    Scanner scanner = new Scanner( System.in );
    System.out.print( "Enter user name: " );
    // Read a line of text from the user.
    String input = scanner.nextLine();
    // create instance of node
    Node node = new Node(input);

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
      Socket socket;
      while (true){
        // Prompt the user
        System.out.print( "Enter hostname or ip: " );
        // Read a line of text from the user.
        String hostName = scanner.nextLine();

        try {
            // creates a socket instance
            socket = new Socket(hostName , node.port);
            node.ips.add(hostName);
            break;
          } catch (UnknownHostException e) {
            System.err.println("Couldn't find host  " + hostName);
          } catch (IOException e) {
            System.err.println("Couldn't connection to  " + hostName);
          }
        }
      }

      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out.println(node.ip + " says hello.");

      // thread out terminal listerner
      //(new Thread(new TermalHandlerThread(node))).start();
      // start server
      node.startServer(node);
   //}
  }
}
