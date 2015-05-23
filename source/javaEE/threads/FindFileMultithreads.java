package javaEE.threads;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by Таня on 22.05.2015.
 */
public class FindFileMultithreads implements Runnable {

    private File point;
    private String path;
    private String key;
    private Bank bank;

    public FindFileMultithreads(String path, String key, Bank bank) {
        this.point = new File(path);
        this.key = key;
        this.bank = bank;
        this.path = path;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            if (point.isFile()) {
                if (point.getName().contains(key))
                    try {
                        bank.saveResult(point.getAbsolutePath() + "\n" + " ");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            } else {
                File[] children = point.listFiles();
                if(children != null) {
                    for (File f : children) {
                        Runnable r = new FindFileMultithreads(f.getAbsolutePath(), key, bank);
                        Thread t = new Thread(r);
                        t.start();
                        try {
                            Thread.currentThread().sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            Thread.currentThread().interrupt();
        }
    }
}
