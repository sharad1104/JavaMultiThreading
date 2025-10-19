package synchronisation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    private static int globalCounter = 0;
    private static final Object obj = new Object();
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        ThreadGroup threadGroup = new ThreadGroup("Group1");

        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(threadGroup, new MyThread());
            t.start();
            threads.add(t);
        }

        threadGroup.interrupt();

        threads.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Count is : " + globalCounter);
    }
    private static class MyThread implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(999999);
            } catch (InterruptedException ignored) {

            }
            synchronized (obj) {
                globalCounter++;
            }
        }
    }
}
