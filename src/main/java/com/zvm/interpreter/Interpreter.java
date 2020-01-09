package com.zvm.interpreter;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.zvm.basestruct.U1;
import com.zvm.basestruct.U2;
import com.zvm.basestruct.U4;
import com.zvm.classfile.*;
import com.zvm.classfile.constantpool.*;
import com.zvm.jnative.NativeUtils;
import com.zvm.memory.ArrayFields;
import com.zvm.memory.MethodArea;
import com.zvm.memory.ObjectFields;
import com.zvm.runtime.*;
import com.zvm.runtime.struct.JObject;
import com.zvm.basestruct.TypeCode;
import com.zvm.utils.TypeUtils;

import java.util.ArrayList;
import java.util.List;

import static com.zvm.interpreter.Opcode.NEWARRAY;
import static com.zvm.basestruct.TypeCode.*;

public class Interpreter {

    public RunTimeEnv runTimeEnv;
    public JThread jThread ;


    public Interpreter(RunTimeEnv runTimeEnv){
        this.runTimeEnv = runTimeEnv;
        jThread = new JThread();
    }

    public void invokeByName(JavaClass javaClass, String name, String descriptor){
        MethodInfo method_info = javaClass.findMethod(name, descriptor);
        if (method_info == null){
            return ;
        }
        CallSite callSite = new CallSite();
        callSite.setCallSite( method_info);

        jThread.pushFrame(callSite.maxStack, callSite.maxLocals);
        executeByteCode(jThread, javaClass, callSite.code, TypeUtils.byteArr2Int(callSite.codeLength.u4));
    }

