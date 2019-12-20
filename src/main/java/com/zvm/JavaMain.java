package com.zvm;

import com.zvm.runtime.RunTimeEnv;

public class JavaMain {
    public static void main(String[] args){
        Cmd.processCmd(args);
        RunTimeEnv zvmEnv = new RunTimeEnv();
        //String curClassNamePath = Cmd.curClassNamePath;
        String curClassName = Cmd.curClassName;
        System.out.println("file path : " + curClassName);

        ZVM zvm = new ZVM(zvmEnv);

        zvm.registerNatives();

        zvm.callMain("main", "([Ljava/lang/String;)V", curClassName);
    }


}
