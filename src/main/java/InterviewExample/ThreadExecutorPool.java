package InterviewExample;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadPoolClass {

    private final int maxPoolSize;
    private final Queue<Runnable> taskQueue;
    private boolean isShutdown;
    private int activeCount;
    private final Object lock = new Object();

    public ThreadPoolClass(int size) {
        this.maxPoolSize = size;
        this.taskQueue = new LinkedList<>();
        this.isShutdown = false;
    }

    public void shutDown() {
        synchronized (lock) {
            isShutdown = true;
            System.out.println("Shutting down");
            lock.notifyAll();
        }
    }

    public void submitTask(Runnable task) {

        synchronized (lock) {
            if (isShutdown) {
                throw new RuntimeException("ThreadPool is shutting down");
            }
            taskQueue.add(task);
            if(activeCount < maxPoolSize) {
                Thread worker = new Thread(new Worker());
                activeCount++;
                worker.start();
            }
            lock.notifyAll();
        }
    }

    class Worker implements Runnable {

        @Override
        public void run() {
            while (true) {
                Runnable task;

                synchronized (lock) {
                    while (taskQueue.isEmpty() && !isShutdown) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    if (taskQueue.isEmpty() && isShutdown) {
                        activeCount--;
                        return; // graceful exit
                    }

                    task = taskQueue.poll();
                }

                // ✅ Execute OUTSIDE synchronized block
                try {
                    task.run();
                } catch (Exception e) {
                    System.out.println("Unable to run task");
                }
            }
        }
    }
}
