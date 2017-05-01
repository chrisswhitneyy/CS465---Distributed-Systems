package appserver.client;

import appserver.comm.Message;
import appserver.comm.MessageTypes;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

import utils.PropertyHandler;

/**
 *
 *
 * @author Christopher D. Whitney 
 */
public class Client extends Thread implements MessageTypes{
    
    private String host;
    private int port;

    private Properties properties;
    private Integer number;

    public Client(String serverPropertiesFile) {
        try {
            properties = new PropertyHandler(serverPropertiesFile);
            host = properties.getProperty("HOST");
            System.out.println("[Client] Host: " + host);
            port = Integer.parseInt(properties.getProperty("PORT"));
            System.out.println("[Client] Port: " + port);
        } catch (Exception e) {
            System.err.println("[Client] Error: " + e);
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try { 
            
            TransServerProxy transServerProxy = new TransServerProxy(host,port);

            int transID = transServerProxy.openTrans();
            System.out.println(" trans #" + transID + " opened.");

            // randomly selected account number
            int withdrawnAccount = (int) Math.floor( Math.random() * numberOfAccounts);
            int depositedAccount = (int) Math.floor( Math.random() * numberOfAccounts);

            // randomly select amount to transfer
            int transferAmount = (int) Math.floor( Math.random());

            // reads from account which is be withdrawn from
            int amountFrom = transServerProxy.read(withdrawnAccount);
            // write back account amount after withdraw
            int amountFromRemain = transServerProxy.write(withdrawnAccount, amountFrom - transferAmount);

            // reads from account which is be deposited in
            int accountTo = transServerProxy.read(depositedAccount);
            // write back amount after deposite
            int amountToRemain = transServerProxy.write(depositedAccount, accountTo + transferAmount );



        } catch (Exception e) {
            System.err.println("[Client.run] Error: " + e);
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        
        /* Threads out the FibClient on numbers = 46,45.....0 to calculate 
            the fibonacci number */ 
        for (int i=46;i>0;i--){
          (new appserver.client.Client("../../config/Server.properties",i)).start();
        }
        
    }  
}
