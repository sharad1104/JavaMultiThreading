package ProducerConsumerWaitNotify;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    public static void main(String[] args) {
        int numThreads = 3;

        // Create a barrier that waits for 3 threads
        CyclicBarrier barrier = new CyclicBarrier(numThreads, () -> {
            System.out.println("✅ All threads reached the barrier. Proceeding together...");
        });

        for (int i = 1; i <= numThreads; i++) {
            Thread t = new Thread(new Worker(barrier, i));
            t.start();
        }
    }
}

class Worker implements Runnable {
    private CyclicBarrier barrier;
    private int id;

    Worker(CyclicBarrier barrier, int id) {
        this.barrier = barrier;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            System.out.println("Worker " + id + " performing initial work...");
            Thread.sleep((long) (Math.random() * 2000));

            System.out.println("Worker " + id + " waiting at the barrier...");
            barrier.await(); // waits for others

            System.out.println("Worker " + id + " proceeding after barrier...");

        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}