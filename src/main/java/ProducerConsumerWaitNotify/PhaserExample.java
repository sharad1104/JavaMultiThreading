package ProducerConsumerWaitNotify;

import java.util.concurrent.Phaser;

public class PhaserExample {
    public static void main(String[] args) {
        int numThreads = 3;
        Phaser phaser = new Phaser(numThreads);

        for (int i = 1; i <= numThreads; i++) {
            Thread t = new Thread(new Worker(phaser, i));
            t.start();
        }
    }

    static class Worker implements Runnable {
        private Phaser phaser;
        private int id;
        public Worker(Phaser phaser, int id) {
            this.phaser = phaser;
            this.id = id;
        }
        @Override
        public void run() {
            System.out.println("Worker " + id + " starting phase 0 work...");
            work();

            phaser.arriveAndAwaitAdvance(); // wait for others
            System.out.println("Worker " + id + " starting phase 1 work...");
            work();

            phaser.arriveAndAwaitAdvance();
            System.out.println("Worker " + id + " done with all phases.");
        }
        private void work() {
            try {
                Thread.sleep((long) (Math.random() * 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}