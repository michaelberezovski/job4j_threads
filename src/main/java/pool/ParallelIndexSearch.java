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

    private int linearSearch(T[] tArray, T element) {
        for (int i = 0; i < tArray.length; i++) {
            if (tArray[i] == element) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Integer compute() {
        if (array.length <= 10) {
            return linearSearch(array, elem);
        }
        int middle = (from + to) / 2;
        ParallelIndexSearch leftSort = new ParallelIndexSearch(array, elem, from, middle);
        ParallelIndexSearch rightSort = new ParallelIndexSearch(array, elem, middle + 1, to)
        return null;
    }

    public int[] sort(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearch(array, elem, 0, array.length - 1));
    }
}
