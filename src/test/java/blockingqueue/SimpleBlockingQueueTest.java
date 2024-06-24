package blockingqueue;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {
    @Test
    void whenPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> one = new SimpleBlockingQueue<>(5);
    }
}