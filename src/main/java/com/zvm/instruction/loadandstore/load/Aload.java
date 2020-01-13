package com.zvm.instruction.loadandstore.load;

import com.zvm.instruction.Instruction;
import com.zvm.interpreter.CallSite;
import com.zvm.interpreter.CodeUtils;
import com.zvm.interpreter.Interpreter;
import com.zvm.runtime.*;
import com.zvm.runtime.struct.JObject;
import com.zvm.utils.TypeUtils;

public class Aload implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        int index = TypeUtils.byte2Int(code.consumeU1());
        JObject jObject = localVars.getJObject(index);
        operandStack.putJObject(jObject);
    }
}
