package ProducerConsumerWaitNotify;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinRecursiveTask {
    private static int [] arr = {1,2,3,4,5,6,7,8,9,10};
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(2);

        int result = pool.invoke(new Summation(0, 9));
        System.out.println(result);



    }


    static class Summation extends RecursiveTask<Integer> {
        int left;
        int right;
        public Summation(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        protected Integer compute() {
            if(left == right){
                return arr[left];
            }
            int mid = left + (right - left)/2;
            Summation leftTask = new Summation(left, mid);
            Summation rightTask = new Summation(mid+1, right);
            leftTask.fork();
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();
            return leftResult + rightResult;
        }
    }
}
