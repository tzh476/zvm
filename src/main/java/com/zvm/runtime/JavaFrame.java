package com.zvm.runtime;

import com.zvm.utils.TypeUtils;
import com.zvm.basestruct.U2;

/**
 * 运行时，表示一个方法帧，lowerFrame指向调用者
 */
public class JavaFrame {
    /** 保留调用当前帧的帧*/
    public JavaFrame lowerFrame;

    public Integer maxStack;
    public Integer maxLocals;

    public OperandStack operandStack;
    public LocalVars localVars;

    public JavaFrame(U2 maxStack, U2 maxLocals){
        this.maxStack = TypeUtils.byteArr2Int(maxStack.u2);
        this.maxLocals = TypeUtils.byteArr2Int(maxLocals.u2);
        operandStack = new OperandStack(this.maxStack);
        localVars = new LocalVars(this.maxLocals);
    }

}
