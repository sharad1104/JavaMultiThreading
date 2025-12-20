import java.util.LinkedList;
import java.util.Queue;

class BoundedBlockingQueue {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity;

    public BoundedBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void enqueue(int item) throws InterruptedException {
        while (queue.size() == capacity) {
            wait(); // wait until space becomes available
        }

        queue.add(item);
        notifyAll(); // notify waiting consumers
    }

    public synchronized int dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // wait until an item is available
        }

        int item = queue.poll();
        notifyAll(); // notify waiting producers
        return item;
    }

    public synchronized int size() {
        return queue.size();
    }
}
