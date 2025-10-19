package ProducerConsumerWaitNotify;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    private static CountDownLatch countDownLatch = new CountDownLatch(2);
    private static final int arr[] = new int[]{1,2,3,4,5,6,7,8};
    private static final int numberToSearch = 7;
    private static int position = -1;

    public static void main(String[] args) throws InterruptedException {
        Thread thread1  = new Thread(new Worker(0,4));
        Thread thread2 = new Thread(new Worker(5, 7));

        thread1.start();
        thread2.start();

        System.out.println("Main thread waiting");
        countDownLatch.await();
        System.out.println("Main thread finished");
        System.out.println("Position is " + position);
    }

    static class Worker implements Runnable {
        private final int left;
        private final int right;

        public Worker(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public void run() {
            for(int i=left;i<right;i++) {
                if(arr[i]==numberToSearch) {
                    position = i;
                    break;
                }
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            countDownLatch.countDown();
        }
    }
}
