package InterviewExample;


public class NumbersThreeThreads {
    private static int limit = 10;
    private static int currNum = 1;
    private static int pos = 0;
    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[3];
        threads[0] = new Thread(new ZeroWorker());
        threads[1] = new Thread(new EvenWorker());
        threads[2] = new Thread(new OddWorker());


        for (Thread thread : threads) {
            thread.start();
        }
    }

    static class ZeroWorker implements Runnable {
        @Override
        public void run() {
            while(currNum<limit) {
            synchronized (lock) {
                    while (pos % 2 != 0) {
                        if (currNum > limit) {
                            lock.notifyAll();
                            return;
                        }
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.print(0 + " ");
                    pos = 1 - pos;
                    lock.notifyAll();
                }
            }
        }
    }

    static class EvenWorker implements Runnable {
        @Override
        public void run() {
            while(currNum<limit) {
            synchronized (lock) {
                    while (pos % 2 == 0 || currNum % 2 != 0) {
                        if (currNum > limit) {
                            lock.notifyAll();
                            return;
                        }
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.print(currNum + " ");
                    currNum++;
                    pos = 1 - pos;
                    lock.notifyAll();
                }
            }
        }
    }

    static class OddWorker implements Runnable {
        @Override
        public void run() {
            while(currNum<limit) {

            synchronized (lock) {
                    while (pos % 2 == 0 || currNum % 2 == 0) {
                        if (currNum > limit) {
                            lock.notifyAll();
                            return;
                        }
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.print(currNum + " ");
                    currNum++;
                    pos = 1 - pos;
                    lock.notifyAll();
                }
            }
        }
    }
}