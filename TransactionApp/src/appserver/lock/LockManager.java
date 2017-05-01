package appserver.lock;

/**
 * Created by cdubs on 4/20/17.
 */
public class LockManager implements LockType {

    private HashMap<Account, Lock> locks;

    public LockManager(){
        locks = new HashMap();
    }

    public void setLock(Account account, TransID trans, LockType lockType){

        Lock lock;

        synchronized(this){

            lock = locks.get(account);

            if (lock == null){
                lock = new Lock (account);
                locks.put(account,lock);
                transaction.log(" [LockManager].setLock ");
            }
            lock.acquire(tans,lockType);
        }

        foundLock.acquire(trans, lockType);
    }

    // synchronize this one because we want to remove all entries
    public synchronized void unLock(TransID trans) {
        // iternates through all the locks and releases them
    }


}
