package appserver.lock;

/**
 * Created by cdubs on 4/25/17.
 */
public class Lock {

    private Object object; // the object being protected by the lock
    private ArrayList<int> holders; // the TIDs of current holders
    private LockType lockType; // the current type

    public synchronized void acquire(TransID trans, LockType lockType ){

        while(isConflict(trans,lockType) {

            try {
                wait();

            } catch ( InterruptedException e){
                /*...*/
            }
        }

        if (holders.isEmpty()) {
            // no TIDs hold lock
            // holders.addElement(trans);
            lockType = aLockType;
        } else if (/*another transaction holds the lock, share it*/ ) ) {
            if (/* this transaction not a holder*/)
                holders.addElement(trans);
        } else if (/* this transaction is a holder but needs a more exclusive lock*/) lockType.promote();
    }

    public synchronized void release(TransID trans ){ holders.removeElement(trans);
        // remove this holder
        // set locktype to none
        notifyAll();
    }

    public boolean isConflict(Trans trans, int lockType){
        // checks to see the conditions when there is no conflicts

        if (lockholders.isEmpty()){
            // no conflict because no locks in holder
            return false;
        }
        // holder list length 1 and lock holder has trans
        else if (){
            return false;
        }
        // current lock type is read and new lock type is read
        else if (){
            return false;
        }
        // everything is a conflict
        else {
            return true;
        }

    }
}
