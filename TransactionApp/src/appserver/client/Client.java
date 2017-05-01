package appserver.client;

import java.util.Properties;
import utils.PropertyHandler;

/**
 *
 * Class [Client] : Instances of client are threaded in main. The thread uses the TransServerProxy class to transform
 * this classes high level calls to low level network calls.
 *
 * Author: Christopher D. Whitney on May 1st, 2017
 *
 */
public class Client extends Thread{

    private Properties properties;
    private String host;
    private int port;
    private  int numberOfAccounts;

    /**
     * ClassConstructor
     * @param serverPropertiesFile - String to the path for the property file
     */
    public Client(String serverPropertiesFile) {
        try {
            properties = new PropertyHandler(serverPropertiesFile);
            host = properties.getProperty("HOST");
            System.out.println("[Client] Host: " + host);
            port = Integer.parseInt(properties.getProperty("PORT"));
            System.out.println("[Client] Port: " + port);
            numberOfAccounts = Integer.parseInt(properties.getProperty("NUMBER_OF_ACCOUNT"));

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

            System.out.println("[Client].run() Trans #" + transID + " opened.");

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
            // write back amount after deposit
            int amountToRemain = transServerProxy.write(depositedAccount, accountTo + transferAmount );

            System.out.println("[Client].run() Account " + amountFrom + " deposited $" + transferAmount + " to account " + accountTo );
            System.out.println("[Client].run() " + amountFrom + " = $" + amountFromRemain + "," + accountTo + " =$" + amountToRemain);

            transServerProxy.closeTrans();

        } catch (Exception e) {
            System.err.println("[Client].run() Error: " + e);
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("[Client].main()");
        // threads out clients
        //for (int i=0;i<=10;i++){
            Client client = new Client("../../../config/Server.properties");
            client.start();
        //}
        
    }  
}
