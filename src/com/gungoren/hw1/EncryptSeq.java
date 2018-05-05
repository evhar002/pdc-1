package com.gungoren.hw1;

import edu.rit.crypto.blockcipher.AES256Cipher;
import edu.rit.util.Hex;

public class EncryptSeq {

    public static void main(String[] args) {
        process();
    }

    public static void process() {
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
        //System.out.println(Hex.toString(msg));
        long start = System.currentTimeMillis();
        for (int i = 0; i < msg.length / 16; i++) {
            byte[] block = new byte[16];
            System.arraycopy(msg, i * block.length, block, 0, block.length);
            cipher.encrypt(block);
            cipher.erase();
            cipher.setKey(key);
            System.arraycopy(block, 0, cipherText, i * block.length, block.length);
        }
        //System.out.println(Hex.toString(cipherText));
        System.out.println(EncryptSeq.class.getSimpleName() + " complete in " + (System.currentTimeMillis() - start));
        return;
    }
}
