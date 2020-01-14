package com.zvm.instruction.loadandstore.store;

import com.zvm.instruction.Instruction;
import com.zvm.interpreter.CallSite;
import com.zvm.interpreter.CodeUtils;
import com.zvm.interpreter.Interpreter;
import com.zvm.memory.ArrayFields;
import com.zvm.runtime.*;
import com.zvm.runtime.struct.JObject;

public class Sastore implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        System.out.println("未实现" + this.getClass().getName());
    }
}
