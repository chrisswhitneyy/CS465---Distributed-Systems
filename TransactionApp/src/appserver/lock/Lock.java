package appserver.lock;

import appserver.lock.LockType;

/**
 * Created by cdubs on 4/25/17.
 */
public class Lock {

    private Object object; // the object being protected by the lock
    private ArrayList<int> lockHolders; // the TIDs of current holders
    private HashMap <trans,Object[]> lockRequestors;
    private LockType currentLockType; // the current type

    public synchronized void acquire(TransID trans, LockType lockType ){

        while(isConflict(trans,lockType) {

            try {
                wait();

            } catch ( InterruptedException e){
                /*...*/
            }
        }

        if (lockHolders.isEmpty()) {
            // no TIDs hold lock
            // holders.addElement(trans);
            lockType = aLockType;
        } else if (/*another transaction holds the lock, share it*/ ) ) {
            if (/* this transaction not a holder*/)
                lockHolders.addElement(trans);
        } else if (/* this transaction is a holder but needs a more exclusive lock*/){
            lockType.promote();
        }

    }

    public synchronized void release(int trans){

        lockHolders.removeElement(trans);

        if (lockHolders.isEmpty()){
            currentLockType = EMPTY_LOCK;
        }

        notifyAll();
    }

    public boolean isConflict(int trans, int newLockType){

        // checks to see the conditions when there is no conflicts
        if (lockHolders.isEmpty()){
            // no conflict because no locks in holder
            // log
            return false;
        }
        // holder list length 1 and lock holder has trans
        else if (lockHolders.size() == 1 && lockHolders.contains(trans)){
            return false;
        }
        // current lock type is read and new lock type is read
        else if (currentLockType == READ_LOCK && newLockType == READ_LOCK){
            return false;
        }
        // everything is a conflict
        else {
            return true;
        }

    }
}
