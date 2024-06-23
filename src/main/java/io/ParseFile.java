package io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {

    public File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() {
        return content(data -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return content(data -> data < 128);
    }

    private synchronized String content(Predicate<Character> filter) {
        StringBuilder builder = new StringBuilder();
        try (InputStream input = new FileInputStream(file)) {
            int data;
            while ((data = input.read()) != -1) {
                if (filter.test((char) data)) {
                    builder.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
