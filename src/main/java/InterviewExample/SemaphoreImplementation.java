package org.example;


import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {

    static class Semaphore {
        private  int permits;

        public Semaphore(int permits) {
            this.permits = permits;
        }
        public void acquire() throws InterruptedException {
            synchronized (this) {
                while (permits == 0) {
                    this.wait();
                }
                permits--;
            }
        }
        public void release() {
            synchronized (this) {
                permits = permits + 1;
                this.notifyAll();
            }
        }

    }

    static class MainThread implements Runnable {
        private Semaphore semaphore;
        public MainThread(Semaphore semaphore) {
            this.semaphore = semaphore;
        }
        @Override
        public void run() {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "doing something");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);

        for(int i=0;i<5;i++){
            new Thread(new MainThread(semaphore)).start();
        }


    }



}
