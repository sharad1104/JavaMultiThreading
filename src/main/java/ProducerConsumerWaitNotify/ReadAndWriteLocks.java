package ProducerConsumerWaitNotify;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadAndWriteLocks {
    private static ReadWriteLock readWriteLocks = new ReentrantReadWriteLock();
    private static Lock readLock = readWriteLocks.readLock();
    private static Lock writeLock = readWriteLocks.writeLock();

    private static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();

        Thread worker = new Thread(new WriterThread());
        Thread reader = new Thread(new ReaderThread());
        Thread reader1 = new Thread(new ReaderThread());
        Thread reader2 = new Thread(new ReaderThread());
        Thread reader3 = new Thread(new ReaderThread());
        threads.add(worker);
        threads.add(reader3);
        threads.add(reader1);
        threads.add(reader2);

        worker.start();
        reader.start();
        reader1.start();
        reader2.start();
        reader3.start();
        threads.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


    }

    static class WriterThread implements Runnable{
        @Override
        public void run() {
            while(true){
                try {
                    writeData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void writeData() throws InterruptedException {
        Thread.sleep(10000);

        writeLock.lock();
        int value = (int) Math.random();
        System.out.println("Producing data " + value);
        list.add(value);
        writeLock.unlock();

    }

    static class ReaderThread implements Runnable{
        @Override
        public void run() {
            while(true){
                try {
                    readData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        private void readData() throws InterruptedException {
            Thread.sleep(4000);
            readLock.lock();
            System.out.println("List is " + list);
            readLock.unlock();
        }
    }

}
