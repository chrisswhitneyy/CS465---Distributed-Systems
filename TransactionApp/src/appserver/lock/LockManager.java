package appserver.lock;

import appserver.data.Account;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * Class [LockManager] : Handles and manages all of the lock. Using this class one can set a lock on an object and
 * unlock that same object.
 *
 * Note: Code is adapted from Distributed Systems Concept and Design 5th Edition by George Coulouris
 *
 * Author Christopher D. Whitney on May 1st, 2017
 *
 **/

public class LockManager implements LockType {

    private HashMap<Account, Lock> locks;

    /** Class constructor
     *
     */
    public LockManager(){
        locks = new HashMap();
    }

    /**
     * setLock : Acquires a lock for the  account and adds it to the hash map of locks.
     * This function contains a synchronized block so that threads attempting
     * to set a lock do not conflict.
     *
     * @param account - object to lock
     * @param TID - Transaction ID
     * @param lockType - the type of lock
     */
    public void setLock(Account account, int TID, int lockType){

        Lock lock; // found lock

        synchronized(this){
            // find lock associated with account
            lock = locks.get(account);


            // if there isn't one, create it and add it to the hash table
            if (lock == null){
                lock = new Lock (account);
                locks.put(account,lock);
                System.out.println("[LockManager].setLock on Account " + account.getId());
            }

            // acquire lock for TID
            lock.acquire(TID,lockType);
            // log
            System.out.println("[LockManager].setLock " + lockType + " set on TID " + TID + ".");
        }
    }

    /**
     * unLock : Releases the lock held by the TID. This method is synchronize so that threads don't conflict
     *
     * @param TID - Transaction ID
     */
    public synchronized void unLock(int TID) {

        Lock tempLock; // temp lock for iterations
        ArrayList<Integer> TIDs; // list of TIDs in a lock

        // iterates through all the locks and release the locks that contain TID
        Iterator iterator = locks.entrySet().iterator();
        while (iterator.hasNext()){
            tempLock = (Lock) ((HashMap.Entry) iterator.next()).getValue(); // get locks from hash map
            TIDs = tempLock.getTIDsHolders(); // get TIDs held by the lock
            if ( TIDs.contains(TID) ){
                // trans is a holder of this lock
                tempLock.release(TID); // release TID
            }
            iterator.remove(); // remove entry from iterator
        }
        // log
        System.out.println("[LockManager].unlock TID " + TID + " unlocked.");
    }

    public HashMap<Account, Lock> getLocks(){
        return this.locks;
    }

}
