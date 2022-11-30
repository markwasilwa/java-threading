package Thread1.demo2;

/**
 * Shows two threads running concurrently, t1 and t2
 */
class Runner extends Thread {
    @Override
    public void run() {
        for (char c : "Hello World".repeat(4).toCharArray()) {
            System.out.println(String.format("Current Char %c", c));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                System.out.println("Error " + e1.getMessage());
            }
        }
    }
}

public class App {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runner());
        Thread t2 = new Thread(new Runner());
        t1.start();
        t2.start();
    }
}