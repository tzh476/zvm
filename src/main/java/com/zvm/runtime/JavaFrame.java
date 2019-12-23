package com.zvm.runtime;

import com.zvm.utils.TypeUtils;
import com.zvm.basestruct.u2;

/**
 * 运行时，表示一个方法帧，lowerFrame指向调用者
 */
public class JavaFrame {
    public JavaFrame lowerFrame;/*保留调用当前帧的帧*/

    public Integer max_stack;
    public Integer max_locals;

    public OperandStack operandStack;
    public LocalVars localVars;

    public JavaFrame(u2 max_stack, u2 max_locals){
        this.max_stack = TypeUtils.byteArr2Int(max_stack.u2);
        this.max_locals = TypeUtils.byteArr2Int(max_locals.u2);
        operandStack = new OperandStack(this.max_stack);
        localVars = new LocalVars(this.max_locals);
    }

    public void putInt(int value){
        operandStack.putInt(value);
    }
}
