package thread;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
    }

    public static void main(String[] args) throws InterruptedException {
        String urlStr = args[0];
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                System.out.println("The URL is valid and accessible.");
            } else {
                System.out.println("The URL is not valid or cannot be accessed.");
            }
        } catch (IOException e) {
            System.out.println("The URL is not valid: " + e.getMessage());
        }
        int speed = Integer.parseInt(args[1]);
        if (speed > 1000) {
            throw new IllegalArgumentException("Speed must be lower than a 1000.");
        }
        Thread wget = new Thread(new Wget(urlStr, speed));
        wget.start();
        wget.join();
    }
}
