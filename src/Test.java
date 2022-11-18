import java.util.Random;
import java.util.concurrent.*;

public class Test {
    private static final BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            try {
                produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    private static void produce() throws InterruptedException {
        Random random = new Random();

        while (true) {
            blockingQueue.put(random.nextInt(100));
        }
    }

    private static void consumer() throws InterruptedException {
         while (true) {
            Thread.sleep(1000);
            System.out.println(blockingQueue.take());
             System.out.println("Queue size is " + blockingQueue.size());
        }
    }
}