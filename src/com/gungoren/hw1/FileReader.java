package com.gungoren.hw1;

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
        return "89f468a10c5a522d4a09d881fdafd54c50aae725a831b920bad9c4913ce5fd40";
    }
}
