package com.zvm.instruction.objectcreatemanipulate;

import com.zvm.classfile.constantpool.ConstantBase;
import com.zvm.classfile.constantpool.ConstantClass;
import com.zvm.classfile.constantpool.ConstantUtf8;
import com.zvm.instruction.Instruction;
import com.zvm.interpreter.CallSite;
import com.zvm.interpreter.CodeUtils;
import com.zvm.interpreter.Interpreter;
import com.zvm.runtime.*;
import com.zvm.runtime.struct.JObject;
import com.zvm.utils.TypeUtils;

public class New implements Instruction {

    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        int newIndex = code.consumeU2();
        ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
        ConstantBase constant_base = constant_bases[newIndex - 1];
        int name_index = TypeUtils.byteArr2Int(((ConstantClass) constant_base).nameIndex.u2);
        ConstantUtf8 constant_utf8 = (ConstantUtf8) constant_bases[name_index - 1];
        JObject jObject = execNew(runTimeEnv, jThread, interpreter, javaClass,constant_utf8);
        operandStack.putJObject(jObject);
    }

    private JObject execNew(RunTimeEnv runTimeEnv, JThread jThread, Interpreter interpreter, JavaClass javaClass, ConstantUtf8 constant_utf8) {
        String className = TypeUtils.u12String(constant_utf8.bytes);
        if(runTimeEnv.methodArea.findClass(className) == null){
            runTimeEnv.methodArea.loadClass(className);
            runTimeEnv.methodArea.linkClass(className);
            runTimeEnv.methodArea.initClass(className, interpreter);
        }
        return runTimeEnv.javaHeap.createJObject(runTimeEnv.methodArea.findClass(className), runTimeEnv, jThread);
    }
}
