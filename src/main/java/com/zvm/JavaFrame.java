package com.zvm;

public class JavaFrame {
    public static Integer max_stack;
    public static Integer max_locals;

    public JType[] stack;
    public JType[] locals;

    public void pushFrame(u2 max_stack, u2 max_locals){
        this.max_stack = TypeUtils.byte2Int(max_stack.u2);
        this.max_locals = TypeUtils.byte2Int(max_locals.u2);
        stack = new JType[this.max_stack];
        Integer size = this.max_stack;
        for(Integer i = 0; i < size; i++){
            stack[i] = new JType();
        }
        size = this.max_locals;
        for(Integer i = 0; i < size; i++){
            locals[i] = new JType();
        }
    }

    public void push(){

    }
}
