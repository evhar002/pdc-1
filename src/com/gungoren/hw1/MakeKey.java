package com.gungoren.hw1;

import edu.rit.util.Hex;

import java.security.SecureRandom;


public class MakeKey {

    public static void main(String[] args){
        System.out.println(Hex.toString(SecureRandom.getSeed(32)));
    }
}
