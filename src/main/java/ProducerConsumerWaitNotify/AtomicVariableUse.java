package ProducerConsumerWaitNotify;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariableUse {
    private static AtomicInteger atomicVariable = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(()->{
            for(int i=0;i<100;i++){
                atomicVariable.incrementAndGet();
            }
        });
        Thread t2 = new Thread(()->{
            for(int i=0;i<100;i++){
                atomicVariable.incrementAndGet();
            }
        });
        Thread t3 = new Thread(()->{
            for(int i=0;i<100;i++){
                atomicVariable.incrementAndGet();
            }
        });

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Final Value is " + atomicVariable.get());

    }
}
