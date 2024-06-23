package io;

import java.io.*;

public class SaveFile {
    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content, File file) {
        try (OutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i++) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}