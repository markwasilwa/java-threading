package Thread10;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {

    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public void firstThread() throws InterruptedException {
        lock.lock();
        // Handover control of the lock with Condition.await()
        System.out.println("Waiting ....");
        cond.await();
        System.out.println("Woken up!");

        try {
            increment();
        } catch (Exception e) {
            System.out.println(e.getCause());
        } finally {
            // In case an exception is thrown in increment()
            lock.unlock();
        }
    }

    public void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();

        System.out.println("Waiting for return key ...");
        new Scanner(System.in).nextLine();
        System.out.println("Got return key!");

        // Release cond.await()
        cond.signal();

        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void finished() {
        System.out.println("Count is: " + count);
    }
}
