package com.zvm.instruction.loadandstore.constant;

import com.zvm.basestruct.TypeCode;
import com.zvm.basestruct.U1;
import com.zvm.basestruct.U2;
import com.zvm.basestruct.U4;
import com.zvm.classfile.constantpool.*;
import com.zvm.instruction.Instruction;
import com.zvm.interpreter.CallSite;
import com.zvm.interpreter.CodeUtils;
import com.zvm.interpreter.Interpreter;
import com.zvm.memory.ArrayFields;
import com.zvm.memory.ObjectFields;
import com.zvm.runtime.*;
import com.zvm.runtime.struct.JObject;
import com.zvm.utils.TypeUtils;

/**
 *
 * ldc_w 指令与 ldc 指令的差别在于它使用了更宽的运行时常量池索引
 * ldc2_w 指令 有更宽的运行时常量池索引，与ldc_w不同的是，ldc2_2处理double、long
 */
public class Ldc implements Instruction {

    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        /*从常量池取值到frame顶*/
        byte cpIndex = code.consumeU1();
        ConstantBase constant_base = javaClass.getClassFile().constantPool.cpInfo[TypeUtils.byte2Int(cpIndex)-1];
        if(constant_base instanceof ConstantInteger){
            U4 ldcBytes = ((ConstantInteger) constant_base).bytes;
            Integer ldcValue = TypeUtils.byteArr2Int(ldcBytes.u4);
            operandStack.putInt(ldcValue);
        }else if(constant_base instanceof ConstantFloat){
            U4 ldcBytes = ((ConstantFloat) constant_base).bytes;
            float ldcValue = TypeUtils.byteArr2Float(ldcBytes.u4);
            operandStack.putFloat(ldcValue);
        }else if(constant_base instanceof ConstantString){
            U2 stringIndex = ((ConstantString) constant_base).stringIndex;
            String className = "java/lang/String";
            JavaClass stringClass = runTimeEnv.methodArea.findClass(className);
            if(stringClass == null){
                stringClass = runTimeEnv.methodArea.loadClass(className);
                runTimeEnv.methodArea.linkClass(className);
                runTimeEnv.methodArea.initClass(className, interpreter);
            }

            ConstantUtf8 valueUtf8 = (ConstantUtf8) javaClass.getClassFile().constantPool.cpInfo[TypeUtils.byteArr2Int(stringIndex.u2)-1];
            String value = TypeUtils.u12String(valueUtf8.bytes);
            char[] chars = value.toCharArray();

            int charsLen = chars.length;
                        /*1.新建new String()对象stringObject，
                          2.stringObject里面含有char[]对象charArrayJObject(JObject 类型)
                          3.charArrayJObject中的offset指向javaHeap中的arrayContainer，将类似"abc"的值填入
                          4.将stringObject put至operandStack中*/
            JObject stringObject = runTimeEnv.javaHeap.createJObject(stringClass, runTimeEnv, jThread);
            String cClassName = "[C";
            JavaClass cClass = runTimeEnv.methodArea.findClass(cClassName);
            if(cClass == null){
                cClass = runTimeEnv.methodArea.loadClass(cClassName);
                //    runTimeEnv.methodArea.linkClass(cClassName);
                //   runTimeEnv.methodArea.initClass(cClassName);
            }
            JObject charArrayJObject = runTimeEnv.javaHeap.createJArray(cClass, TypeCode.T_CHAR,charsLen, runTimeEnv, jThread);
            putCharArrField(runTimeEnv, charArrayJObject, chars);

            ObjectFields stringFields = runTimeEnv.javaHeap.objectContainer.get(stringObject.offset);
            int slotId = stringClass.findField("value", cClassName).slotId;
            stringFields.putJObject(slotId, charArrayJObject);
            operandStack.putJObject(stringObject);
        }else if(constant_base instanceof ConstantClass){
            U2 nameIndex = ((ConstantClass) constant_base).nameIndex;

            //operandStack.putFloat(ldcValue);
        }
    }

    private void putCharArrField(RunTimeEnv runTimeEnv, JObject charArrayJObject, char[] chars) {
        ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(charArrayJObject.offset);
        arrayFields.putCharArr(chars);
    }
}
