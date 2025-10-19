package ProducerConsumerWaitNotify;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    private static Lock lock = new ReentrantLock();
    private static int S = 0;
    private static final int[] arr = new int[10];

    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            arr[i] = 10;
        }
        int slice = arr.length/2;
        List<Thread> threads = new ArrayList<Thread>();
        for(int i=0;i<2;i++){
            Thread thread = new Thread(new WorkerThread(i*slice, (i+1)*slice));
            threads.add(thread);
            thread.start();
        }
        threads.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("All threads finished and Sum is " + S);

    }

    static class WorkerThread implements Runnable {
        private final int left;
        private final int right;

        public WorkerThread(int left, int right) {
            this.left = left;
            this.right = right;
        }
        @Override
        public void run() {
            for (int i = left; i < right; i++){
                lock.lock();
                S = S + arr[i];
                lock.unlock();
            }
        }
    }
}
