package Thread7;

import java.security.SecureRandom;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Producer-Consumer pattern using Blocking Queues
 * <br/>
 * Threads add values to a queue, other Thread reads values from this queue
 */
public class App {

    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    private static void producer() throws InterruptedException {
        SecureRandom random = new SecureRandom();
        while(true) {
            /* waits till the queue has space before adding new items */
            queue.put(random.nextInt(100));
        }
    }

    private static void consumer() throws InterruptedException {
        SecureRandom random = new SecureRandom();
        while(true) {
            Thread.sleep(100);
            if (random.nextInt() == 0) {
                /* waits till queue has value before consuming it */
                Integer value = (Integer) queue.take();
                System.out.println("Taken value: " + value + "; Queue size is" + queue.size());
            }
        }
    }

}
