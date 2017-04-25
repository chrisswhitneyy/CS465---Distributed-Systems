
package appserver.comm;

/**
 * Interface [MessageTypes] Defines the different message types used in the application.
 * Any entity using objects of class Message needs to implement this interface.
 * 
 * @author Christopher D. Whitney
 */
public interface MessageTypes {
    
    public static int OPEN_TRANS = 1;
    public static int CLOSE_TRANS = 2;
    public static int READ_REQUEST = 3;
    public static int WRITE_REQUEST = 4;

}
