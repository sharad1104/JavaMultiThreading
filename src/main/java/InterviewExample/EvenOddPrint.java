package InterviewExample;

public class EvenOddPrint {
    private static int currNum = 0;
    private static final int capacity = 21;
    private static final Object lock = new Object();



    public static void main(String[] args) {
        Thread[] threads = new Thread[2];
        threads[0] = new Thread(new EvenThread());
        threads[1] = new Thread(new OddThread());
        threads[0].start();
        threads[1].start();
    }

    static class EvenThread implements Runnable {

        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                synchronized (lock) {
                    while (currNum % 2 != 0 ) {
                        if(currNum > capacity) {
                            lock.notifyAll();
                            return;
                        }
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (currNum > capacity) return;
                    System.out.print(currNum + " ");
                    currNum = currNum+1;
                    lock.notifyAll();
                }

            }

        }
    }

    static class OddThread implements Runnable {

        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                synchronized (lock) {

                    while (currNum % 2 == 0 ) {
                        if(currNum > capacity) {
                            lock.notifyAll();
                            return;
                        }
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (currNum > capacity) return;
                    System.out.print(currNum + " ");
                    currNum = currNum+1;
                    lock.notifyAll();
                }

            }


        }
    }






}
