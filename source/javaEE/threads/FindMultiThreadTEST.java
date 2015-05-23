package javaEE.threads;

/**
 * Created by Таня on 22.05.2015.
 */
public class FindMultiThreadTEST {
    public static void main(String[] args) {
        Bank bank= new Bank("temp/result1.txt");
        Runnable r = new FindFileMultithreads("E:/","tex2dparticle", bank);
        Thread t = new Thread(r);
        t.start();
    }
}
