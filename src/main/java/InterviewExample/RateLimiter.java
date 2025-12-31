package InterviewExample;

public class RateLimiter {
    private int maxTokens;
    private int currentTokens;
    private long lastRefillTime;
    private long refillTimeInMillis;
    private Object lock = new Object();


    public RateLimiter(int maxTokens, int refillTimeInMillis) {
        this.maxTokens = maxTokens;
        this.currentTokens = maxTokens;
        this.refillTimeInMillis = refillTimeInMillis;
        this.lastRefillTime = System.currentTimeMillis();
    }

    public void acquire() throws InterruptedException {
        synchronized (lock) {
            refill();

            while (currentTokens == 0) {
                long waitTime = refillTimeInMillis -
                        (System.currentTimeMillis() - lastRefillTime);
                if (waitTime <= 0) {
                    refill();
                } else {
                    lock.wait(waitTime);
                }
            }

            currentTokens--;
        }

    }

    private void refill() {
        long now = System.currentTimeMillis();
        long elapedTime = now - lastRefillTime;

        if (elapedTime >= refillTimeInMillis) {
            int tokensToAdd = (int) (elapedTime/refillTimeInMillis);
            currentTokens = Math.min(currentTokens + tokensToAdd, maxTokens);
            lastRefillTime = now;
            lock.notifyAll();
        }
    }




}
