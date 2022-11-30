package Thread11;

/**
 * DeadLocks
 * <br/>
 * Running into deadlocks when acquiring a lock. <br/>
 * How to avoid dead locks when acquiring them.
 */
public class App {

    private static Runner runner = new Runner();

    public App(Runner runner) {
        this.runner = runner;
    }

    public static void main(String[] args) throws InterruptedException {
        runner.firstThread();
        runner.secondThread();
        runner.finished();
    }
}
