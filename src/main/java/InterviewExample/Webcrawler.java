import java.util.*;
import java.util.concurrent.*;

public class WebCrawler {

    private final ExecutorService executor;
    private final BlockingQueue<String> urlQueue;
    private final Set<String> visited;
    private final int maxThreads;

    public WebCrawler(int maxThreads) {
        this.maxThreads = maxThreads;
        this.executor = Executors.newFixedThreadPool(maxThreads);
        this.urlQueue = new LinkedBlockingQueue<>();
        this.visited = ConcurrentHashMap.newKeySet();
    }

    public void startCrawling(String startUrl) throws InterruptedException {
        urlQueue.put(startUrl);
        visited.add(startUrl);

        for (int i = 0; i < maxThreads; i++) {
            executor.submit(this::crawl);
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

    private void crawl() {
        try {
            while (true) {
                String url = urlQueue.poll(5, TimeUnit.SECONDS);
                if (url == null) return; // no more work

                System.out.println(Thread.currentThread().getName() +
                        " crawling " + url);

                List<String> links = fetchLinks(url);

                for (String link : links) {
                    if (visited.add(link)) { // atomic check + add
                        urlQueue.put(link);
                    }
                }
            }
        } catch (InterruptedException ignored) {}
    }

    // Mock page fetch
    private List<String> fetchLinks(String url) {
        return List.of(
                url + "/a",
                url + "/b"
        );
    }
}
