package Thread11;

import java.security.SecureRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {

    private Account acc1 = new Account();
    private Account acc2 = new Account();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    private void acquireLock(Lock firstLock, Lock secondLock) throws InterruptedException {
        while(true) {
            // acquire locks
            boolean gotFirstLock = false;
            boolean gotSecondLock = false;

            try {
                gotFirstLock = firstLock.tryLock();
                gotSecondLock = secondLock.tryLock();
            } finally {
                if (gotFirstLock && gotSecondLock)
                    return;
                if (gotFirstLock)
                    firstLock.unlock();
                if (gotSecondLock)
                    secondLock.unlock();
            }
            // Locks not acquired
            Thread.sleep(1);
        }
    }

    public void firstThread() throws InterruptedException {
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 10000; i++) {

            acquireLock(lock1, lock2);

            try {
                Account.transfer(acc1, acc2, random.nextInt(100));
            } finally {
                 lock1.unlock();
                 lock2.unlock();
            }
        }
    }

    public void secondThread() throws InterruptedException {
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 10000; i++) {

            acquireLock(lock2, lock1);

            try {
                Account.transfer(acc2, acc1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finished() {
        System.out.println("Account 1 balance: " + acc1.getBalance());
        System.out.println("Account 2 balance: " + acc2.getBalance());
        System.out.println("Total balance: " + (acc1.getBalance() + acc2.getBalance()));
    }
}
