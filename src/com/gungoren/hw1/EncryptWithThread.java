package com.gungoren.hw1;

import edu.rit.crypto.blockcipher.AES256Cipher;
import edu.rit.util.Hex;

public class EncryptWithThread extends Thread{

    private static byte[] msg;
    private static byte[] cipherText;
    private static AES256Cipher cipher;
    private static byte[] key;

    private int startPos;
    private int endPos;

    public EncryptWithThread(int startPos, int endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public static void main(String[] args) {
        process();
    }

    public static void process() {
        String message = FileReader.readFile();
        key = Hex.toByteArray(FileReader.getKey());
        cipher = new AES256Cipher(key);
        msg = message.getBytes();

        if (msg.length % 16 != 0) {
            byte[] temp = new byte[(msg.length / 16 + 1) * 16];
            System.arraycopy(msg, 0, temp, 0, msg.length);
            msg = temp;
        }
        cipherText = new byte[msg.length];
        //System.out.println(Hex.toString(msg));
        long start = System.currentTimeMillis();
        int blockCount = msg.length / 16;
        int threadCount = 2;
        int subThreadCount = blockCount / threadCount;

        EncryptWithThread[] threads = new EncryptWithThread[threadCount];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new EncryptWithThread(i, ( i + 1 ) * subThreadCount);
        }

        for (EncryptWithThread thread : threads) {
            thread.start();
        }

        boolean complete = false;
        while (!complete) {
            complete = true;
            for (EncryptWithThread thread : threads) {
                if (thread.isAlive())
                    complete = false;
            }
            try{
                Thread.sleep(20);
            }catch(Exception e)
            {            }
        }

        //System.out.println(Hex.toString(cipherText));
        System.out.println(EncryptWithThread.class.getSimpleName() + " complete in " + (System.currentTimeMillis() - start));
        return;
    }

    @Override
    public void run() {
        for (int i = startPos; i < endPos; i++) {
            byte[] block = new byte[16];
            System.arraycopy(msg, i * block.length, block, 0, block.length);
            cipher.encrypt(block);
            System.arraycopy(block, 0, cipherText, i * block.length, block.length);
        }
        //System.out.println(startPos + " start " + start + " completed in " + (System.currentTimeMillis() - start));
    }
}
