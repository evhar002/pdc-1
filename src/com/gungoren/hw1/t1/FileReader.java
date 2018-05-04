package com.gungoren.hw1.t1;

import java.io.*;

public class FileReader {

    public static String readFile() {
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(new File(FileReader.class.getResource("plaintext").getPath())));
            String text = br.readLine();
            return text;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getKey () {
        return "Aa123456Aa123456Aa123456Aa123456Aa123456Aa123456Aa123456Aa123456";
    }
}
