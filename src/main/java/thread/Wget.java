package thread;

import java.io.File;
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
        var file = new File("tmp.xml");
        String url = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[1024];
            int bytesRead;
            double time;
            double speed;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                time = System.nanoTime() - downloadAt;
                speed = (dataBuffer.length / time) * 1000000;
                if (speed > 1000) {
                    long sleepTime = (long) speed / 1000;
                    Thread.sleep(sleepTime);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void createFile(String name) {
        File file = new File("src/main/java/thread/" + name);
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
            throw new IllegalArgumentException("Speed limit must be lower than a 1000.");
        }
        String fileName = args[2];
        Thread wget = new Thread(new Wget(urlStr, speed, fileName));
        wget.start();
        wget.join();
    }
}
