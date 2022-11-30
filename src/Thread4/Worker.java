package Thread4;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Worker {
    private SecureRandom random = new SecureRandom();

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();

    public void stageOne() {
        synchronized (lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            list1.add(random.nextInt());
        }
    }

    public void stageTwo() {
       synchronized (lock2) {
           try {
               Thread.sleep(1);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
           list2.add(random.nextInt());
       }
    }

    public void process(){
        for (int i = 0; i < 1000; i++) {
            stageOne();
            stageTwo();
        }
    }
    public void main() {
        System.out.println("Starting ...");

        long start = System.currentTimeMillis();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e1) {
            System.out.println(e1.getMessage());
        }
        long end = System.currentTimeMillis();
        System.out.println("Time taken " + (end - start));
        System.out.println("List1: " + list1.size() + " List2: " + list2.size());
    }
}