    public void executeByteCode(JThread jThread, JavaClass javaClass, U1[] codeRaw, int codeLength) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;
        CodeUtils code = new CodeUtils(codeRaw, 0);
        for (; code.getPc() < codeLength; code.pcAdd(1)) {
            int opcodeInt = TypeUtils.byteArr2Int(codeRaw[code.getPc()].u1);
            Gson gson = new Gson();
            System.out.println("pc = " + code.getPc() + " operandStack size "+ operandStack.size);
            //System.out.println("pc = " + code.getPc() + " operandStack "+gson.toJson(operandStack));
            System.out.println("pc = " + code.getPc() + " localVars size "+ localVars.slots.length);
            //System.out.println("pc = " + code.getPc() + " localVars " + gson.toJson(localVars));
            System.out.println();
            System.out.println("pc = " + code.getPc() + " opcode:" + Opcode1.getMnemonic(opcodeInt));

            switch (opcodeInt) {
                case Opcode.NOP: {

                }
                break;
                case Opcode.ACONST_NULL: {
                    operandStack.putJObject(null);
                }
                break;
                case Opcode.ICONST_M1: {
                }
                break;
                case Opcode.ICONST_0: {
                    operandStack.putInt(0);
                }
                break;
                case Opcode.ICONST_1: {
                    operandStack.putInt(1);
                }
                break;
                case Opcode.ICONST_2: {
                    operandStack.putInt(2);

                }
                break;
                case Opcode.ICONST_3: {
                    operandStack.putInt(3);
                }
                break;
                case Opcode.ICONST_4: {
                    operandStack.putInt(4);
                }
                break;
                case Opcode.ICONST_5: {
                    operandStack.putInt(5);
                }
                break;
                case Opcode.LCONST_0: {
                    operandStack.putLong(0);
                }
                break;
                case Opcode.BIPUSH: {
                    byte byteConstant = code.consumeU1();
                    operandStack.putByte(byteConstant);
                }
                break;
                case Opcode.SIPUSH: {
                    short shortConstant = code.consumeU2();
                    operandStack.putInt(shortConstant);
                }
                break;
                case Opcode.LDC: {
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
                            runTimeEnv.methodArea.initClass(className, this);
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
                        putCharArrField(charArrayJObject, chars);

                        ObjectFields stringFields = runTimeEnv.javaHeap.objectContainer.get(stringObject.offset);
                        int slotId = stringClass.findField("value", cClassName).slotId;
                        stringFields.putJObject(slotId, charArrayJObject);
                        operandStack.putJObject(stringObject);
                    }else if(constant_base instanceof ConstantClass){
                        U2 nameIndex = ((ConstantClass) constant_base).nameIndex;

                        //operandStack.putFloat(ldcValue);
                    }

                }
                break;
                case Opcode.LDC_W: {
                    /*从常量池取值到frame顶*/
                    short cpIndex = code.consumeU2();
                    ConstantBase constant_base = javaClass.getClassFile().constantPool.cpInfo[cpIndex - 1];
                    if(constant_base instanceof ConstantInteger){
                        U4 ldcBytes = ((ConstantInteger) constant_base).bytes;
                        Integer ldcValue = TypeUtils.byteArr2Int(ldcBytes.u4);
                        operandStack.putInt(ldcValue);
                    }else if(constant_base instanceof ConstantFloat){
                        U4 ldcBytes = ((ConstantFloat) constant_base).bytes;
                        float ldcValue = TypeUtils.byteArr2Float(ldcBytes.u4);
                        operandStack.putFloat(ldcValue);
                    }
                }
                break;
                case Opcode.LDC2_W: {
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
                break;
                case Opcode.ILOAD: {
                    int index = TypeUtils.byte2Int(code.consumeU1());
                    int loadValue = localVars.getIntByIndex(index);
                    operandStack.putInt(loadValue);
                }
                break;
                case Opcode.LLOAD: {
                    int index = TypeUtils.byte2Int(code.consumeU1());
                    long loadValue = localVars.getLongByIndex(index);
                    operandStack.putLong(loadValue);
                }
                break;
                case Opcode.FLOAD: {
                    int index = TypeUtils.byte2Int(code.consumeU1());
                    float loadValue = localVars.getFloat(index);
                    operandStack.putFloat(loadValue);
                }
                break;
                case Opcode.DLOAD: {
                    int index = TypeUtils.byte2Int(code.consumeU1());
                    double loadValue = localVars.getDouble(index);
                    operandStack.putDouble(loadValue);
                }
                break;
                case Opcode.ALOAD: {
                    int index = TypeUtils.byte2Int(code.consumeU1());
                    JObject jObject = localVars.getJObject(index);
                    operandStack.putJObject(jObject);
                }
                break;
                case Opcode.LLOAD_2: {
                    long loadValue = localVars.getLongByIndex(2);
                    operandStack.putLong(loadValue);
                }
                break;
                case Opcode.LLOAD_3: {
                    long loadValue = localVars.getLongByIndex(3);
                    operandStack.putLong(loadValue);
                }
                break;
                case Opcode.FLOAD_0: {
                    float loadValue = localVars.getFloat(0);
                    operandStack.putFloat(loadValue);
                }
                break;
                case Opcode.FLOAD_1: {
                    float loadValue = localVars.getFloat(1);
                    operandStack.putFloat(loadValue);
                }
                break;
                case Opcode.FLOAD_2: {
                    float loadValue = localVars.getFloat(2);
                    operandStack.putFloat(loadValue);
                }
                break;
                case Opcode.FLOAD_3: {
                    float loadValue = localVars.getFloat(3);
                    operandStack.putFloat(loadValue);
                }
                break;
                case Opcode.DLOAD_0: {
                    double loadValue = localVars.getDouble(0);
                    operandStack.putDouble(loadValue);
                }
                break;
                case Opcode.DLOAD_1: {
                    double loadValue = localVars.getDouble(1);
                    operandStack.putDouble(loadValue);
                }
                break;
                case Opcode.DLOAD_2: {
                    double loadValue = localVars.getDouble(2);
                    operandStack.putDouble(loadValue);
                }
                break;
                case Opcode.DLOAD_3: {
                    double loadValue = localVars.getDouble(3);
                    operandStack.putDouble(loadValue);
                }
                break;
                case Opcode.FALOAD: {
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    operandStack.putFloat(arrayFields.getFloat(index));
                }
                break;
                case Opcode.DALOAD: {
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    operandStack.putDouble(arrayFields.getDouble(index));
                }
                break;
                case Opcode.AALOAD: {
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    operandStack.putJObject(arrayFields.getJObject(index));
                }
                break;
                case Opcode.BALOAD: {
                }
                break;
                case Opcode.CALOAD: {

                }
                break;
                case Opcode.SALOAD: {
                }
                break;
                case Opcode.ISTORE: {
                    int index = code.consumeU1();
                    localVars.putIntByIndex(index, operandStack.popInt());
                }
                break;
                case Opcode.LSTORE: {
                    int index = code.consumeU1();
                    localVars.putLong(index, operandStack.popLong());
                }
                break;
                case Opcode.FSTORE: {
                    int index = code.consumeU1();
                    localVars.putFloat(index, operandStack.popFloat());
                }
                break;
                case Opcode.DSTORE: {
                    int index = code.consumeU1();
                    localVars.putDouble(index, operandStack.popDouble());
                }
                break;
                case Opcode.LSTORE_1: {
                    localVars.putLong(1, operandStack.popLong());
                }
                break;
                case Opcode.LSTORE_2: {
                    localVars.putLong(2, operandStack.popLong());
                }
                break;
                case Opcode.LSTORE_3: {
                    localVars.putLong(3, operandStack.popLong());
                }
                break;
                case Opcode.FSTORE_0: {
                    localVars.putFloat(0, operandStack.popFloat());
                }
                break;
                case Opcode.FSTORE_1: {
                    localVars.putFloat(1, operandStack.popFloat());
                }
                break;
                case Opcode.FSTORE_2: {
                    localVars.putFloat(2, operandStack.popFloat());
                }
                break;
                case Opcode.FSTORE_3: {
                    localVars.putFloat(3, operandStack.popFloat());
                }
                break;
                case Opcode.DSTORE_0: {
                    localVars.putDouble(0, operandStack.popDouble());
                }
                break;
                case Opcode.DSTORE_1: {
                    localVars.putDouble(1, operandStack.popDouble());
                }
                break;
                case Opcode.DSTORE_2: {
                    localVars.putDouble(2, operandStack.popDouble());
                }
                break;
                case Opcode.LASTORE: {
                    long value = operandStack.popLong();
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    arrayFields.putLong(index, value);
                }
                break;
                case Opcode.FASTORE: {
                    float value = operandStack.popFloat();
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    arrayFields.putFloat(index, value);
                }
                break;
                case Opcode.DASTORE: {
                    double value = operandStack.popDouble();
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    arrayFields.putDouble( index, value);
                }
                break;
                case Opcode.AASTORE: {
                    JObject value = operandStack.popJObject();
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    arrayFields.putJOject( index, value);
                }
                break;
                case Opcode.BASTORE: {
                }
                break;
                case Opcode.CASTORE: {
                }
                break;
                case Opcode.SASTORE: {
                }
                break;
                case Opcode.POP: {
                }
                break;
                case Opcode.POP2: {
                }
                break;
                case Opcode.DUP: {
                    operandStack.putSlot(operandStack.getSlot());
                }
                break;
                case Opcode.IADD: {
                    int var1 = operandStack.popInt();
                    int var0 = operandStack.popInt();
                    int sum = var0 + var1;
                    operandStack.putInt(sum);
                }
                break;
                case Opcode.LADD: {
                    long var1 = operandStack.popLong();
                    long var0 = operandStack.popLong();
                    long addValue = var0 + var1;
                    operandStack.putLong(addValue);
                }
                break;
                case Opcode.FADD: {
                    float var1 = operandStack.popFloat();
                    float var0 = operandStack.popFloat();
                    float addValue = var0 + var1;
                    operandStack.putFloat(addValue);
                }
                break;
                case Opcode.DADD: {
                    double var1 = operandStack.popDouble();
                    double var0 = operandStack.popDouble();
                    double addValue = var0 + var1;
                    operandStack.putDouble(addValue);
                }
                break;
                case Opcode.ISUB: {
                    int var1 = operandStack.popInt();
                    int var0 = operandStack.popInt();
                    int subValue = var0 - var1;
                    operandStack.putInt(subValue);
                }
                break;
                case Opcode.LSUB: {
                    long var1 = operandStack.popLong();
                    long var0 = operandStack.popLong();
                    long subValue = var0 - var1;
                    operandStack.putLong(subValue);
                }
                break;
                case Opcode.FSUB: {
                    float var1 = operandStack.popFloat();
                    float var0 = operandStack.popFloat();
                    float subValue = var0 - var1;
                    operandStack.putFloat(subValue);
                }
                break;
                case Opcode.DSUB: {
                    double var1 = operandStack.popLong();
                    double var0 = operandStack.popLong();
                    double subValue = var0 - var1;
                    operandStack.putDouble(subValue);
                }
                break;
                case Opcode.IMUL: {
                    int var1 = operandStack.popInt();
                    int var0 = operandStack.popInt();
                    int sum = var0 * var1;
                    operandStack.putInt(sum);
                }
                break;
                case Opcode.LMUL: {
                }
                break;
                case Opcode.IREM: {
                }
                break;
                case Opcode.LREM: {
                }
                break;
                case Opcode.FREM: {
                }
                break;
                case Opcode.DREM: {
                }
                break;
                case Opcode.INEG: {
                }
                break;
                case Opcode.LNEG: {
                }
                break;
                case Opcode.FNEG: {
                }
                break;
                case Opcode.DNEG: {
                }
                break;
                case Opcode.ISHL: {
                }
                break;
                case Opcode.LSHL: {
                }
                break;
                case Opcode.IOR: {
                }
                break;
                case Opcode.LOR: {
                }
                break;
                case Opcode.IXOR: {
                }
                break;
                case Opcode.LXOR: {
                }
                break;
                case Opcode.IINC: {
                    byte localVarIndex = code.consumeU1();
                    byte constValue = code.consumeU1();
                    int localVar = localVars.getIntByIndex(localVarIndex);
                    localVars.putIntByIndex(localVarIndex, localVar + constValue);
                }
                break;
                case Opcode.I2L: {
                }
                break;
                case Opcode.I2F: {
                }
                break;
                case Opcode.I2D: {
                    int iValue = operandStack.popInt();
                    double dValue = iValue + 0.0;
                    operandStack.putDouble(dValue);
                }
                break;
                case Opcode.L2I: {
                }
                break;
                case Opcode.L2F: {
                }
                break;
                case Opcode.D2F: {
                }
                break;
                case Opcode.I2B: {
                }
                break;
                case Opcode.I2C: {
                }
                break;
                case Opcode.I2S: {
                }
                break;
                case Opcode.LCMP: {
                    long var1 = operandStack.popLong();
                    long var0 = operandStack.popLong();
                    int cmpRes = 0;
                    if(var0 > var1){
                        cmpRes = 1;
                    }else if(var0 < var1){
                        cmpRes = -1;
                    }
                    operandStack.putInt(cmpRes);
                }
                break;
                case Opcode.FCMPL: {
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
                break;
                case Opcode.FCMPG: {
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
                break;
                case Opcode.DCMPL: {
                    /*未考虑出现NaN的情况*/
                    double var1 = operandStack.popDouble();
                    double var0 = operandStack.popDouble();
                    int cmpRes = 0;
                    if(var0 > var1){
                        cmpRes = 1;
                    }else if(var0 < var1){
                        cmpRes = -1;
                    }
                    operandStack.putInt(cmpRes);
                }
                break;
                case Opcode.DCMPG: {
                    /*未考虑出现NaN的情况*/
                    double var1 = operandStack.popDouble();
                    double var0 = operandStack.popDouble();
                    int cmpRes = 0;
                    if(var0 > var1){
                        cmpRes = 1;
                    }else if(var0 < var1){
                        cmpRes = -1;
                    }
                    operandStack.putInt(cmpRes);
                }
                break;
                case Opcode.IFEQ: {
                    int ifeqValue = operandStack.popInt();
                    if(ifeqValue == 0){
                        int offset = code.readU2();
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }

                }
                break;
                case Opcode.LCONST_1: {
                    operandStack.putLong(1L);
                }
                break;
                case Opcode.FCONST_0: {
                    operandStack.putFloat(0.0f);
                }
                break;
                case Opcode.FCONST_1: {
                    operandStack.putFloat(1.0f);
                }
                break;
                case Opcode.FCONST_2: {
                    operandStack.putFloat(2.0f);
                }
                break;
                case Opcode.DCONST_0: {
                    operandStack.putDouble(0.0);
                }
                break;
                case Opcode.DCONST_1: {
                    operandStack.putDouble(1.0);
                }
                break;
                case Opcode.ILOAD_0: {
                    operandStack.putInt(localVars.getIntByIndex(0));
                }
                break;
                case Opcode.ILOAD_1: {
                    operandStack.putInt(localVars.getIntByIndex(1));
                }
                break;
                case Opcode.ILOAD_2: {
                    operandStack.putInt(localVars.getIntByIndex(2));
                }
                break;
                case Opcode.ILOAD_3: {
                    operandStack.putInt(localVars.getIntByIndex(3));
                }
                break;
                case Opcode.LLOAD_0: {
                    long loadValue = localVars.getLongByIndex(0);
                    operandStack.putLong(loadValue);
                }
                break;
                case Opcode.LLOAD_1: {
                    long loadValue = localVars.getLongByIndex(1);
                    operandStack.putLong(loadValue);
                }
                break;
                case Opcode.ALOAD_0: {
                    operandStack.putJObject(localVars.getJObject(0));
                }
                break;
                case Opcode.ALOAD_1: {
                    operandStack.putJObject(localVars.getJObject(1));
                }
                break;
                case Opcode.ALOAD_2: {
                    operandStack.putJObject(localVars.getJObject(2));
                }
                break;
                case Opcode.ALOAD_3: {
                    operandStack.putJObject(localVars.getJObject(3));
                }
                break;
                case Opcode.IALOAD: {
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    operandStack.putInt(arrayFields.getInt(index));
                }
                break;
                case Opcode.LALOAD: {
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    operandStack.putLong((arrayFields).getLong( index));
                }
                break;
                case Opcode.ASTORE: {
                    int index = code.consumeU1();
                    localVars.putJObject(index, operandStack.popJObject());
                }
                break;
                case Opcode.ISTORE_0: {
                    localVars.putIntByIndex(0, operandStack.popInt());
                }
                break;
                case Opcode.ISTORE_1: {
                    localVars.putIntByIndex(1, operandStack.popInt());
                }
                break;
                case Opcode.ISTORE_2: {
                    localVars.putIntByIndex(2, operandStack.popInt());
                }
                break;
                case Opcode.ISTORE_3: {
                    localVars.putIntByIndex(3, operandStack.popInt());
                }
                break;
                case Opcode.LSTORE_0: {
                    localVars.putLong(0, operandStack.popLong());
                }
                break;
                case Opcode.DSTORE_3: {
                    localVars.putDouble(3, operandStack.popDouble());
                }
                break;
                case Opcode.ASTORE_0: {
                    localVars.putJObject(0, operandStack.popJObject());
                }
                break;
                case Opcode.ASTORE_1: {
                    localVars.putJObject(1, operandStack.popJObject());
                }
                break;
                case Opcode.ASTORE_2: {
                    localVars.putJObject(2, operandStack.popJObject());
                }
                break;
                case Opcode.ASTORE_3: {
                    localVars.putJObject(3, operandStack.popJObject());
                }
                break;
                case Opcode.IASTORE: {
                    int value = operandStack.popInt();
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    arrayFields.putInt(index, value);
                }
                break;
                case Opcode.DUP_X1: {
                }
                break;
                case Opcode.DUP_X2: {
                }
                break;
                case Opcode.DUP2: {
                }
                break;
                case Opcode.DUP2_X1: {
                }
                break;
                case Opcode.DUP2_X2: {
                }
                break;
                case Opcode.SWAP: {
                }
                break;
                case Opcode.FMUL: {
                }
                break;
                case Opcode.DMUL: {
                    double var1 = operandStack.popDouble();
                    double var0 = operandStack.popDouble();
                    double sum = var0 * var1;
                    operandStack.putDouble(sum);
                }
                break;
                case Opcode.IDIV: {
                }
                break;
                case Opcode.LDIV: {
                }
                break;
                case Opcode.FDIV: {
                }
                break;
                case Opcode.DDIV: {
                }
                break;
                case Opcode.ISHR: {
                }
                break;
                case Opcode.LSHR: {
                }
                break;
                case Opcode.IUSHR: {
                }
                break;
                case Opcode.LUSHR: {
                }
                break;
                case Opcode.IAND: {
                }
                break;
                case Opcode.LAND: {
                }
                break;
                case Opcode.L2D: {
                }
                break;
                case Opcode.F2I: {
                }
                break;
                case Opcode.F2L: {
                }
                break;
                case Opcode.F2D: {
                }
                break;
                case Opcode.D2I: {
                }
                break;
                case Opcode.D2L: {
                }
                break;
                case Opcode.IFNE: {
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 != 0){
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.IFLT: {
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 < 0){
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.IFGE: {
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 >= 0){
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.IFGT: {
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 > 0){
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.IFLE: {
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 <= 0){
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.IF_ICMPEQ: {
                    int var1 = operandStack.popInt();
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 == var1){
                        /*分支*/
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.IF_ICMPNE: {
                    int var1 = operandStack.popInt();
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 != var1){
                        /*分支*/
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.IF_ICMPLT: {
                    int var1 = operandStack.popInt();
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 == var1){
                        /*分支*/
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.IF_ICMPGE: {
                    int var1 = operandStack.popInt();
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 >= var1){
                        /*分支*/
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.IF_ICMPGT: {
                    int var1 = operandStack.popInt();
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 > var1){
                        /*分支*/
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.IF_ICMPLE: {
                    int var1 = operandStack.popInt();
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 <= var1){
                        /*分支*/
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.IF_ACMPEQ: {

                }
                break;
                case Opcode.IF_ACMPNE: {

                }
                break;
                case Opcode.GOTO_: {
                    short offset = code.readU2();
                    code.pcAddBackOne(offset );
                }
                break;
                case Opcode.JSR: {
                }
                break;
                case Opcode.RET: {
                }
                break;
                case Opcode.TABLESWITCH: {
                }
                break;
                case Opcode.LOOKUPSWITCH: {
                }
                break;
                case Opcode.IRETURN: {
                    jThread.popFrame();
                    JavaFrame invokerFrame = jThread.getTopFrame();
                    int val = operandStack.popInt();
                    invokerFrame.operandStack.putInt(val);
                    return;
                }
                case Opcode.LRETURN: {
                    jThread.popFrame();
                    JavaFrame invokerFrame = jThread.getTopFrame();
                    long val = operandStack.popLong();
                    invokerFrame.operandStack.putLong(val);
                    return;
                }
                case Opcode.FRETURN: {
                    jThread.popFrame();
                    JavaFrame invokerFrame = jThread.getTopFrame();
                    float val = operandStack.popFloat();
                    invokerFrame.operandStack.putFloat(val);
                    return;
                }
                case Opcode.DRETURN: {
                    jThread.popFrame();
                    JavaFrame invokerFrame = jThread.getTopFrame();
                    double val = operandStack.popDouble();
                    invokerFrame.operandStack.putDouble(val);
                    return;
                }
                case Opcode.ARETURN: {
                    jThread.popFrame();
                    JavaFrame invokerFrame = jThread.getTopFrame();
                    JObject val = operandStack.popJObject();
                    invokerFrame.operandStack.putJObject(val);
                    return;
                }
                case Opcode.RETURN_: {
                    jThread.popFrame();
                    return;
                }
                case Opcode.GETSTATIC: {
                    short staticIndex = code.consumeU2();
                    ClassFile classFile = javaClass.getClassFile();
                    ConstantBase[] constant_bases = classFile.constantPool.cpInfo;
                    ConstantBase constant_base = constant_bases[staticIndex - 1];
                    Ref fieldRef = processRef(javaClass, constant_base);

                    FieldInfo field_info = parseFieldRef(fieldRef);
                    int slotId = field_info.slotId;
                    JavaClass javaClass1 = runTimeEnv.methodArea.findClass(fieldRef.className);
                    StaticVars staticVars = javaClass1.staticVars;
                    char s = fieldRef.descriptorName.charAt(0);
                    if(s == 'Z' || s == 'B' || s == 'C' || s == 'S' || s == 'I'){
                        operandStack.putInt(staticVars.getIntByIndex(slotId));
                    }else if ( s == 'J' ){
                        operandStack.putLong(staticVars.getLongByIndex(slotId));
                    }else if (s == 'F'){
                        operandStack.putFloat(staticVars.getFloat(slotId));
                    }else if (s == 'D'){
                        operandStack.putDouble(staticVars.getDouble(slotId));
                    }else if(s == 'L' || s== '['){
                        /*bug*/
                        operandStack.putJObject(staticVars.getJObject(slotId));
                    }

                }
                break;
                case Opcode.PUTSTATIC: {
                    short fieldCpIndex = code.consumeU2();
                    ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
                    ConstantBase constant_fieldref = constant_bases[fieldCpIndex - 1];
                    putStaticField(javaClass,constant_fieldref);
                }
                break;
                case Opcode.GETFIELD: {
                    short fieldCpIndex = code.consumeU2();
                    ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
                    ConstantBase constant_fieldref = constant_bases[fieldCpIndex - 1];
                    getField(javaClass,constant_fieldref);
                }
                break;
                case Opcode.PUTFIELD: {
                    short fieldCpIndex = code.consumeU2();
                    ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
                    ConstantBase constant_fieldref = constant_bases[fieldCpIndex - 1];
                    putField(javaClass,constant_fieldref);
                }
                break;
                case Opcode.INVOKEVIRTUAL: {
                    short invokeIndex = code.consumeU2();
                    ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
                    ConstantBase constant_methodref = constant_bases[invokeIndex - 1];
                    invokeVirtual(javaClass,constant_methodref);
                }
                break;
                case Opcode.INVOKESPECIAL: {
                    short invokeIndex = code.consumeU2();
                    ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
                    ConstantBase constant_methodref = constant_bases[invokeIndex - 1];
                    Ref methodRef = processRef(javaClass, constant_methodref);

                    invokeSpecial(methodRef);

                }
                break;
                case Opcode.INVOKESTATIC: {
                    short invokeIndex = code.consumeU2();
                    ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
                    ConstantBase constant_base = constant_bases[invokeIndex - 1];
                    invokeStatic(javaClass,constant_base);

                }
                break;
                case Opcode.INVOKEINTERFACE: {
                }
                break;
                case Opcode.XXXUNUSEDXXX: {
                }
                break;
                case Opcode.NEW_: {
                    int newIndex = code.consumeU2();
                    ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
                    ConstantBase constant_base = constant_bases[newIndex - 1];
                    int name_index = TypeUtils.byteArr2Int(((ConstantClass) constant_base).nameIndex.u2);
                    ConstantUtf8 constant_utf8 = (ConstantUtf8) constant_bases[name_index - 1];
                    JObject jObject = execNew(javaClass,constant_utf8);
                    operandStack.putJObject(jObject);
                }
                break;
                case NEWARRAY: {
                    int arrayType = code.consumeU1();
                    int count = operandStack.popInt();
                    JObject jArray = newarray(arrayType,count);
                    operandStack.putJObject(jArray);
                }
                break;
                case Opcode.ANEWARRAY: {
                    int classIndex = code.consumeU2();
                    int count = operandStack.popInt();
                    JObject jArray = anewarray(classIndex, javaClass,count);
                    operandStack.putJObject(jArray);
                }
                break;
                case Opcode.ARRAYLENGTH: {
                    JObject arrayJObject = operandStack.popJObject();
                    ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayJObject.offset);
                    int size = arrayFields.arraySize;
                    operandStack.putInt(size);
                }
                break;
                case Opcode.ATHROW: {
                }
                break;
                case Opcode.CHECKCAST: {
                    code.consumeU2();
                }
                break;
                case Opcode.INSTANCEOF_: {
                }
                break;
                case Opcode.MONITORENTER: {
                }
                break;
                case Opcode.MONITOREXIT: {
                }
                break;
                case Opcode.WIDE: {
                }
                break;
                case Opcode.MULTIANEWARRAY: {
                }
                break;
                case Opcode.IFNULL: {
                }
                break;
                case Opcode.IFNONNULL: {
                    JObject jObject = operandStack.popJObject();
                    if(jObject != null){
                        int offset = code.readU2();
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.GOTO_W: {
                }
                break;
                case Opcode.JSR_W: {
                }
                break;
                case Opcode.BREAKPOINT: {
                }
                break;
                case Opcode.INVOKENATIVE: {
//                    short invokeIndex = code.consumeU1();
//                    ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.CpInfo;
//                    ConstantBase constant_base = constant_bases[invokeIndex - 1];
                    invokeNative(javaClass);
                }
                break;
                case Opcode.IMPDEP2: {
                }
                break;
            }
        }
    }



    private void putCharArrField(JObject charArrayJObject, char[] chars) {
        ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(charArrayJObject.offset);
        arrayFields.putCharArr(chars);
    }


    private void getField(JavaClass javaClass, ConstantBase constant_fieldref) {
        OperandStack operandStack = jThread.getTopFrame().operandStack;
        Ref fieldRef = processRef(javaClass, constant_fieldref);
        JavaClass classOfCurField = runTimeEnv.methodArea.findClass(fieldRef.className);
        if(classOfCurField == null){
            runTimeEnv.methodArea.loadClass(fieldRef.className);
            runTimeEnv.methodArea.linkClass(fieldRef.className);
            runTimeEnv.methodArea.initClass(fieldRef.className, this);
        }
        FieldInfo field_info = classOfCurField.findField(fieldRef.refName,fieldRef.descriptorName);
        char s = fieldRef.descriptorName.charAt(0);
        JObject jObject = operandStack.popJObject();
        ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
        if(s == 'Z' || s == 'B' || s == 'C' || s == 'S' || s == 'I'){
            operandStack.putInt(objectFields.getIntByIndex(field_info.slotId));
        }else if ( s == 'J' ){
            operandStack.putLong(objectFields.getLongByIndex(field_info.slotId));
        }else if (s == 'F'){
            operandStack.putFloat(objectFields.getFloat(field_info.slotId));
        }else if (s == 'D'){
            operandStack.putDouble(objectFields.getDouble(field_info.slotId));
        }else if(s == 'L' || s== '['){
            /*bug*/
            operandStack.putJObject(objectFields.getJObject(field_info.slotId));
        }
    }

    /**
     * putfield指令
     * @param javaClass
     * @param constant_fieldref
     */
    private void putField(JavaClass javaClass, ConstantBase constant_fieldref) {
        OperandStack operandStack = jThread.getTopFrame().operandStack;
        Ref fieldRef = processRef(javaClass, constant_fieldref);
        FieldInfo field_info = javaClass.findField(fieldRef.refName,fieldRef.descriptorName);
        char s = fieldRef.descriptorName.charAt(0);

        if(s == 'Z' || s == 'B' || s == 'C' || s == 'S' || s == 'I'){
            int val = operandStack.popInt();
            JObject jObject = operandStack.popJObject();
            ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
            objectFields.putIntByIndex(field_info.slotId, val);
        }else if ( s == 'J' ){
            long val = operandStack.popLong();
            JObject jObject = operandStack.popJObject();
            ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
            objectFields.putLong(field_info.slotId, val);
        }else if (s == 'F'){
            float val = operandStack.popFloat();
            JObject jObject = operandStack.popJObject();
            ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
            objectFields.putFloat(field_info.slotId, val);
        }else if (s == 'D'){
            double val = operandStack.popDouble();
            JObject jObject = operandStack.popJObject();
            ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
            objectFields.putDouble(field_info.slotId, val);
        }else if(s == 'L' ){
            JObject val = operandStack.popJObject();
            JObject jObject = operandStack.popJObject();
            ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
            objectFields.putJObject(field_info.slotId, val);
        }
        else if( s == '['){
            /*这个是数组对象引用*/
            JObject val = operandStack.popJObject();
            /*将数组对象引用放入这个对象中*/
            JObject jObject = operandStack.popJObject();
            FieldInfo concreteFieldInfo = jObject.javaClass.findField(fieldRef.refName, fieldRef.descriptorName);
            ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
            objectFields.putJObject(concreteFieldInfo.slotId, val);
        }
    }

    private void putStaticField(JavaClass javaClass, ConstantBase constant_fieldref) {
        OperandStack operandStack = jThread.getTopFrame().operandStack;
        Ref fieldRef = processRef(javaClass, constant_fieldref);
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

    /**
     * 解析字段
     * @param fieldRef
     */
    private FieldInfo parseFieldRef(Ref fieldRef ) {
        JavaClass javaClass = runTimeEnv.methodArea.loadClass(fieldRef.className);
        runTimeEnv.methodArea.linkClass(fieldRef.className);
        runTimeEnv.methodArea.initClass(fieldRef.className, this);
        JavaClass javaClass1 = runTimeEnv.methodArea.findClass(fieldRef.className);
        FieldInfo field_info = javaClass1.findField(fieldRef.refName, fieldRef.descriptorName);
        return field_info;
    }


//    private void invokeSpecial(JavaClass javaClass, ConstantBase constant_methodref) {
//        Ref methodRef = processRef(javaClass, constant_methodref);
//        invokeSpecial(methodRef);
//    }

    public void invokeSpecial(Ref methodRef) {

        MethodInfo method_info = parseMethodRef(methodRef);
        /*调用传递参数 如(J)J*/
        Descriptor descriptor = processDescriptor(methodRef.descriptorName);

        CallSite callSite = new CallSite();
        callSite.setCallSite( method_info);
        OperandStack invokerStack = jThread.getTopFrame().operandStack;
        jThread.pushFrame(callSite.maxStack, callSite.maxLocals);
        JavaFrame curFrame = jThread.getTopFrame();
        LocalVars curLocalVars = curFrame.localVars;

        /*调用传递参数*/
        int slotCount = calParametersSlot(method_info, descriptor.parameters);

        for(int i = 0; i < slotCount; i++){
            curLocalVars.putSlot(slotCount - 1 - i, invokerStack.popSlot());
        }
        executeByteCode(jThread, method_info.javaClass, callSite.code, TypeUtils.byteArr2Int(callSite.codeLength.u4));
    }



    private void invokeVirtual(JavaClass javaClass, ConstantBase constant_methodref) {
        Ref methodRef = processRef(javaClass, constant_methodref);

        MethodInfo method_info = parseMethodRef(methodRef);
        /*调用传递参数 如(J)J*/
        Descriptor descriptor = processDescriptor(methodRef.descriptorName);

        JObject jObject = jThread.getTopFrame().operandStack.getJObjectFromTop(method_info.argSlotCount - 1);

        if(jObject == null){
            /*hack*/
            if("println".equals(methodRef.refName)){
                _println(jThread.getTopFrame().operandStack, methodRef.descriptorName);
                return;
            }
        }
        /*类似 Father object = new Son();Son 存在方法时，需要调用son的方法，而不是father的*/
        if(!methodRef.className.equals(jObject.javaClass.classPath) ){
            MethodInfo concreteClassMethod = jObject.javaClass.findMethod(methodRef.refName, methodRef.descriptorName);
            if(concreteClassMethod != null){
                methodRef.className = jObject.javaClass.classPath;
                method_info = parseMethodRef(methodRef);
            }
        }

        CallSite callSite = new CallSite();
        callSite.setCallSite( method_info);
        OperandStack invokerStack = jThread.getTopFrame().operandStack;
        jThread.pushFrame(callSite.maxStack, callSite.maxLocals);
        JavaFrame curFrame = jThread.getTopFrame();
        LocalVars curLocalVars = curFrame.localVars;

        /*调用传递参数*/
        int slotCount = calParametersSlot(method_info, descriptor.parameters);

        for(int i = 0; i < slotCount; i++){
            curLocalVars.putSlot(slotCount - 1 - i, invokerStack.popSlot());
        }

        executeByteCode(jThread, method_info.javaClass, callSite.code, TypeUtils.byteArr2Int(callSite.codeLength.u4));
    }

    /**
     * 本地方法hack
     * @param operandStack
     * @param descriptor
     */
    private void _println(OperandStack operandStack, String descriptor) {
        if("(Z)V".equals(descriptor)){
            System.out.println(operandStack.popInt() != 0);
        }else if("(C)V".equals(descriptor)){
            System.out.println((char)operandStack.popInt());
        }else if("(I)V".equals(descriptor)
                ||"(B)V".equals(descriptor)||"(S)V".equals(descriptor)){
            System.out.println(operandStack.popInt());
        }else if("(F)V".equals(descriptor)){
            System.out.println(operandStack.popFloat());
        }else if("(J)V".equals(descriptor)){
            System.out.println(operandStack.popLong());
        }else if("(D)V".equals(descriptor)){
            System.out.println(operandStack.popDouble());
        }else if("(Ljava/lang/String;)V".equals(descriptor)) {
            JObject jObject = operandStack.popJObject();
            ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
            FieldInfo field_info = jObject.javaClass.findField("value","[C");
            JObject charArrObject = objectFields.getJObject(field_info.slotId);
            ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(charArrObject.offset);
            char[] chars = arrayFields.trans2CharArr();
            System.out.println(chars);
        }else {
            System.out.println("println " + descriptor);
        }
        operandStack.popJObject();
    }


    /**
     * 解析方法:将方法的argSlotCount和javaClass赋值
     * @param methodRef
     */
    private MethodInfo parseMethodRef(Ref methodRef) {
        runTimeEnv.methodArea.loadClass(methodRef.className);
        runTimeEnv.methodArea.linkClass(methodRef.className);
        //runTimeEnv.methodArea.initClass(methodRef.className, this);
        JavaClass javaClass = runTimeEnv.methodArea.findClass(methodRef.className);
        MethodInfo method_info = javaClass.findMethod(methodRef.refName, methodRef.descriptorName);
        method_info.javaClass = javaClass;
        /*argSlotCount未赋值过，则赋值*/
        if(method_info.argSlotCount == -1){
            Descriptor descriptor = processDescriptor(methodRef.descriptorName);
            int slotCount = calParametersSlot(method_info, descriptor.parameters);
            method_info.argSlotCount = slotCount;

        }
        return method_info;
    }

    private void invokeStatic(JavaClass javaClass, ConstantBase constant_base) {

        Ref ref = processRef(javaClass, constant_base);
        JavaClass curClass = runTimeEnv.methodArea.findClass(ref.className);
        if(curClass == null){
            curClass = runTimeEnv.methodArea.loadClass(ref.className);
            runTimeEnv.methodArea.linkClass(ref.className);
            runTimeEnv.methodArea.initClass(ref.className, this);
        }

        MethodInfo method_info = curClass.findMethod(ref.refName, ref.descriptorName);

        if (method_info == null){
            return ;
        }

        /*如System类中的registerNatives直接返回，System的arraycopy*/
        if(MethodArea.isNative(method_info.accessFlags) && ! NativeUtils.hasNativeClass(ref)){
            return;
        }
        Descriptor descriptor = processDescriptor(ref.descriptorName);
        CallSite callSite = new CallSite();
        callSite.setCallSiteOrNative( method_info, descriptor.returnType);
        OperandStack invokerStack = jThread.getTopFrame().operandStack;
        jThread.pushFrame(callSite.maxStack, callSite.maxLocals);
        JavaFrame curFrame = jThread.getTopFrame();
        LocalVars curLocalVars = curFrame.localVars;

        /*调用传递参数*/
        int slotCount = calParametersSlot(method_info, descriptor.parameters);

        for(int i = 0; i < slotCount; i++){
            curLocalVars.putSlot(slotCount - 1 - i, invokerStack.popSlot());
        }

        executeByteCode(jThread, curClass, callSite.code, TypeUtils.byteArr2Int(callSite.codeLength.u4));
    }

    /**
     * 调用native方法,暂时只支持arraycopy
     * @param javaClass
     */
    private void invokeNative(JavaClass javaClass) {

        JavaFrame javaFrame = jThread.getTopFrame();
        arraycopy(javaFrame);
    }

    private void arraycopy(JavaFrame javaFrame) {
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;
        JObject src = localVars.getJObject(0);
        int srcPos = localVars.getIntByIndex(1);
        JObject dest = localVars.getJObject(2);
        int descPos = localVars.getIntByIndex(3);
        int length = localVars.getIntByIndex(4);

        ArrayFields srcFields = runTimeEnv.javaHeap.arrayContainer.get(src.offset);
        ArrayFields destFields = runTimeEnv.javaHeap.arrayContainer.get(dest.offset);

        for(int i = 0; i < length; i ++){
            /*暂时只考虑char*/
            destFields.putChar(descPos + i, srcFields.getChar(srcPos + i));
        }
    }

    /**
     * 传入CONSTANT_Base(子类：ConstantFieldref、ConstantMethodref)，返回className，descriptorName，methodName(或fieldName)
     */
    public Ref processRef(JavaClass javaClass, ConstantBase constant_base){
        Ref ref = new Ref();
        ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;

        int class_index = 0;
        int name_and_type_index = 0;
        if(constant_base instanceof ConstantMethodref){
            ConstantMethodref methodref  = (ConstantMethodref) constant_base;
            class_index = TypeUtils.byteArr2Int(methodref.classIndex.u2);
            name_and_type_index = TypeUtils.byteArr2Int(methodref.nameAndTypeIndex.u2);
        }else if(constant_base instanceof ConstantFieldref){
            ConstantFieldref fieldref  = (ConstantFieldref) constant_base;
            class_index = TypeUtils.byteArr2Int(fieldref.classIndex.u2);
            name_and_type_index = TypeUtils.byteArr2Int(fieldref.nameAndTypeIndex.u2);
        }

        ConstantClass constant_class = (ConstantClass) constant_bases[class_index - 1];
        ConstantNameandtype constant_nameAndType = (ConstantNameandtype)constant_bases[name_and_type_index - 1];

        ConstantUtf8 methodNameUtf8 = (ConstantUtf8)constant_bases[TypeUtils.byteArr2Int(constant_nameAndType.nameIndex.u2) - 1];
        ConstantUtf8 descriptorNameUtf8 = (ConstantUtf8)constant_bases[TypeUtils.byteArr2Int(constant_nameAndType.descriptorIndex.u2) - 1];
        ConstantUtf8 classNameUtf8 = (ConstantUtf8)constant_bases[TypeUtils.byteArr2Int(constant_class.nameIndex.u2) - 1];

        ref.className = TypeUtils.u12String(classNameUtf8.bytes);
        ref.descriptorName = TypeUtils.u12String(descriptorNameUtf8.bytes);
        ref.refName = TypeUtils.u12String(methodNameUtf8.bytes);
        System.out.println(JSON.toJSONString(ref));
        return ref;
    }

    /**
     * 解析方法的修饰符，如(J)J解析为如下形式：Descriptor：{parameters:[J],returnType:J}
     * @param descriptorName
     * @return
     */
    private Descriptor processDescriptor(String descriptorName) {
        Descriptor descriptor = new Descriptor();
        List<Integer> parameters = new ArrayList<>();
        Integer returnType = new Integer(0);
        char[] descriptorNameArr = descriptorName.toCharArray();
        int i = 0;
        while (descriptorNameArr[i] != ')'){
            switch (descriptorNameArr[i]) {
                case 'B': {
                    parameters.add(T_BYTE);
                }
                break;
                case 'C': {
                    parameters.add(T_CHAR);
                }
                break;
                case 'D': {
                    parameters.add(TypeCode.T_DOUBLE);
                }
                break;
                case 'F': {
                    parameters.add(TypeCode.T_FLOAT);
                }
                break;
                case 'I': {
                    parameters.add(TypeCode.T_INT);
                }
                break;
                case 'J': {
                    parameters.add(TypeCode.T_LONG);
                }
                break;
                case 'S': {
                    parameters.add(TypeCode.T_SHORT);
                }
                break;
                case 'Z': {
                    parameters.add(TypeCode.T_BOOLEAN);
                }
                break;
                case '[': {
                    int arrayComponentType = ++i;
                    while (descriptorNameArr[arrayComponentType] == '[') {
                        arrayComponentType++;
                    }
                    i = arrayComponentType;
                    parameters.add(TypeCode.T_EXTRA_ARRAY);
                }
                break;
                case 'L': {
                    int objectType = i++;
                    while (descriptorNameArr[objectType] != ';') {
                        objectType++;
                    }
                    i = objectType;
                    parameters.add(TypeCode.T_EXTRA_OBJECT);
                }
                break;
            }
            i++;
        }
        int len = descriptorName.length();
        while (i < len) {
            switch (descriptorNameArr[i]) {
                case 'B':{
                    returnType = T_BYTE;
                }
                break;
                case 'C':{
                    returnType = T_CHAR;
                }
                break;
                case 'D':{
                    returnType = T_DOUBLE;
                }
                break;
                case 'F':{
                    returnType = T_FLOAT;
                }
                break;
                case 'I':{
                    returnType = T_INT;
                }
                break;
                case 'J':{
                    returnType = T_LONG;
                }
                break;
                case 'S':{
                    returnType = T_SHORT;
                }
                break;
                case 'Z':{
                    returnType = T_BOOLEAN;
                }
                break;
                case 'V':{
                    returnType = T_EXTRA_VOID;
                }
                break;
                case '[': {
                    returnType = T_EXTRA_ARRAY;
                }
                break;
                case 'L': {
                    returnType = T_EXTRA_OBJECT;
                }
                break;
            }
            i++;
        }
        descriptor.parameters = parameters;
        descriptor.returnType = returnType;
        return descriptor;
    }

    private int calParametersSlot(MethodInfo method_info, List<Integer> parameters){
        int parametersCount = parameters.size();

        int slotCount = parametersCount;
        for(int i = 0; i < parametersCount; i++){
            if(parameters.get(i) == TypeCode.T_LONG
                    ||parameters.get(i) == TypeCode.T_DOUBLE){
                slotCount ++;
            }
        }
        if(!MethodArea.isStatic(method_info.accessFlags)){
            slotCount ++;/*'this' 引用*/
        }
        return slotCount;
    }

    private JObject execNew(JavaClass javaClass, ConstantUtf8 constant_utf8) {
        String className = TypeUtils.u12String(constant_utf8.bytes);
        if(runTimeEnv.methodArea.findClass(className) == null){
            runTimeEnv.methodArea.loadClass(className);
            runTimeEnv.methodArea.linkClass(className);
            runTimeEnv.methodArea.initClass(className, this);
        }
        return runTimeEnv.javaHeap.createJObject(runTimeEnv.methodArea.findClass(className), runTimeEnv, jThread);
    }

    private JObject newarray( int arrayType, int count) {
        JavaClass arrayClass = getPrimitiveArrayClass(arrayType, count);

        return runTimeEnv.javaHeap.createJArray(arrayClass,arrayType, count, runTimeEnv, jThread);
    }

    /**
     * 引用类型数组创建
     * @param classIndex
     * @param javaClass
     * @param count
     * @return
     */
    private JObject anewarray(int classIndex, JavaClass javaClass, int count) {
        ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
        ConstantClass constant_class = (ConstantClass) constant_bases[classIndex - 1];
        ConstantUtf8 classNameUtf8 = (ConstantUtf8)constant_bases[TypeUtils.byteArr2Int(constant_class.nameIndex.u2) - 1];
        String className = TypeUtils.u12String(classNameUtf8.bytes);
        JavaClass curClass = runTimeEnv.methodArea.findClass(className);
        if(curClass == null){
            curClass = runTimeEnv.methodArea.loadClass(className);
            runTimeEnv.methodArea.linkClass(className);
            runTimeEnv.methodArea.initClass(className, this);
        }

        className = processClassName(className);

        JObject jObject = runTimeEnv.javaHeap.createJArray(curClass, TypeCode.T_EXTRA_OBJECT, count, runTimeEnv, jThread);

        return jObject;
    }


    private String processClassName(String className){
        className = "[L" + className + ";";
        return className;
    }

    private JavaClass getPrimitiveArrayClass(int arrayType, int count){
        String className = null;
        if(arrayType == TypeCode.T_BOOLEAN){
            className = "[Z";
        }else if(arrayType == TypeCode.T_CHAR){
            className = "[C";
        }else if(arrayType == TypeCode.T_FLOAT){
            className = "[F";

        }else if(arrayType == TypeCode.T_DOUBLE){
            className = "[D";

        }else if(arrayType == TypeCode.T_BYTE){
            className = "[B";

        }else if(arrayType == TypeCode.T_SHORT){
            className = "[S";

        }else if(arrayType == TypeCode.T_INT){
            className = "[I";

        }else if(arrayType == TypeCode.T_LONG){
            className = "[J";

        }
        JavaClass arrayClass = runTimeEnv.methodArea.loadClass(className);
//        runTimeEnv.methodArea.linkClass(className);
 //       runTimeEnv.methodArea.initClass(className);
        return arrayClass;
    }
}
