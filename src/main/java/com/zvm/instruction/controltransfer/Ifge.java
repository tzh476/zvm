package com.zvm.instruction.controltransfer;

import com.zvm.instruction.Instruction;
import com.zvm.interpreter.CallSite;
import com.zvm.interpreter.CodeUtils;
import com.zvm.interpreter.Interpreter;
import com.zvm.runtime.*;

public class Ifge implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        int var0 = operandStack.popInt();
        short offset = code.readU2();
        if(var0 >= 0){
            code.pcAddBackOne(offset);
        }else {
            code.pcAdd(2);
        }
    }
}
