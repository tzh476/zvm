package com.zvm.runtime;


import com.zvm.basestruct.u2;


public class JThread {

    Integer pc;
    JVMStack jvmStack;


    public void pushFrame(u2 max_stack, u2 max_locals){
        if(jvmStack == null){
            jvmStack = new JVMStack();
        }
        jvmStack.put(new JavaFrame(max_stack, max_locals));
    }

    public JavaFrame getTopFrame(){

        JavaFrame topFrame = jvmStack.topFrame;
        return topFrame;
    }

    public JVMStack getStack(){
        return jvmStack;
    }
}
