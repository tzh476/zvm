package com.zvm.instruction.objectcreatemanipulate;

import com.zvm.instruction.Instruction;
import com.zvm.interpreter.CallSite;
import com.zvm.interpreter.CodeUtils;
import com.zvm.interpreter.Interpreter;
import com.zvm.runtime.*;
import com.zvm.runtime.struct.JObject;

public class NewArray implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        int arrayType = code.consumeU1();
        int count = operandStack.popInt();
        JObject jArray = newarray(runTimeEnv, jThread, arrayType,count);
        operandStack.putJObject(jArray);
    }

    private JObject newarray( RunTimeEnv runTimeEnv, JThread jThread, int arrayType, int count) {
        JavaClass arrayClass = PrimitiveArrayUtils.getPrimitiveArrayClass(runTimeEnv, arrayType, count);

        return runTimeEnv.javaHeap.createJArray(arrayClass,arrayType, count, runTimeEnv, jThread);
    }
}
