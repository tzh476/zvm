package com.zvm.runtime;


import com.zvm.basestruct.U2;


public class JThread {

    Integer pc;
    ThreadStack threadStack;


    public void pushFrame(U2 maxStack, U2 maxLocals){
        if(threadStack == null){
            threadStack = new ThreadStack();
        }
        threadStack.put(new JavaFrame(maxStack, maxLocals));
    }

    public JavaFrame popFrame(){
        return threadStack.pop();
    }

    public JavaFrame getTopFrame(){
        JavaFrame topFrame = threadStack.topFrame;
        return topFrame;
    }

    public ThreadStack getStack(){
        return threadStack;
    }
}
