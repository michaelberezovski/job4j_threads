package io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFileFunc {

    public String content(File file, Predicate<Character> filter) throws IOException {
        String output;
        try (InputStream input = new FileInputStream(file)) {
            output = "";
            int data;
            while ((data = input.read()) > 0) {
                output += (char) data;
            }
        }
        return output;
    }

    public String getContent(File file) throws IOException {
        String output;
        try (InputStream input = new FileInputStream(file)) {
            output = "";
            int data;
            while ((data = input.read()) > 0) {
                output += (char) data;
            }
        }
        return output;
    }

    public String getContentWithoutUnicode(File file) throws IOException {
        String output;
        try (InputStream input = new FileInputStream(file)) {
            output = "";
            int data;
            while ((data = input.read()) > 0) {
                if (data < 0x80) {
                    output += (char) data;
                }
            }
        }
        return output;
    }

    public void saveContent(String content, File file) throws IOException {
        try (OutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i++) {
                o.write(content.charAt(i));
            }
        }
    }
}
