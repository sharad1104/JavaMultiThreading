package InterviewExample;


import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    private static int count = 0;
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(new WriterThread());
        }
        for (int i = 5; i < 10; i++) {
            threads[i] = new Thread(new ReaderThread());
        }

        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }


    }

    static class ReaderThread implements Runnable {
        @Override
        public void run() {
            try {
                lock.readLock().lock();
                Thread.sleep(5000);
                System.out.println("count value is " + count);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.readLock().unlock();
            }
        }
    }

    static class WriterThread implements Runnable {
        @Override
        public void run() {
            try {
                lock.writeLock().lock();
                count = count + 1;
                System.out.println("updated count value to " + count);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.writeLock().unlock();
            }
        }
    }

}
