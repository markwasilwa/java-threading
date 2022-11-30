package Thread8;

import java.util.Scanner;

/**
 * Using Thread.wait() and Thread.notify()
 */
public class Processor {

    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer thread running ...");
            // this block loses control of this block
            wait();
            // From another thread locked on this object, call notify
            System.out.println("Resumed.");
        }
    }

    public void consume() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);

        synchronized (this) {
            System.out.println("Waiting for return key.");
            scanner.nextLine();
            System.out.println("Return key pressed");
            // Relinquish lock back to producer
            notify();
            // Notify does not release the lock back to the wait() block
            Thread.sleep(5000);
            System.out.println("Slept for 5 more seconds");
        }
    }
}
