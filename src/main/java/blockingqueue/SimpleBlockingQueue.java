package blockingqueue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final Object monitor = this;

    private final int limit;

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == limit) {
            monitor.wait();
        }
        queue.offer(value);
        monitor.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        T element;
        while (queue.isEmpty()) {
            monitor.wait();
        }
        element = queue.poll();
        monitor.notifyAll();
        return element;
    }
}
