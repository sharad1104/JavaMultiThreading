package ProducerConsumerWaitNotify;

import java.util.concurrent.*;

public class ThreadPoolExecutorServiceExample {
    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            3,
            5,
            2,
            TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(3)
    );

    public static void main(String[] args) throws InterruptedException {

        threadPoolExecutor.execute(() ->{
            System.out.println("Hello World");
        });

        Future<Integer> ans = threadPoolExecutor.submit(new CallableTask(10));
        try {
            System.out.println("Future: " + ans.get());
        } catch (ExecutionException e) {
            System.out.println("Future Exception: " + e);
        }

        threadPoolExecutor.shutdown();

    }

    static class CallableTask implements Callable<Integer> {

        public Integer id;
        public CallableTask(Integer id) {
            this.id = id;
        }
        @Override
        public Integer call() throws Exception {
            return this.id;
        }
    }


}
