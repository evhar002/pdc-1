package com.gungoren.hw1.t1;

import edu.rit.util.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

public class MakeKey {

    public static void main(String[] args){
        System.out.println(Hex.toString(SecureRandom.getSeed(32)));
    }
}
