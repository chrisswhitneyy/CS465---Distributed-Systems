package appserver.client;

import appserver.comm.Message;
import appserver.comm.MessageTypes;
import appserver.job.Job;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

import utils.PropertyHandler;

/**
 * Class [FibClient] A FibClient client that uses the Fib tool and the passed
 * server connectivity information to request a Fib job on number = 46,45,...,0
 * 
 * @author Christopher D. Whitney 
 */
public class FibClient extends Thread implements MessageTypes{
    
    String host = null;
    int port;

    Properties properties;
    Integer number;

    public FibClient(String serverPropertiesFile, Integer number) {
        try {
            properties = new PropertyHandler(serverPropertiesFile);
            host = properties.getProperty("HOST");
            System.out.println("[FibClient.FibClient] Host: " + host);
            port = Integer.parseInt(properties.getProperty("PORT"));
            System.out.println("[FibClient.FibClient] Port: " + port);
        } catch (Exception e) {
            System.err.println("[FibClient] Error: " + e);
            e.printStackTrace();
        }
        this.number = number;
    }
    
    @Override
    public void run() {
        try { 
            
            // connect to application server
            Socket server = new Socket(host, port);
            
            // hard-coded string of class, aka tool name ... plus one argument
            String classString = "appserver.job.impl.Fib";
            Integer number = new Integer(this.number);
            
            // create job and job request message
            Job job = new Job(classString, number);
            Message message = new Message(JOB_REQUEST, job);
            
            // sending job out to the application server in a message
            ObjectOutputStream writeToNet = new ObjectOutputStream(server.getOutputStream());
            writeToNet.writeObject(message);
            
            // reading result back in from application server
            // for simplicity, the result is not encapsulated in a message
            ObjectInputStream readFromNet = new ObjectInputStream(server.getInputStream());
            Integer result = (Integer) readFromNet.readObject();
            System.out.println("RESULT: " + result);
            
        } catch (Exception e) {
            System.err.println("[PlusOneClient.run] Error: " + e);
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        
        /* Threads out the FibClient on numbers = 46,45.....0 to calculate 
            the fibonacci number */ 
        for (int i=46;i>0;i--){
          (new FibClient("../../config/Server.properties",i)).start();  
        }
        
        
    }  
}
