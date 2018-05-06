package com.gungoren.hw1;

public class Test {
    public static void main(String[] args) throws Exception {
        String process = EncryptSeq.process();
        String process1 = EncryptParalel.process();
        String process2 = EncryptWithThread.process();
        System.out.println(process.equals(process1));
        System.out.println(process.equals(process2));
    }
}
