package com.gungoren.hw1.t1;

import edu.rit.crypto.blockcipher.AES256Cipher;
import edu.rit.util.Hex;

public class EncryptWithThread extends Thread{

    private static byte[] msg;
    private static byte[] cipherText;
    private static AES256Cipher cipher;
    private static byte[] key;
    private int startPos;

    public EncryptWithThread(int start) {
        this.startPos = start;
    }

    public static void main(String[] args) {

        String message = FileReader.readFile();
        key = Hex.toByteArray(FileReader.getKey());

        msg = message.getBytes();

        if (msg.length % 16 != 0) {
            byte[] temp = new byte[(msg.length / 16 + 1) * 16];
            System.arraycopy(msg, 0, temp, 0, msg.length);
            msg = temp;
        }
        cipherText = new byte[msg.length];
        System.out.println(Hex.toString(msg));
        long start = System.currentTimeMillis();

        EncryptWithThread[] threads = new EncryptWithThread[msg.length / 16];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new EncryptWithThread(i);
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
                Thread.sleep(100);
            }catch(Exception e)
            {            }
        }

        System.out.println(Hex.toString(cipherText));
        System.out.println("Complete in " + (System.currentTimeMillis() - start));
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        byte[] block = new byte[16];
        System.arraycopy(msg, startPos * block.length, block, 0, block.length);
        AES256Cipher cipher = new AES256Cipher(key);
        cipher.encrypt(block);
        System.arraycopy(block, 0, cipherText, startPos * block.length, block.length);
        //System.out.println(startPos + " start " + start + " completed in " + (System.currentTimeMillis() - start));
    }

    private static void usage() {
        System.out.println("Program should work with 3 parameters");
        System.out.println("\t Encrypt.class message key n");
        System.exit(1);
    }
}
