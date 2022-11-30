package Thread1.demo3;

public class App {

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
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
        });
        t1.start();
    }
}
