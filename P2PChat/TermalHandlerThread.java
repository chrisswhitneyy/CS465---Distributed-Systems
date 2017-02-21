import java.util.Scanner;

public class TermalHandlerThread extends Node implements Runnable{
  Node node;

  public TermalHandlerThread(Node node){
    this.node = node;
  }

  //@override
  public void run(){

    while (true){
      Scanner scanner = new Scanner( System.in );
      // Prompt the user
      System.out.print( "Enter message: " );
      // Read a line of text from the user.
      String message = scanner.nextLine();
      super.writeMessageToSockets(node.user_name,message,node);
    }

  }
}
