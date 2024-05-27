package concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        progress.interrupt();
    }

    @Override
    public void run() {
        var process = new char[] {'-', '\\', '|', '/'};
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (char c : process) {
                    System.out.print("\r load: " + c);
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
