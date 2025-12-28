package InterviewExample;



public class Main {

    static class Resource {
        public static int id;
        public Resource(int id) {
            this.id = id;
        }
    }

    static class Worker implements Runnable {
        private final  Resource resource1;
        private final  Resource resource2;
        public Worker(Resource resource1, Resource resource2) {
            this.resource1 = resource1;
            this.resource2 = resource2;
        }
        @Override
        public void run() {
            synchronized (resource1) {
                System.out.println("Got lock on resource1");
                synchronized (resource2) {
                    System.out.println("Got lock on resource2");
                }
            }
        }
    }



    public static void main(String[] args) throws InterruptedException {
        Resource resource1 = new Resource(1);
        Resource resource2 = new Resource(2);
        Thread t1 = new Thread(new  Worker(resource1, resource2));
        Thread t2 = new Thread(new  Worker(resource2, resource1));
        t1.start();
        t2.start();

        t1.join(1000);
        t2.join(1000);

        System.out.println("If threads are still alive, deadlock occurred.");
        if (t1.isAlive() || t2.isAlive()) {
            System.out.println("Deadlock detected!");
        } else {
            System.out.println("No deadlock.");
        }



    }



}
