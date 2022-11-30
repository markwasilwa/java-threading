package Thread12;

import java.util.concurrent.Semaphore;

/**
 * Using Singleton pattern.
 */
public class Connection {

    private static Connection instance = new Connection();

    private Semaphore sem = new Semaphore(10);
    private int connections = 0;

    private Connection() {

    }

    public static Connection getInstance() throws InterruptedException {
        return instance;
    }

    public void connect() {
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            doConnect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            sem.release();
        }
    }

    public void doConnect() throws InterruptedException {

        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            connections++;
            System.out.println("Current connections: " + connections);
        }

        Thread.sleep(2000);

        synchronized (this) {
            connections--;
        }
        sem.release();
    }
}
