package com.zvm;

/**
 * zvm运行环境
 */
public class RunTimeEnv {

    public JavaHeap javaHeap;
    public MethodArea methodArea;

    public RunTimeEnv(){
        methodArea = new MethodArea();
        javaHeap = new JavaHeap();

    }

}
