package Thread14;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.*;

/**
 * Callable and Future
 */
public class App {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        /* use Future<?> to return null and take adv of Future methods */
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                SecureRandom random = new SecureRandom();
                int duration = random.nextInt(4000);

                System.out.println("Starting...");

                /* simulate an exception in the callable */
                if (duration > 2000) {
                    throw new IOException("Sleeping for too long");
                }

                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Finished.");
                return duration;
            }
        });
        executor.shutdown();

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            /* cast execution back to I/O exception */
            IOException ex = (IOException) e.getCause();
            System.out.println(ex.getMessage());
        }

    }
}
