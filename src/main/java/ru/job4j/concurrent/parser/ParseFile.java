package ru.job4j.concurrent.parser;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Integer> predicate) throws IOException {
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            String output = "";
            int data;
            while ((data = i.read()) > 0) {
                if (predicate.test(data)) {
                    output += (char) data;
                }
            }
            return output;
        }
    }

    public String getAllContent() throws IOException {
        return getContent(data -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent(data -> data < 0x80);
    }
}