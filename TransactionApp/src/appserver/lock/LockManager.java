package appserver.lock;

import appserver.data.Account;
import java.util.HashMap;

/**
 *
 * Class [LockManager] :
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

            // if there isn't one, create it and add it to the hashtable
            if (lock == null){
                lock = new Lock (account);
                locks.put(account,lock);
                System.out.println("[LockManager].setLock on Account " + account.getId());
            }

            // acquire lock for TID
            lock.acquire(TID,lockType);
        }
    }

    /**
     * unLock : Releases the lock held by the TID. This method is synchronize so that threads don't conflict
     *
     * @param TID - Transaction ID
     */
    public synchronized void unLock(int TID) {
        // iternates through all the locks and releases them
    }


}
