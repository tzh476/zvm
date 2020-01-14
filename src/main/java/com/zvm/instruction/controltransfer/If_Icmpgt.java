package com.zvm.instruction.controltransfer;

import com.zvm.instruction.Instruction;
import com.zvm.interpreter.CallSite;
import com.zvm.interpreter.CodeUtils;
import com.zvm.interpreter.Interpreter;
import com.zvm.runtime.*;

public class If_Icmpgt implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        int var1 = operandStack.popInt();
        int var0 = operandStack.popInt();
        short offset = code.readU2();
        if(var0 > var1){
            /*分支*/
            code.pcAddBackOne(offset);
        }else {
            code.pcAdd(2);
        }
    }
}
