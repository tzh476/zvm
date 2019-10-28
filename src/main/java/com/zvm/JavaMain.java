package com.zvm;

public class JavaMain {
    public static void main(String[] args){
        String path = args[0];
        Zvm zvm = new Zvm();
        System.out.println("file path : " + path);
        zvm.readBytecode2ClassFile(path);
    }
}
