package com.zvm;

public class JavaMain {
    public static void main(String[] args){
        String path = args[0];
        RunTimeEnv zvmEnv = new RunTimeEnv();
        System.out.println("file path : " + path);

        ZVM zvm = new ZVM(zvmEnv);

        zvm.callMain("main", "([Ljava/lang/String;)V", path);


    }
}
