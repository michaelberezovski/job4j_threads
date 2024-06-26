package nonblockingcounter;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    private void setCount() {
        count.set(0);
    }

    public void increment() {
        int current;
        int upcoming;
        do {
            current = count.get();
            upcoming = current + 1;
        } while (count.compareAndSet(current, upcoming));
    }

    public int get() {
        return count.get();
    }
}
