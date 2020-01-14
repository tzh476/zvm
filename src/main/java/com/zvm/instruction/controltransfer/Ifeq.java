package com.zvm.instruction.controltransfer;

import com.zvm.instruction.Instruction;
import com.zvm.interpreter.CallSite;
import com.zvm.interpreter.CodeUtils;
import com.zvm.interpreter.Interpreter;
import com.zvm.runtime.*;

public class Ifeq implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        int ifeqValue = operandStack.popInt();
        if(ifeqValue == 0){
            int offset = code.readU2();
            code.pcAddBackOne(offset);
        }else {
            code.pcAdd(2);
        }
    }
}
