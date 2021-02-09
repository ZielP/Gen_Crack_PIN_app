package com.example.crackthepin;

import static java.security.MessageDigest.getInstance;
import static java.util.stream.IntStream.range;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Md5Hash {
    public static String getMd5(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(message.getBytes());
            BigInteger representation = new BigInteger(1, messageDigest);

            String hashText = representation.toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }

            return hashText;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String crack(String hash) {
        for (int i = 0; i < 100000; i++) {
            String value = String.valueOf(i);

            if (value.length() < 5) {
                for (int difference = 5 - value.length(); difference > 0; difference--)
                    value = "0" + value;
            }

            if (getMd5(value).equals(hash)) return value;
        }
        return null;
    }
}
