import java.util.Scanner;

public class Run{

  public static void main(String[] args){

    String answer;
    String new_chat = "1";
    String join_chat = "2";

    while (true){
      Scanner user_input = new Scanner(System.in);
      System.out.print("Enter 1.)To create a chat 2.)Join existing chat:");

      answer = user_input.next();

      if (new_chat.compareTo(answer) == 0 || join_chat.compareTo(answer) == 0 ){
        break;
      }
      System.out.println("Invalid Input");
    } // End of while

    if (new_chat.compareTo(answer)==0){
      Peer peer = new Peer();
      peer.initilize_chat();

    }else if (join_chat.compareTo(answer)==0){


    }

  }
}
