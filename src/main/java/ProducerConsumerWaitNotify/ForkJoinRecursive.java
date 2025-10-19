package ProducerConsumerWaitNotify;

import java.util.concurrent.*;
public class ForkJoinRecursive {
    private static int [] array = {1,2,3,4,5,6,7,8,9,10};

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(2);
        int ans = pool.invoke(new IncrementalThread(0,9));

        for(int i = 0; i < array.length; i++){
            System.out.println(array[i]);
        }
        System.out.println("operations performed: " + ans);

    }

    static class IncrementalThread extends  RecursiveTask<Integer> {
        int left;
        int right;
        public IncrementalThread(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        protected Integer compute() {
            if (left == right) {
                array[left]++;
                return 1;
            } else {
                int mid = left + (right - left) / 2;
                IncrementalThread leftTask = new IncrementalThread(left, mid);
                IncrementalThread rightTask = new IncrementalThread(mid+1, right);
                leftTask.fork();
                int rightResult = rightTask.compute();
                int leftResult = leftTask.join();
                return leftResult + rightResult;
            }
        }
    }




}
