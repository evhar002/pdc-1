package com.gungoren.hw1.t1;

import edu.rit.crypto.blockcipher.AES256Cipher;
import edu.rit.util.Hex;

public class Encrypt {

    public static void main(String[] args) {
        String message = FileReader.readFile();
        byte[] key = Hex.toByteArray(FileReader.getKey());

        byte[] msg = message.getBytes();

        AES256Cipher cipher = new AES256Cipher(key);

        if (msg.length % 16 != 0) {
            byte[] temp = new byte[(msg.length / 16 + 1) * 16];
            System.arraycopy(msg, 0, temp, 0, msg.length);
            msg = temp;
        }
        byte[] cipherText = new byte[msg.length];
        System.out.println(Hex.toString(msg));
        long start = System.currentTimeMillis();
        for (int i = 0; i < msg.length / 16; i++) {
            byte[] block = new byte[16];
            System.arraycopy(msg, i * block.length, block, 0, block.length);
            cipher.encrypt(block);
            cipher.erase();
            cipher.setKey(key);
            System.arraycopy(block, 0, cipherText, i * block.length, block.length);
        }
        System.out.println(Hex.toString(cipherText));
        System.out.println("Complete in " + (System.currentTimeMillis() - start));
    }

    private static void usage() {
        System.out.println("Program should work with 3 parameters");
        System.out.println("\t Encrypt.class message key n");
        System.exit(1);
    }
}
