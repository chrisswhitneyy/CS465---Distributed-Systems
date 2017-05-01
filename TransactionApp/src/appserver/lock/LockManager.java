package appserver.lock;

import appserver.data.Account;
import java.util.HashMap;

/**
 *
 * Class [LockManager] :
 *
 * Note: Code is adapted from Distributed Systems Concept and Design 5th Edition by George Coulouris
 *
 * Author: Christopher D. Whitney
 * Date Created: May 1st, 2017
 *
 **/

public class LockManager implements LockType {

    private HashMap<Account, Lock> locks;

    public LockManager(){
        locks = new HashMap();
    }

    /**
     * Function [setLock]: Given an account object, a transaction ID, and lock type this function acquires a lock for the
     * account and adds it to the hash map of locks. This function contains a synchronized block so that threads attempting
     * to set a lock do not conflict.
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
     * Function [unLock]: Given a transaction TID this function releases the lock held by the TID. This method is
     * synchronize so that threads don't conflict.*/
    public synchronized void unLock(int TID) {
        // iternates through all the locks and releases them
    }


}
