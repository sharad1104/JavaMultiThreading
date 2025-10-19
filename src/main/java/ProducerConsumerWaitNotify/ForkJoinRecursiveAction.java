package ProducerConsumerWaitNotify;

import java.util.concurrent.*;
public class ForkJoinRecursiveAction {
    private static int [] array = {1,2,3,4,5,6,7,8,9,10};

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(2);
        pool.invoke(new IncrementalThread(0,9));
        for(int i = 0; i < array.length; i++){
            System.out.println(array[i]);
        }
    }

    static class IncrementalThread extends  RecursiveAction {
        int left;
        int right;
        public IncrementalThread(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            if(left==right){
                array[left]++;
            } else {
                int mid = left + (right - left)/2;
                invokeAll(new IncrementalThread(left,mid), new IncrementalThread(mid+1, right));


            }
        }
    }




}
