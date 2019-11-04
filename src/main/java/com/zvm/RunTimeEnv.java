package com.zvm;

/**
 * zvm运行环境
 */
public class RunTimeEnv {

    public JavaHeap javaHeap;
    public MethodArea methodArea;

    ClassFile[] classFiles ;

    public RunTimeEnv(){
        methodArea = new MethodArea();
        javaHeap = new JavaHeap();

    }

}
