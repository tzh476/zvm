package com.zvm.runtime;

/**
 * 运行时，表示整个线程的栈，可能存在一连串的方法帧
 */
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

    public JavaFrame pop(){
        JavaFrame popFrame = topFrame;
        topFrame = topFrame.lowerFrame;
        popFrame.lowerFrame = null;
        size --;
        return popFrame;
    }
}
