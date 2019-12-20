package com.zvm.classfile;

import com.zvm.basestruct.u1;
import com.zvm.basestruct.u2;
import com.zvm.basestruct.u4;

public class IOUtils {
    public static byte[] bytecode;
    public static Integer index;
    public static u1 read_u1(){
        u1 res = new u1();
        res.u1 = read( 1);
        index++;
        return res;
    }
    public static u2 read_u2(){
        u2 res = new u2();
        res.u2 = read(2);
        index += 2;
        return res;
    }
    public static u4 read_u4(){
        u4 res = new u4();
        res.u4 = read( 4);
        index += 4;
        return res;
    }

    public static byte[] read(Integer size){
        byte[] bytes = new byte[size];
        for(Integer i = 0; i < size; i++){
            bytes[i] = bytecode[index + i];
        }
        return bytes;
    }

//    public static u1 read_u1(byte[] bytecode, Integer pc){
//        u1 res = null;
//        res.u1 = read(pc, bytecode, 1);
//        return res;
//    }
//    public static u2 read_u2(byte[] bytecode, Integer pc){
//        u2 res = null;
//        res.u2 = read(pc, bytecode, 2);
//        return res;
//    }
//    public static u4 read_u4( byte[] bytecode, Integer pc){
//        u4 res = null;
//        res.u4 = read(pc, bytecode, 4);
//        return res;
//    }
//
//    public static byte[] read(Integer pc, byte[] bytecode, Integer size){
//        byte[] bytes = new byte[size];
//        for(Integer i = 0; i < size; i++){
//            bytes[i] = bytecode[pc + i];
//        }
//        return bytes;
//    }
}
