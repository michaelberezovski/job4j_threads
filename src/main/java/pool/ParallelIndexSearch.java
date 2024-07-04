package pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T elem;
    private final int from;
    private final int to;

    public ParallelIndexSearch(T[] array, T elem, int from, int to) {
        this.array = array;
        this.elem = elem;
        this.from = from;
        this.to = to;
    }

    private int linearSearch() {
        for (int i = from; i <= to; i++) {
            if (elem.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Integer compute() {
        if (to - from + 1 <= 10) {
            return linearSearch();
        }
        int middle = (from + to) / 2;
        ParallelIndexSearch<T> leftSearch = new ParallelIndexSearch<>(array, elem, from, middle);
        ParallelIndexSearch<T> rightSearch = new ParallelIndexSearch<>(array, elem, middle + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        int leftIndex = leftSearch.join();
        int rightIndex = rightSearch.join();
        return null;
    }

    public static <T> int search(T[] array, T elem) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearch<>(array, elem, 0, array.length - 1));
    }
}
