package com.zvm.instruction.arithmetic.unary;

import com.zvm.instruction.Instruction;
import com.zvm.interpreter.CallSite;
import com.zvm.interpreter.CodeUtils;
import com.zvm.interpreter.Interpreter;
import com.zvm.runtime.*;

/**
 * 一元运算符类别
 */
public class Iinc implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        byte localVarIndex = code.consumeU1();
        byte constValue = code.consumeU1();
        int localVar = localVars.getIntByIndex(localVarIndex);
        localVars.putIntByIndex(localVarIndex, localVar + constValue);
    }
}
