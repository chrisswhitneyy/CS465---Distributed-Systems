package appserver.lock;

import java.util.ArrayList;

/**
 * Class [Lock] : Each lock is an instance of this class and is associated with an object. It maintains three instance
 * variables, the object its locking, a list of the transaction which hold the lock, and the current lock type. It's
 * methods are synchronized so that threads attempting to acquire or release a lock will not interfere with one another.
 *
 * Note: Code is adapted from Distributed Systems Concept and Design 5th Edition by George Coulouris
 *
 * Author: Christopher D. Whitney
 * Date Created: May 1st, 2017
 *
 **/

public class Lock implements LockType {

    private Object object; // the object being protected by the lock
    private ArrayList<Integer> holders; // the TIDs of current holders
    private int currentLockType; // the current type

    public Lock(Object object){
        this.object = object;
        holders = new ArrayList<>();
    }

    public synchronized void acquire(int TID, int lockType){

        while(isConflict(TID,lockType)){
            try { wait(); }
            catch ( InterruptedException e){/*...*/}
        }

        if (holders.isEmpty()) {
            // no TIDs hold lock
            holders.add(TID);
            currentLockType = lockType;

        } else if (!holders.isEmpty()) {
            // if another transaction holds the lock, share it.
            if (!holders.contains(TID)){
                holders.add(TID);
            }

        } else if (holders.contains(TID) && lockType == READ_LOCK){
            // this transaction is a holder but needs a more exclusive lock
            lockType = WRITE_LOCK;
            currentLockType = lockType;
            holders.add(TID);
        }

    }

    public synchronized void release(int TID) {
        // remove this holder
        holders.remove(TID);

        if (holders.isEmpty()) {
            // if no more holders set locktype to empty
            currentLockType = EMPTY_LOCK;
        }
        notifyAll();
    }

    public boolean isConflict(int trans, int newLockType){

        // checks to see the conditions when there is no conflicts
        if (holders.isEmpty()){
            // no conflict because no locks in holder
            // log
            return false;
        }
        // holder list length 1 and lock holder has trans
        else if (holders.size() == 1 && holders.contains(trans)){
            //log
            return false;
        }
        // holder list length 1 and lock holder has trans
        else if (holders.size() == 1 && holders.contains(trans)){
            //log
            return false;
        }
        // current lock type is read and new lock type is read
        else if (currentLockType == READ_LOCK && newLockType == READ_LOCK){
            //log
            return false;
        }
        // everything is a conflict
        else {
            return true;
        }
    }
}
