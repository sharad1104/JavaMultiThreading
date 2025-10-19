package ProducerConsumerWaitNotify;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerUsingConditionVariables {
    private static final Queue<String> queue = new LinkedList<>();
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();


    public static void main(String[] args) {
        List<Thread> threads  = new ArrayList<>();
        Thread consumerThread = new Thread(new Consumer(queue));
        Thread producerThread = new Thread(new Producer(queue));

        threads.add(consumerThread);
        threads.add(producerThread);
        for (Thread thread : threads) {
            thread.start(); 
        }
        threads.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    static class Producer implements Runnable {
        private final Queue<String> queue;
        public Producer(Queue<String> queue) {
            this.queue = queue;
        }
        @Override
        public void run() {
            while (true){
                try {
                    produceData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        private void produceData() throws InterruptedException {
            lock.lock();
               if (queue.size()==10){
                   System.out.println("Queue is full, waiting to produce.....");
                   condition.await();
               }
               Thread.sleep(1000);
               System.out.println("Producing data..." + "element_" + queue.size());
               queue.add("element_" + queue.size());
               condition.signal();
               lock.unlock();

        }
    }

    static class Consumer implements Runnable {
        private final Queue<String> queue;
        public Consumer(Queue<String> queue) {
            this.queue = queue;
        }
        @Override
        public void run() {
            while (true){
                try {
                    consumeData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        private void consumeData() throws InterruptedException {
            lock.lock();
                if (queue.isEmpty()) {
                    System.out.println("Queue is empty, waiting to consume...");
                    condition.await();
                }
                Thread.sleep(1000);
                System.out.println("Consuming data..." +  queue.remove());
                condition.signal();
            lock.unlock();
        }
    }

}
