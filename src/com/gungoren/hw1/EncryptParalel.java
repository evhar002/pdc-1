package com.gungoren.hw1;

import edu.rit.crypto.blockcipher.AES256Cipher;
import edu.rit.pj.*;
import edu.rit.util.Hex;

public class EncryptParalel{

    private static byte[] msg;
    private static byte[] cipherText;

    public static void main(String[] args) throws Exception {
        process();
    }

    public static String process() throws Exception {
        String message = FileReader.readFile();
        byte[] key = Hex.toByteArray(FileReader.getKey());
        msg = message.getBytes();

        if (msg.length % 16 != 0) {
            byte[] temp = new byte[(msg.length / 16 + 1) * 16];
            System.arraycopy(msg, 0, temp, 0, msg.length);
            msg = temp;
        }
        cipherText = new byte[msg.length];
        //System.out.println(Hex.toString(msg));
        long startMillis = System.currentTimeMillis();

        int blockCount = msg.length / 16;
        int threadCount = 64;
        int subThreadCount = blockCount % threadCount == 0 ? blockCount / threadCount : blockCount / threadCount + 1;
        ParallelTeam parallelTeam = new ParallelTeam(threadCount);
        parallelTeam.execute(new ParallelRegion() {
            @Override
            public void run(){
                AES256Cipher aes256Cipher = new AES256Cipher(key);
                int start = getThreadIndex() * subThreadCount;
                int end = Math.min((getThreadIndex() + 1) * subThreadCount, blockCount);
                long s = System.currentTimeMillis();
                byte[] block = new byte[16];
                for (int i = start; i < end; i++) {
                    System.arraycopy(msg, i * block.length, block, 0, block.length);
                    aes256Cipher.encrypt(block);
                    System.arraycopy(block, 0, cipherText, i * block.length, block.length);
                }
                //System.out.println(" start " + (s - startMillis) + " completed in " + (System.currentTimeMillis() - s));
            }
        });

        //System.out.println(Hex.toString(cipherText));
        System.out.println(EncryptParalel.class.getSimpleName() + " complete in " + (System.currentTimeMillis() - startMillis));
        return Hex.toString(cipherText);
    }
}
