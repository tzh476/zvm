package com.zvm.instruction.methodinvocation;

import com.zvm.classfile.MethodInfo;
import com.zvm.classfile.constantpool.ConstantBase;
import com.zvm.instruction.Instruction;
import com.zvm.interpreter.*;
import com.zvm.jnative.NativeUtils;
import com.zvm.runtime.*;

public class InvokeNative implements Instruction {

    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        invokeNative(runTimeEnv, jThread, javaClass, callSite);
    }

    /**
     * 调用native方法,暂时只支持arraycopy
     * @param javaClass
     * @param callSite
     */
    private void invokeNative(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite) {
        Ref methodRef = new Ref();
        methodRef.refName = javaClass.constantUtf8Index2String(callSite.nameIndex);
        methodRef.descriptorName = javaClass.constantUtf8Index2String(callSite.descriptorIndex);
        methodRef.className = javaClass.classPath;
        if(!NativeUtils.hasNativeMethod(methodRef)){
            return;
        }
        JavaFrame javaFrame = jThread.getTopFrame();
        NativeUtils.executeMethod(runTimeEnv, javaFrame, methodRef);
    }
}
