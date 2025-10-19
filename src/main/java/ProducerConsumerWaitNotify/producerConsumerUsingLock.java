package ProducerConsumerWaitNotify;

import java.util.LinkedList;
import java.util.Queue;

public class producerConsumerUsingLock {
    public static void main(String[] args)  {
        Queue<String> queue = new LinkedList<>();
        Thread producer = new Thread(new Producer(queue));
        Thread consumer = new Thread(new Consumer(queue));
        producer.start();
        consumer.start();
    }

    static class Producer implements Runnable {
        private final Queue<String> queue;

        public Producer(Queue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    produceData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        void produceData() throws InterruptedException {
            synchronized (queue) {
                if (queue.size() == 10) {
                    System.out.println("Queue is full, Producer data is waiting to produce  ");
                    queue.wait();
                }
                Thread.sleep(1000);
                System.out.println("Producer adding element_ " + queue.size());
                queue.add("element_ " + queue.size());
                if(queue.size() == 1){
                    queue.notify();
                }
            }
        }
    }

    static class Consumer implements Runnable {
        private final Queue<String> queue;
        public Consumer(Queue<String> queue) {
            this.queue = queue;
        }
        @Override
        public void run() {
            while(true) {
                try {
                    consumeData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        void consumeData() throws InterruptedException {
            synchronized (queue) {
                while (queue.isEmpty()){
                    System.out.println("Consumer data is waiting to consume ");
                    queue.wait();
                }
                Thread.sleep(700);
                System.out.println("Consumer data  " + queue.remove());
                if(queue.size() == 9){
                    queue.notify();
                }
            }

        }
    }
}
