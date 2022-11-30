package Thread9;

/**
 * Using low level synchronization
 * <br/>
 * Implementing Producer & Consumer using Thread.wait() and Thread.notify()
 */
public class App {

    private static Processor processor = new Processor();

    public App (Processor processor) {
        this.processor = processor;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consume();
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

}
