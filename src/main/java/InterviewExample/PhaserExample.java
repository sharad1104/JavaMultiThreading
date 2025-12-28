package InterviewExample;



public class Main {

    static class BarrierExample {
        private int counter;
        private final int initialCounter;
        private int phase;

        public BarrierExample(int counter) {
            this.counter = counter;
            this.initialCounter = counter;
            this.phase = 0;
        }

        public void await() throws InterruptedException {
            synchronized (this) {
                int myPhase = phase;
                counter--;

                if (counter == 0) {
                    System.out.println("BarrierExample has been awaited, All the required threads have reached for phase " + phase);
                    phase++;
                    counter = initialCounter;
                    this.notifyAll();
                } else {
                    while (myPhase == phase ) {
                        this.wait();
                    }
                }
            }
        }
    }

    static class MainThread implements Runnable {
        private BarrierExample barrierExample;
        public MainThread(BarrierExample BarrierExample) {
            this.barrierExample = BarrierExample;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "doing something");
            try {
                barrierExample.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "done something");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread().getName() + "Again doing something");
            try {
                barrierExample.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "Again done something");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
    }

    public static void main(String[] args) {
        BarrierExample barrierExample = new BarrierExample(5);
        for(int i=0;i<5;i++){
            new Thread(new MainThread(barrierExample)).start();
        }


    }



}
