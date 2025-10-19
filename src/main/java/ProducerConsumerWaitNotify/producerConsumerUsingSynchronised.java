package ProducerConsumerWaitNotify;

import java.util.LinkedList;
import java.util.Queue;

public class producerConsumerUsingSynchronised {
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
            while (true) {
                try {
                    produceData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        private void produceData() throws InterruptedException {
            synchronized (queue) {
                if (queue.size() == 10) {
                    System.out.println("Queue is full, Producer is waiting to produce the data");
                    queue.wait();
                }
                Thread.sleep(1000);
                System.out.println("Producing data with id " + queue.size());
                queue.add("element_" + queue.size());
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
                    consumedata();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void consumedata() throws InterruptedException {
            synchronized (queue) {
                if(queue.isEmpty()) {
                    System.out.println("Queue is empty, Consumer is waiting to consume the data");
                    queue.wait();
                }
                Thread.sleep(700);
                String data = queue.remove();
                System.out.println("Consuming data  " + data);
                if(queue.size() == 9){
                    queue.notify();
                }

            }

        }
    }
}
