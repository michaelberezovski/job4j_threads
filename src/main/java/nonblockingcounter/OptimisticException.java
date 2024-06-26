package nonblockingcounter;

public class OptimisticException extends Exception {

    public OptimisticException(String message) {
        super(message);
    }
}
