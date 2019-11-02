package com.zvm.runtime;

public class JVMStack {
    public Integer maxSize = 10;
    public Integer size;
    public JavaFrame topFrame;

    public JVMStack(){
        size = 0;
    }

    public void put(JavaFrame javaFrame){
        javaFrame.lowerFrame = topFrame;
        topFrame = javaFrame;
        size ++;
    }


}
