package com.zvm;

public class TypeUtils {
    /**
     * byte[]转int
     * byte的范围是-128至127，此处视为无符号，0-255
     * @param bytes
     * @return
     */
    static Integer byte2Int(byte[] bytes) {
        Integer size = bytes.length;
        Integer res = 0xff & bytes[0];
        for (Integer i = 1; i < size; i++) {
            res = ((res << 8) + 0xff) & bytes[i];
        }
        return res;
    }

    /**
     * byte[]转String
     *
     * @param bytes
     * @return
     */
    static String byte2String(byte[] bytes) {
        Integer size = bytes.length;
        char[] res = new char[size];
        for (Integer i = 0; i < size; i++) {
            res[i] = (char) bytes[i];
        }
        return String.valueOf(res);
    }

    /**
     * u1[] to String
     *
     * @param bytes
     * @return
     */
    static String u12String(u1[] bytes) {
        Integer size = bytes.length;
        char[] res = new char[size];
        for (Integer i = 0; i < size; i++) {
            res[i] = (char) bytes[i].u1[0];
        }
        return String.valueOf(res);
    }

    static boolean compare(String s, String d){
        if(s == null && d == null){
            return true;
        }
        if(s == null || d == null){
            return false;
        }
        Integer len0 = s.length();
        Integer len1 = d.length();
        if(len0 != len1){
            return false;
        }

        char[] arr0 = s.toCharArray();
        char[] arr1 = d.toCharArray();
        for(int i = 0; i < len0; i++){
            if(arr0[i] != arr1[i]){
                return false;
            }
        }
        return true;
    }
}