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
 * Class [FibClient] A primitive POC client that uses the Fib tool
 * 
 * @author Christopher D. Whitney 
 */
public class FibClient implements MessageTypes{
    
    String host = null;
    int port;

    Properties properties;

    public FibClient(String serverPropertiesFile) {
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
    }
    
    public void run() {
        try { 
            
            // connect to application server
            Socket server = new Socket(host, port);
            
            // hard-coded string of class, aka tool name ... plus one argument
            String classString = "appserver.job.impl.Fib";
            Integer number = new Integer(3);
            
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
        
        FibClient client = null;
        if(args.length == 1) {
            client = new FibClient(args[0]);
        } else {
            client = new FibClient("../../config/Server.properties");
        }
        client.run();
    }  
}
