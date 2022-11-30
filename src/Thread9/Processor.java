package Thread9;

import java.security.SecureRandom;
import java.util.LinkedList;

public class Processor {

    private LinkedList<Integer> list = new LinkedList<>();
    private final int LIMIT = 10;
    private Object lock = new Object();

    public void produce() throws InterruptedException {
        int value = 0;
        synchronized (lock) {
            while (true) {
                /**
                 * keep checking that this condition has been met,
                 * value keeps changing because consumer is continuously removing values
                 */
                while (list.size() == LIMIT) {
                    lock.wait();
                }
                list.add(value++);
                lock.notify();
            }
        }

    }

    public void consume() throws InterruptedException {
        SecureRandom random = new SecureRandom();
        while (true) {
            synchronized (lock) {
                while(list.size() == 0) {
                    lock.wait();
                }
                System.out.println("list size is: " + list.size());
                int value = list.removeFirst();
                System.out.println("value is: " + value);
                lock.notify();
            }
            Thread.sleep(random.nextInt(100));
        }
    }

}
