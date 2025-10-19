package ProducerConsumerWaitNotify;

class MyTask implements Runnable {
    private volatile boolean running = true;

    public void run() {
        while (running) {
            System.out.println("Working...");
        }
    }

    public void stop() {
        running = false;
    }
}

public class VolatileDemo {
    public static void main(String[] args) throws InterruptedException {
        MyTask task = new MyTask();
        Thread t = new Thread(task);
        t.start();

        Thread.sleep(1000);
        task.stop();
        System.out.println("Requested stop");
    }
}