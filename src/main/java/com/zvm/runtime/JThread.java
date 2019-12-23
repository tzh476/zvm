package com.zvm.runtime;


import com.zvm.basestruct.u2;


public class JThread {

    Integer pc;
    ThreadStack threadStack;


    public void pushFrame(u2 max_stack, u2 max_locals){
        if(threadStack == null){
            threadStack = new ThreadStack();
        }
        threadStack.put(new JavaFrame(max_stack, max_locals));
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
