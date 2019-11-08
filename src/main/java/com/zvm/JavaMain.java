package com.zvm;

public class JavaMain {
    public static void main(String[] args){
        Cmd.processCmd(args);
        RunTimeEnv zvmEnv = new RunTimeEnv();
        String curClassNamePath = Cmd.curClassNamePath;
        System.out.println("file path : " + curClassNamePath);

        ZVM zvm = new ZVM(zvmEnv);

        zvm.callMain("main", "([Ljava/lang/String;)V", curClassNamePath);


    }


}
