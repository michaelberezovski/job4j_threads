package thread;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileForDownload;

    public Wget(String url, int speed, String fileForDownload) {
        this.url = url;
        this.speed = speed;
        this.fileForDownload = fileForDownload;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(fileForDownload)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[1024];
            int bytesRead;
            long allBytes = 0;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                output.write(dataBuffer, 0, bytesRead);
                allBytes += bytesRead;
                if (allBytes >= speed) {
                    long sleepTime = allBytes / 1000;
                    Thread.sleep(sleepTime);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Wrong number of CLI arguments");
        }
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
            throw new IllegalArgumentException("Speed limit must be lower than a 1000.");
        }
        String fileName = args[2];
        Thread wget = new Thread(new Wget(urlStr, speed, fileName));
        wget.start();
        wget.join();
    }
}
