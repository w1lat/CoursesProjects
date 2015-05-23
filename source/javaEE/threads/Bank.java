package javaEE.threads;

import java.io.*;

/**
 * Created by Таня on 22.05.2015.
 */
public class Bank {
    private File file;
    private String path;

    public Bank(String path) {
        this.path = path;
        file = new File(path);
        try{
            Writer writer = new FileWriter(file);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public synchronized void saveResult(String s) throws IOException {
        Writer writer = new FileWriter(file, true);
        writer.write(s);
        writer.flush();
        writer.close();
    }
}
