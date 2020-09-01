package com.bjgoodwill.jhecis;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppUtis {
    public final static String BASE_URL = "http://192.168.137.1:8090/";
    public final static String TAG = "liyahui";

    public static String md5(String string) {
        if (string.isEmpty()) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
