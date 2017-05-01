package appserver.lock;

/**
 * Interface [LockType] Defines the different lock types used in the application.
 * Any entity using objects of class Message needs to implement this interface.
 *
 * Author Christopher D. Whitney on April 28th, 2017
 */
public interface LockType {
    int EMPTY_LOCK = 0;
    int READ_LOCK = 1;
    int WRITE_LOCK = 2;
}
