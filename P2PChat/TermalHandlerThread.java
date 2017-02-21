import java.util.Scanner;

public class TermalHandlerThread extends Node implements Runnable{
  Node nodeObject;

  public TermalHandlerThread(Node nodeObject){
    this.nodeObject = nodeObject;
  }

  //@override
  public void run(){

    while (true){
      Scanner scanner = new Scanner( System.in );
      // Prompt the user
      System.out.print( "Enter message: " );
      // Read a line of text from the user.
      String input = scanner.nextLine();
      nodeObject.writeMessageToSockets(nodeObject.user_name,input,nodeObject);
    }

  }
}
