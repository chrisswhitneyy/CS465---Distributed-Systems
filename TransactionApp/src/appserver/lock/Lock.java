package appserver.lock;

import java.util.ArrayList;

/**
 *
 * Class [Lock] : Each lock is an instance of this class and is associated with an object. It maintains three instance
 * variables, the object its locking, a list of the transaction which hold the lock, and the current lock type. It's
 * methods are synchronized so that threads attempting to acquire or release a lock will not interfere with one another.
 *
 * Note: Code is adapted from Distributed Systems Concept and Design 5th Edition by George Coulouris
 *
 * Author: Christopher D. Whitney on May 1st, 2017
 *
 **/

public class Lock implements LockType {

    private Object object; // the object being protected by the lock
    private ArrayList<Integer> holders; // the TIDs of current holders
    private int currentLockType; // the current type

    /**
     * ClassConstructor
     *
     * @param object - Object which one wants to lock
     */
    public Lock(Object object){
        this.object = object;
        holders = new ArrayList<Integer>();
    }

    /**
     * acquire - acquires a given lock type for a given transaction.
     * @param TID - transaction ID
     * @param lockType - lock type
     *
     */
    public synchronized void acquire(int TID, int lockType){


        while(isConflict(TID,lockType)){
            try { wait(); }
            catch ( InterruptedException e){/*...*/}
        }

        if (holders.isEmpty()) {
            // no TIDs hold lock
            holders.add(TID);
            currentLockType = lockType;

        } else if (currentLockType == lockType) {
            // if another transaction holds the lock, share it.
            if (!holders.contains(TID)){
                holders.add(TID);
            }
        } else if ( holders.contains(TID) ){
            // if transaction is a holder but needs a more exclusive lock
            if (currentLockType == READ_LOCK){
                lockType = WRITE_LOCK;
            }
            holders.add(TID);
            currentLockType = lockType;
        }

        System.out.println("[Lock].acquire() TID:" + TID + " LockType" + lockType + ".");

    }

    /**
     * release - releases a the lock of a given transaction
     * @param TID - transaction ID
     */
    public synchronized void release(int TID) {

        holders.remove(holders.indexOf(TID));

        currentLockType = EMPTY_LOCK;

        notifyAll();

        System.out.println("[Lock].release() TID " + TID + ".");
    }

    /**
     * isConflicts - Checks for lock conflicts with a given new lock type and the current lock type
     *
     * @param TID - transaction ID
     * @param newLockType - new lock type
     * @return flag - boolean value to flag if there is a conflict (true) or there is no conflict (false)
     */
    public boolean isConflict(int TID, int newLockType){

        // checks to see the conditions when there is no conflicts
        if (holders.isEmpty()){
            // no conflict because no locks in holder
            // log
            return false;
        }
        // holder list length 1 and lock holder has trans
        else if (holders.size() == 1 && holders.contains(TID)){
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

    /**
     * getTIDsHOlders - returns the list of TIDs
     */
    public ArrayList<Integer> getTIDsHolders(){
        return holders;
    }
}
