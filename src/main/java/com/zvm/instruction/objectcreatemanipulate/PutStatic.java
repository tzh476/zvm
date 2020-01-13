package com.zvm.instruction.objectcreatemanipulate;

import com.zvm.classfile.FieldInfo;
import com.zvm.classfile.constantpool.ConstantBase;
import com.zvm.instruction.Instruction;
import com.zvm.interpreter.CallSite;
import com.zvm.interpreter.CodeUtils;
import com.zvm.interpreter.Interpreter;
import com.zvm.interpreter.Ref;
import com.zvm.runtime.*;
import com.zvm.runtime.struct.JObject;

public class PutStatic implements Instruction {

    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        short fieldCpIndex = code.consumeU2();
        ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
        ConstantBase constant_fieldref = constant_bases[fieldCpIndex - 1];
        putStaticField(jThread, javaClass,constant_fieldref);
    }

    private void putStaticField(JThread jThread, JavaClass javaClass, ConstantBase constant_fieldref) {
        OperandStack operandStack = jThread.getTopFrame().operandStack;
        Ref fieldRef = JavaClass.processRef(javaClass, constant_fieldref);
        FieldInfo field_info = javaClass.findField(fieldRef.refName,fieldRef.descriptorName);
        field_info.javaClass = javaClass;
        char s = fieldRef.descriptorName.charAt(0);
        StaticVars staticVars = javaClass.staticVars;

        if(s == 'Z' || s == 'B' || s == 'C' || s == 'S' || s == 'I'){
            int val = operandStack.popInt();
            staticVars.putIntByIndex(field_info.slotId, val);
        }else if ( s == 'J' ){
            long val = operandStack.popLong();
            staticVars.putLong(field_info.slotId, val);
        }else if (s == 'F'){
            float val = operandStack.popFloat();
            staticVars.putFloat(field_info.slotId, val);
        }else if (s == 'D'){
            double val = operandStack.popDouble();
            staticVars.putDouble(field_info.slotId, val);
        }else if(s == 'L' ){
            JObject val = operandStack.popJObject();
            staticVars.putJObject(field_info.slotId,val);
        }
        else if( s == '['){
            JObject val = operandStack.popJObject();
            staticVars.putJObject(field_info.slotId,val);
        }
    }
}
