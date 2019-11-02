package com.zvm.runtime;

import com.zvm.TypeUtils;
import com.zvm.basestruct.u2;

public class JavaFrame {
    JavaFrame lowerFrame;/*保留调用当前帧的帧*/

    public static Integer max_stack;
    public static Integer max_locals;

    public OperandStack operandStack;
    public LocalVars localVars;

    public JavaFrame(u2 max_stack, u2 max_locals){
        this.max_stack = TypeUtils.byte2Int(max_stack.u2);
        this.max_locals = TypeUtils.byte2Int(max_locals.u2);
        operandStack = new OperandStack(this.max_stack);
        localVars = new LocalVars(this.max_locals);
    }

    public void putInt(int value){
        operandStack.putInt(value);
    }
}
