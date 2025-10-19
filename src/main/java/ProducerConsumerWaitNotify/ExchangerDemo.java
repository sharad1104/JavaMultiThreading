package ProducerConsumerWaitNotify;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;

public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        Thread producer = new Thread(new Producer(exchanger));
        Thread consumer = new Thread(new Consumer(exchanger));

        producer.start();
        consumer.start();
    }
}

class Producer implements Runnable {
    private Exchanger<String> exchanger;

    Producer(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            String message = "Data from Producer";
            System.out.println("Producer sending: " + message);

            // exchange data with consumer
            String response = exchanger.exchange(message);

            System.out.println("Producer received: " + response);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    private Exchanger<String> exchanger;

    Consumer(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            String message = "Data from Consumer";
            System.out.println("Consumer sending: " + message);

            // exchange data with producer
            String response = exchanger.exchange(message);

            System.out.println("Consumer received: " + response);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}