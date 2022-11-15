import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        worker.main();
    }
}

class Worker {
    Random random = new Random();

    private final List<Integer> list1 = new ArrayList<>();
    private final List<Integer> list2 = new ArrayList<>();

    public void addToList1() throws InterruptedException {
        Thread.sleep(1);
        synchronized (list1) {
            list1.add(random.nextInt(100));
        }
    }

    public void addToList2() throws InterruptedException {
        Thread.sleep(1);
        synchronized (list2) {
            list2.add(random.nextInt(100));
        }
    }

    public void work() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            addToList1();
            addToList2();
        }
    }

    public void main() throws InterruptedException {
        long before = System.currentTimeMillis();

        Thread newThread = new Thread(() -> {
            try {
                work();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        newThread.start();

        Thread newThread2 = new Thread(() -> {
            try {
                work();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        newThread2.start();

        newThread.join();
        newThread2.join();
       // work();

        long after = System.currentTimeMillis();
        System.out.println("Program took " + (after - before) + " ms to run");

        System.out.println("list1: " + list1.size());
        System.out.println("list2: " + list2.size());

    }
}

