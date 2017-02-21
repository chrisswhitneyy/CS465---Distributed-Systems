import java.util.Scanner;

public class TermalHandlerThread extends Node implements Runnable{
  Node node;

  public TermalHandlerThread(Node node){
    this.node = node;
  }

  //@override
  public void run(){
    String message;

    Scanner scanner = new Scanner( System.in );
    // Read a line of text from the user.
    while (true){
      message = scanner.nextLine();
      // Prompt the user
      System.out.print( "Enter message: " );
      if (scanner.hasNext()){
        super.writeMessageToSockets(node.user_name,message,node);
      }
    }

  }
}
