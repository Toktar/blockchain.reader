package ru.blockboxchain.reader;

import java.io.*;

public class FileIO {

    public static String open(File file) throws FileNotFoundException {

        String fileContent;
        fileContent = "";

        for (int i = 0; i < 5; i++) {
            fileContent = fileContent + "cur string: " + i + "\n";
        }
        return fileContent;
    }

    public static void save(File file, String content) throws IOException {
        BufferedWriter writer;
        writer = new BufferedWriter(new FileWriter(file));
        writer.write(content);
        writer.flush();
        writer.close();
    }

}
