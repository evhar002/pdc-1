package com.gungoren.hw1.t1;

import edu.rit.crypto.blockcipher.AES256Cipher;
import edu.rit.pj.*;
import edu.rit.util.Hex;

public class EncryptParalel{

    private static byte[] msg;
    private static byte[] cipherText;
    private static AES256Cipher cipher;

    public static void main(String[] args) throws Exception {

        String message = FileReader.readFile();
        byte[] key = Hex.toByteArray(FileReader.getKey());

        msg = message.getBytes();

        if (msg.length % 16 != 0) {
            byte[] temp = new byte[(msg.length / 16 + 1) * 16];
            System.arraycopy(msg, 0, temp, 0, msg.length);
            msg = temp;
        }
        cipherText = new byte[msg.length];
        System.out.println(Hex.toString(msg));
        long start = System.currentTimeMillis();
        cipher = new AES256Cipher(key);

        int threadCount = msg.length / 16;
        ParallelTeam parallelTeam = new ParallelTeam(4);
        parallelTeam.execute(new ParallelRegion() {

            @Override
            public void start() throws Exception {
                System.out.println("parallel region started");
            }

            @Override
            public void run() throws Exception {
                int start = getThreadIndex() * threadCount;
                int end = (getThreadIndex() + 1) * threadCount;
                System.out.println("start "+ start + " end " + end + " idx " + getThreadIndex());
                execute(start, end, new IntegerForLoop() {

                    @Override
                    public void start() throws Exception {
                        System.out.println("started " + getThreadIndex());
                    }

                    @Override
                    public void run(int first, int last) throws Exception {
                        System.out.println("first "+ first + " last " + last + " idx " + getThreadIndex());
                        for (int i = first; i <= last ; i++) {
                            int finalI = i;
                            ordered(new ParallelSection() {
                                @Override
                                public void run() throws Exception {
                                    System.out.println("inner run of ordered " + finalI);
                                }
                            });
                        }
                    }
                });
                System.out.println("nothingg");
                /*int startPos = getThreadIndex();
                long start = System.currentTimeMillis();
                byte[] block = new byte[16];
                System.arraycopy(msg, startPos * block.length, block, 0, block.length);
                cipher.encrypt(block);
                cipher.erase();
                System.arraycopy(block, 0, cipherText, startPos * block.length, block.length);*/
                //System.out.println(startPos + " start " + start + " completed in " + (System.currentTimeMillis() - start));
            }
        });

        System.out.println(Hex.toString(cipherText));
        System.out.println("Complete in " + (System.currentTimeMillis() - start));
    }

    private static void usage() {
        System.out.println("Program should work with 3 parameters");
        System.out.println("\t Encrypt.class message key n");
        System.exit(1);
    }
}
