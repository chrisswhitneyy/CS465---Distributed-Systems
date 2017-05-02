
package appserver.comm;

/**
 * Interface [MessageTypes] Defines the different message types used in the application.
 * Any entity using objects of class Message needs to implement this interface.
 * 
 * Author Christopher D. Whitney on April 28th, 2017
 */
public interface MessageTypes {
    int OPEN_TRANS = 1;
    int CLOSE_TRANS = 2;
    int READ_REQUEST = 3;
    int WRITE_REQUEST = 4;
    int ACCOUNT_TOTAL_REQUEST = 5;
}
