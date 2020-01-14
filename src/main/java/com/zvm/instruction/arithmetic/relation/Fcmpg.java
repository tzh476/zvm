package com.zvm.instruction.arithmetic.relation;

import com.zvm.instruction.Instruction;
import com.zvm.interpreter.CallSite;
import com.zvm.interpreter.CodeUtils;
import com.zvm.interpreter.Interpreter;
import com.zvm.runtime.*;

/**
 * fcmpg 和 fcmpl 指令之间的差别仅仅是当比价参数中出现 NaN 值时的处理方
 * 式而已
 */
public class Fcmpg implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        /*未考虑出现NaN的情况*/
        float var1 = operandStack.popFloat();
        float var0 = operandStack.popFloat();
        int cmpRes = 0;
        if(var0 > var1){
            cmpRes = 1;
        }else if(var0 < var1){
            cmpRes = -1;
        }
        operandStack.putInt(cmpRes);
    }
}
