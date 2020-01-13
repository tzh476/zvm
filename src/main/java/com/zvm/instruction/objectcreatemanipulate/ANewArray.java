package com.zvm.instruction.objectcreatemanipulate;

import com.zvm.basestruct.TypeCode;
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

public class ANewArray implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        int classIndex = code.consumeU2();
        int count = operandStack.popInt();
        JObject jArray = anewarray(runTimeEnv, jThread,interpreter, classIndex, javaClass,count);
        operandStack.putJObject(jArray);
    }

    /**
     * 引用类型数组创建
     * @param classIndex
     * @param javaClass
     * @param count
     * @return
     */
    private JObject anewarray(RunTimeEnv runTimeEnv, JThread jThread, Interpreter interpreter, int classIndex, JavaClass javaClass, int count) {
        ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
        ConstantClass constant_class = (ConstantClass) constant_bases[classIndex - 1];
        ConstantUtf8 classNameUtf8 = (ConstantUtf8)constant_bases[TypeUtils.byteArr2Int(constant_class.nameIndex.u2) - 1];
        String className = TypeUtils.u12String(classNameUtf8.bytes);
        JavaClass curClass = runTimeEnv.methodArea.findClass(className);
        if(curClass == null){
            curClass = runTimeEnv.methodArea.loadClass(className);
            runTimeEnv.methodArea.linkClass(className);
            //runTimeEnv.methodArea.initClass(className, interpreter);
        }

        className = JavaClass.classNameToArrayClassName(className);

        JObject jObject = runTimeEnv.javaHeap.createJArray(curClass, TypeCode.T_EXTRA_OBJECT, count, runTimeEnv, jThread);

        return jObject;
    }
}
