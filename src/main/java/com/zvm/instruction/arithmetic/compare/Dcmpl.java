package com.zvm.instruction.arithmetic.compare;

import com.zvm.instruction.Instruction;
import com.zvm.interpreter.CallSite;
import com.zvm.interpreter.CodeUtils;
import com.zvm.interpreter.Interpreter;
import com.zvm.runtime.*;

public class Dcmpl implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        /*未考虑出现NaN的情况*/
        double var1 = operandStack.popDouble();
        double var0 = operandStack.popDouble();
        int cmpRes = 0;
        if(var0 > var1){
            cmpRes = 1;
        }else if(var0 < var1){
            cmpRes = -1;
        }
        operandStack.putInt(cmpRes);
    }
}
