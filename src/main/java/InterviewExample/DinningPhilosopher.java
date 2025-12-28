package InterviewExample;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Fork {
    private int id;
    private Lock lock;

    public Fork(int id) {
        this.id = id;
        lock = new ReentrantLock(true);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Lock getLock() {
        return lock;
    }
}

class Philosopher implements Runnable {
    private final int id;
    private final Fork leftFork;
    private final Fork rightFork;

    public Philosopher(int id, Fork fork1, Fork fork2) {
        this.id = id;
        this.leftFork = fork1;
        this.rightFork = fork2;
    }

    @Override
    public void run() {
        try {
            while (true) {
                think();
                Fork left = leftFork.getId()<rightFork.getId() ? leftFork : rightFork;
                Fork right = leftFork.getId()<rightFork.getId() ? rightFork : leftFork;

                left.getLock().lock();
                right.getLock().lock();

                eat();

                left.getLock().unlock();
                right.getLock().unlock();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + this.id + " is thinking");
        Thread.sleep(500);
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + this.id + " is eating");
        Thread.sleep(500);
    }
}
public class DinningPhilosopher {

    public static void main(String[] args) {
        int N = 5;
        Fork[] forks = new Fork[N];
        Philosopher[] phils = new Philosopher[N];
        for (int i = 0; i < N; i++) {
            forks[i] = new Fork(i);
        }
        for (int i = 0; i < N; i++) {
            phils[i] = new Philosopher(i, forks[i], forks[(i+1)%N]);
        }
        Thread[] threads = new Thread[N];
        for(int i = 0; i < N; i++){
            threads[i] = new Thread(phils[i]);
            threads[i].start();
        }

    }

}
