package com.zvm.instruction.loadandstore.constant;

import com.zvm.basestruct.U4;
import com.zvm.classfile.constantpool.ConstantBase;
import com.zvm.classfile.constantpool.ConstantDouble;
import com.zvm.classfile.constantpool.ConstantLong;
import com.zvm.instruction.Instruction;
import com.zvm.interpreter.CallSite;
import com.zvm.interpreter.CodeUtils;
import com.zvm.interpreter.Interpreter;
import com.zvm.runtime.*;
import com.zvm.utils.TypeUtils;

/**
 *
 */
public class Ldc2_W implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        /*从常量池取值到frame顶*/
        short cpIndex = code.consumeU2();
        ConstantBase constant_base = javaClass.getClassFile().constantPool.cpInfo[cpIndex - 1];
        if(constant_base instanceof ConstantLong){
            U4 highBytes = ((ConstantLong) constant_base).highBytes;
            U4 lowBytes = ((ConstantLong) constant_base).lowBytes;
            operandStack.putLong(TypeUtils.byteArr2Int(highBytes.u4), TypeUtils.byteArr2Int(lowBytes.u4));
        }else if(constant_base instanceof ConstantDouble){
            U4 highBytes = ((ConstantDouble) constant_base).highBytes;
            U4 lowBytes = ((ConstantDouble) constant_base).lowBytes;
            byte[] doubleByte = TypeUtils.appendByte(highBytes.u4, lowBytes.u4);
            double ldcValue = TypeUtils.byteArr2Double(doubleByte);
            operandStack.putDouble(ldcValue);
        }
    }
}
