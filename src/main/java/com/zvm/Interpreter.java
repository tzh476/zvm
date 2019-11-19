package com.zvm;

import com.google.gson.Gson;
import com.zvm.basestruct.u1;
import com.zvm.basestruct.u4;
import com.zvm.draft.Opcode1;
import com.zvm.runtime.*;
import com.zvm.runtime.struct.JObject;

import java.util.ArrayList;
import java.util.List;

import static com.zvm.Opcode.newarray;
import static com.zvm.TypeCode.T_BYTE;
import static com.zvm.TypeCode.T_CHAR;

public class Interpreter {

    public RunTimeEnv runTimeEnv;
    public JThread jThread ;


    public Interpreter(RunTimeEnv runTimeEnv){
        this.runTimeEnv = runTimeEnv;
        jThread = new JThread();
    }

    public void invokeByName(JavaClass javaClass, String name, String descriptor){
        method_info method_info = javaClass.findMethod(name, descriptor);
        if (method_info == null){
            return ;
        }
        CallSite callSite = new CallSite();
        callSite.setCallSite( method_info);

        jThread.pushFrame(callSite.max_stack, callSite.max_locals);
        executeByteCode(jThread, javaClass, callSite.code, TypeUtils.byteArr2Int(callSite.code_length.u4));
    }

    public void executeByteCode(JThread jThread, JavaClass javaClass, u1[] codeRaw, int codeLength) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;
        CodeUtils code = new CodeUtils(codeRaw, 0);
        for (; code.getPc() < codeLength; code.pcAdd(1)) {
            int opcodeInt = TypeUtils.byteArr2Int(codeRaw[code.getPc()].u1);
            Gson gson = new Gson();
//            System.out.println("pc = " + code.getPc() + " operandStack "+gson.toJson(operandStack));
//            System.out.println("pc = " + code.getPc() + " localVars " + gson.toJson(localVars));
//            System.out.println();
//            System.out.println("pc = " + code.getPc() + " opcode:" + Opcode1.getMnemonic(opcodeInt));

            switch (opcodeInt) {
                case Opcode.nop: {

                }
                break;
                case Opcode.aconst_null: {
                    operandStack.putJObject(null);
                }
                break;
                case Opcode.iconst_m1: {
                }
                break;
                case Opcode.iconst_0: {
                    operandStack.putInt(0);
                }
                break;
                case Opcode.iconst_1: {
                    operandStack.putInt(1);
                }
                break;
                case Opcode.iconst_2: {
                    operandStack.putInt(2);

                }
                break;
                case Opcode.iconst_3: {
                    operandStack.putInt(3);

                }
                break;
                case Opcode.iconst_4: {
                    operandStack.putInt(4);
                }
                break;
                case Opcode.iconst_5: {
                    operandStack.putInt(5);
                }
                break;
                case Opcode.lconst_0: {
                    operandStack.putLong(0);
                }
                break;
                case Opcode.bipush: {
                    byte byteConstant = code.consumeU1();
                    operandStack.putByte(byteConstant);
                }
                break;
                case Opcode.sipush: {
                    short shortConstant = code.consumeU2();
                    operandStack.putInt(shortConstant);
                }
                break;
                case Opcode.ldc: {
                    /*从常量池取值到frame顶*/
                    byte cpIndex = code.consumeU1();
                    CONSTANT_Base constant_base = javaClass.getClassFile().constant_pool.cp_info[TypeUtils.byte2Int(cpIndex)-1];
                    if(constant_base instanceof CONSTANT_Integer){
                        u4 ldcBytes = ((CONSTANT_Integer) constant_base).bytes;
                        Integer ldcValue = TypeUtils.byteArr2Int(ldcBytes.u4);
                        operandStack.putInt(ldcValue);
                    }else if(constant_base instanceof CONSTANT_Float){
                        u4 ldcBytes = ((CONSTANT_Float) constant_base).bytes;
                        float ldcValue = TypeUtils.byteArr2Float(ldcBytes.u4);
                        operandStack.putFloat(ldcValue);
                    }

                }
                break;
                case Opcode.ldc_w: {
                    /*从常量池取值到frame顶*/
                    short cpIndex = code.consumeU2();
                    CONSTANT_Base constant_base = javaClass.getClassFile().constant_pool.cp_info[cpIndex - 1];
                    if(constant_base instanceof CONSTANT_Integer){
                        u4 ldcBytes = ((CONSTANT_Integer) constant_base).bytes;
                        Integer ldcValue = TypeUtils.byteArr2Int(ldcBytes.u4);
                        operandStack.putInt(ldcValue);
                    }else if(constant_base instanceof CONSTANT_Float){
                        u4 ldcBytes = ((CONSTANT_Float) constant_base).bytes;
                        float ldcValue = TypeUtils.byteArr2Float(ldcBytes.u4);
                        operandStack.putFloat(ldcValue);
                    }
                }
                break;
                case Opcode.ldc2_w: {
                    /*从常量池取值到frame顶*/
                    short cpIndex = code.consumeU2();
                    CONSTANT_Base constant_base = javaClass.getClassFile().constant_pool.cp_info[cpIndex - 1];
                    if(constant_base instanceof CONSTANT_Long){
                        u4 highBytes = ((CONSTANT_Long) constant_base).high_bytes;
                        u4 lowBytes = ((CONSTANT_Long) constant_base).low_bytes;
                        operandStack.putLong(TypeUtils.byteArr2Int(highBytes.u4), TypeUtils.byteArr2Int(lowBytes.u4));
                    }else if(constant_base instanceof CONSTANT_Double){
                        u4 highBytes = ((CONSTANT_Double) constant_base).high_bytes;
                        u4 lowBytes = ((CONSTANT_Double) constant_base).low_bytes;
                        byte[] doubleByte = TypeUtils.appendByte(highBytes.u4, lowBytes.u4);
                        double ldcValue = TypeUtils.byteArr2Double(doubleByte);
                        operandStack.putDouble(ldcValue);
                    }
                }
                break;
                case Opcode.iload: {
                    int index = TypeUtils.byte2Int(code.consumeU1());
                    int loadValue = localVars.getIntByIndex(index);
                    operandStack.putInt(loadValue);
                }
                break;
                case Opcode.lload: {
                    int index = TypeUtils.byte2Int(code.consumeU1());
                    long loadValue = localVars.getLongByIndex(index);
                    operandStack.putLong(loadValue);
                }
                break;
                case Opcode.fload: {
                    int index = TypeUtils.byte2Int(code.consumeU1());
                    float loadValue = localVars.getFloat(index);
                    operandStack.putFloat(loadValue);
                }
                break;
                case Opcode.dload: {
                    int index = TypeUtils.byte2Int(code.consumeU1());
                    double loadValue = localVars.getDouble(index);
                    operandStack.putDouble(loadValue);
                }
                break;
                case Opcode.aload: {
                    int index = TypeUtils.byte2Int(code.consumeU1());
                    JObject jObject = localVars.getJObject(index);
                    operandStack.putJObject(jObject);
                }
                break;
                case Opcode.lload_2: {
                    long loadValue = localVars.getLongByIndex(2);
                    operandStack.putLong(loadValue);
                }
                break;
                case Opcode.lload_3: {
                    long loadValue = localVars.getLongByIndex(3);
                    operandStack.putLong(loadValue);
                }
                break;
                case Opcode.fload_0: {
                    float loadValue = localVars.getFloat(0);
                    operandStack.putFloat(loadValue);
                }
                break;
                case Opcode.fload_1: {
                    float loadValue = localVars.getFloat(1);
                    operandStack.putFloat(loadValue);
                }
                break;
                case Opcode.fload_2: {
                    float loadValue = localVars.getFloat(2);
                    operandStack.putFloat(loadValue);
                }
                break;
                case Opcode.fload_3: {
                    float loadValue = localVars.getFloat(3);
                    operandStack.putFloat(loadValue);
                }
                break;
                case Opcode.dload_0: {
                    double loadValue = localVars.getDouble(0);
                    operandStack.putDouble(loadValue);
                }
                break;
                case Opcode.dload_1: {
                    double loadValue = localVars.getDouble(1);
                    operandStack.putDouble(loadValue);
                }
                break;
                case Opcode.dload_2: {
                    double loadValue = localVars.getDouble(2);
                    operandStack.putDouble(loadValue);
                }
                break;
                case Opcode.dload_3: {
                    double loadValue = localVars.getDouble(3);
                    operandStack.putDouble(loadValue);
                }
                break;
                case Opcode.faload: {
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ObjectFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    operandStack.putFloat(arrayFields.getFloat(index));
                }
                break;
                case Opcode.daload: {
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ObjectFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    /*double数组中，一个double占2个slot，定位是，偏移*2*/
                    operandStack.putDouble(arrayFields.getDouble(2 * index));
                }
                break;
                case Opcode.aaload: {
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ObjectFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    operandStack.putJObject(arrayFields.getJObject(index));
                }
                break;
                case Opcode.baload: {
                }
                break;
                case Opcode.caload: {

                }
                break;
                case Opcode.saload: {
                }
                break;
                case Opcode.istore: {
                    int index = code.consumeU1();
                    localVars.putIntByIndex(index, operandStack.popInt());
                }
                break;
                case Opcode.lstore: {
                    int index = code.consumeU1();
                    localVars.putLong(index, operandStack.popLong());
                }
                break;
                case Opcode.fstore: {
                    int index = code.consumeU1();
                    localVars.putFloat(index, operandStack.popFloat());
                }
                break;
                case Opcode.dstore: {
                    int index = code.consumeU1();
                    localVars.putDouble(index, operandStack.popDouble());
                }
                break;
                case Opcode.lstore_1: {
                    localVars.putLong(1, operandStack.popLong());
                }
                break;
                case Opcode.lstore_2: {
                    localVars.putLong(2, operandStack.popLong());
                }
                break;
                case Opcode.lstore_3: {
                    localVars.putLong(3, operandStack.popLong());
                }
                break;
                case Opcode.fstore_0: {
                    localVars.putFloat(0, operandStack.popFloat());
                }
                break;
                case Opcode.fstore_1: {
                    localVars.putFloat(1, operandStack.popFloat());
                }
                break;
                case Opcode.fstore_2: {
                    localVars.putFloat(2, operandStack.popFloat());
                }
                break;
                case Opcode.fstore_3: {
                    localVars.putFloat(3, operandStack.popFloat());
                }
                break;
                case Opcode.dstore_0: {
                    localVars.putDouble(0, operandStack.popDouble());
                }
                break;
                case Opcode.dstore_1: {
                    localVars.putDouble(1, operandStack.popDouble());
                }
                break;
                case Opcode.dstore_2: {
                    localVars.putDouble(2, operandStack.popDouble());
                }
                break;
                case Opcode.lastore: {
                    long value = operandStack.popLong();
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ObjectFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    /*long数组中，一个long占2个slot，定位是，偏移*2*/
                    arrayFields.putLong(2 * index, value);
                }
                break;
                case Opcode.fastore: {
                    float value = operandStack.popFloat();
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ObjectFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    arrayFields.putFloat(index, value);
                }
                break;
                case Opcode.dastore: {
                    double value = operandStack.popDouble();
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ObjectFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    /*double数组中，一个double占2个slot，定位是，偏移*2*/
                    arrayFields.putDouble(2 * index, value);
                }
                break;
                case Opcode.aastore: {
                }
                break;
                case Opcode.bastore: {
                }
                break;
                case Opcode.castore: {
                }
                break;
                case Opcode.sastore: {
                }
                break;
                case Opcode.pop: {
                }
                break;
                case Opcode.pop2: {
                }
                break;
                case Opcode.dup: {
                    operandStack.putSlot(operandStack.getSlot());
                }
                break;
                case Opcode.iadd: {
                    int var1 = operandStack.popInt();
                    int var0 = operandStack.popInt();
                    int sum = var0 + var1;
                    operandStack.putInt(sum);
                }
                break;
                case Opcode.ladd: {
                    long var1 = operandStack.popLong();
                    long var0 = operandStack.popLong();
                    long addValue = var0 + var1;
                    operandStack.putLong(addValue);
                }
                break;
                case Opcode.fadd: {
                    float var1 = operandStack.popFloat();
                    float var0 = operandStack.popFloat();
                    float addValue = var0 + var1;
                    operandStack.putFloat(addValue);
                }
                break;
                case Opcode.dadd: {
                    double var1 = operandStack.popDouble();
                    double var0 = operandStack.popDouble();
                    double addValue = var0 + var1;
                    operandStack.putDouble(addValue);
                }
                break;
                case Opcode.isub: {
                    int var1 = operandStack.popInt();
                    int var0 = operandStack.popInt();
                    int subValue = var0 - var1;
                    operandStack.putInt(subValue);
                }
                break;
                case Opcode.lsub: {
                    long var1 = operandStack.popLong();
                    long var0 = operandStack.popLong();
                    long subValue = var0 - var1;
                    operandStack.putLong(subValue);
                }
                break;
                case Opcode.fsub: {
                    float var1 = operandStack.popFloat();
                    float var0 = operandStack.popFloat();
                    float subValue = var0 - var1;
                    operandStack.putFloat(subValue);
                }
                break;
                case Opcode.dsub: {
                    double var1 = operandStack.popLong();
                    double var0 = operandStack.popLong();
                    double subValue = var0 - var1;
                    operandStack.putDouble(subValue);
                }
                break;
                case Opcode.imul: {
                }
                break;
                case Opcode.lmul: {
                }
                break;
                case Opcode.irem: {
                }
                break;
                case Opcode.lrem: {
                }
                break;
                case Opcode.frem: {
                }
                break;
                case Opcode.drem: {
                }
                break;
                case Opcode.ineg: {
                }
                break;
                case Opcode.lneg: {
                }
                break;
                case Opcode.fneg: {
                }
                break;
                case Opcode.dneg: {
                }
                break;
                case Opcode.ishl: {
                }
                break;
                case Opcode.lshl: {
                }
                break;
                case Opcode.ior: {
                }
                break;
                case Opcode.lor: {
                }
                break;
                case Opcode.ixor: {
                }
                break;
                case Opcode.lxor: {
                }
                break;
                case Opcode.iinc: {
                    byte localVarIndex = code.consumeU1();
                    byte constValue = code.consumeU1();
                    int localVar = localVars.getIntByIndex(localVarIndex);
                    localVars.putIntByIndex(localVarIndex, localVar + constValue);
                }
                break;
                case Opcode.i2l: {
                }
                break;
                case Opcode.i2f: {
                }
                break;
                case Opcode.i2d: {
                    int iValue = operandStack.popInt();
                    double dValue = iValue + 0.0;
                    operandStack.putDouble(dValue);
                }
                break;
                case Opcode.l2i: {
                }
                break;
                case Opcode.l2f: {
                }
                break;
                case Opcode.d2f: {
                }
                break;
                case Opcode.i2b: {
                }
                break;
                case Opcode.i2c: {
                }
                break;
                case Opcode.i2s: {
                }
                break;
                case Opcode.lcmp: {
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
                case Opcode.fcmpl: {
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
                case Opcode.fcmpg: {
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
                case Opcode.dcmpl: {
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
                case Opcode.dcmpg: {
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
                case Opcode.ifeq: {
                    int ifeqValue = operandStack.popInt();
                    if(ifeqValue == 0){
                        int offset = code.readU2();
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }

                }
                break;
                case Opcode.lconst_1: {
                    operandStack.putLong(1L);
                }
                break;
                case Opcode.fconst_0: {
                    operandStack.putFloat(0.0f);
                }
                break;
                case Opcode.fconst_1: {
                    operandStack.putFloat(1.0f);
                }
                break;
                case Opcode.fconst_2: {
                    operandStack.putFloat(2.0f);
                }
                break;
                case Opcode.dconst_0: {
                    operandStack.putDouble(0.0);
                }
                break;
                case Opcode.dconst_1: {
                    operandStack.putDouble(1.0);
                }
                break;
                case Opcode.iload_0: {
                    operandStack.putInt(localVars.getIntByIndex(0));
                }
                break;
                case Opcode.iload_1: {
                    operandStack.putInt(localVars.getIntByIndex(1));
                }
                break;
                case Opcode.iload_2: {
                    operandStack.putInt(localVars.getIntByIndex(2));
                }
                break;
                case Opcode.iload_3: {
                    operandStack.putInt(localVars.getIntByIndex(3));
                }
                break;
                case Opcode.lload_0: {
                    long loadValue = localVars.getLongByIndex(0);
                    operandStack.putLong(loadValue);
                }
                break;
                case Opcode.lload_1: {
                    long loadValue = localVars.getLongByIndex(1);
                    operandStack.putLong(loadValue);
                }
                break;
                case Opcode.aload_0: {
                    operandStack.putJObject(localVars.getJObject(0));
                }
                break;
                case Opcode.aload_1: {
                    operandStack.putJObject(localVars.getJObject(1));
                }
                break;
                case Opcode.aload_2: {
                    operandStack.putJObject(localVars.getJObject(2));
                }
                break;
                case Opcode.aload_3: {
                    operandStack.putJObject(localVars.getJObject(3));
                }
                break;
                case Opcode.iaload: {
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ObjectFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    operandStack.putInt(arrayFields.getIntByIndex(index));
                }
                break;
                case Opcode.laload: {
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ObjectFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    /*long数组中，一个long占2个slot，定位是，偏移*2*/
                    operandStack.putLong(arrayFields.getLongByIndex(2 * index));
                }
                break;
                case Opcode.astore: {
                    int index = code.consumeU1();
                    localVars.putJObject(index, operandStack.popJObject());
                }
                break;
                case Opcode.istore_0: {
                    localVars.putIntByIndex(0, operandStack.popInt());
                }
                break;
                case Opcode.istore_1: {
                    localVars.putIntByIndex(1, operandStack.popInt());
                }
                break;
                case Opcode.istore_2: {
                    localVars.putIntByIndex(2, operandStack.popInt());
                }
                break;
                case Opcode.istore_3: {
                    localVars.putIntByIndex(3, operandStack.popInt());
                }
                break;
                case Opcode.lstore_0: {
                    localVars.putLong(0, operandStack.popLong());
                }
                break;
                case Opcode.dstore_3: {
                    localVars.putDouble(3, operandStack.popDouble());
                }
                break;
                case Opcode.astore_0: {
                    localVars.putJObject(0, operandStack.popJObject());
                }
                break;
                case Opcode.astore_1: {
                    localVars.putJObject(1, operandStack.popJObject());
                }
                break;
                case Opcode.astore_2: {
                    localVars.putJObject(2, operandStack.popJObject());
                }
                break;
                case Opcode.astore_3: {
                    localVars.putJObject(3, operandStack.popJObject());
                }
                break;
                case Opcode.iastore: {
                    int value = operandStack.popInt();
                    int index = operandStack.popInt();
                    JObject arrayObject = operandStack.popJObject();
                    ObjectFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
                    arrayFields.putIntByIndex(index, value);
                }
                break;
                case Opcode.dup_x1: {
                }
                break;
                case Opcode.dup_x2: {
                }
                break;
                case Opcode.dup2: {
                }
                break;
                case Opcode.dup2_x1: {
                }
                break;
                case Opcode.dup2_x2: {
                }
                break;
                case Opcode.swap: {
                }
                break;
                case Opcode.fmul: {
                }
                break;
                case Opcode.dmul: {
                    double var1 = operandStack.popDouble();
                    double var0 = operandStack.popDouble();
                    double sum = var0 * var1;
                    operandStack.putDouble(sum);
                }
                break;
                case Opcode.idiv: {
                }
                break;
                case Opcode.ldiv: {
                }
                break;
                case Opcode.fdiv: {
                }
                break;
                case Opcode.ddiv: {
                }
                break;
                case Opcode.ishr: {
                }
                break;
                case Opcode.lshr: {
                }
                break;
                case Opcode.iushr: {
                }
                break;
                case Opcode.lushr: {
                }
                break;
                case Opcode.iand: {
                }
                break;
                case Opcode.land: {
                }
                break;
                case Opcode.l2d: {
                }
                break;
                case Opcode.f2i: {
                }
                break;
                case Opcode.f2l: {
                }
                break;
                case Opcode.f2d: {
                }
                break;
                case Opcode.d2i: {
                }
                break;
                case Opcode.d2l: {
                }
                break;
                case Opcode.ifne: {
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 != 0){
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.iflt: {
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 < 0){
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.ifge: {
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 >= 0){
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.ifgt: {
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 > 0){
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.ifle: {
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 <= 0){
                        code.pcAddBackOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.if_icmpeq: {
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
                case Opcode.if_icmpne: {
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
                case Opcode.if_icmplt: {
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
                case Opcode.if_icmpge: {
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
                case Opcode.if_icmpgt: {
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
                case Opcode.if_icmple: {
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
                case Opcode.if_acmpeq: {

                }
                break;
                case Opcode.if_acmpne: {

                }
                break;
                case Opcode.goto_: {
                    short offset = code.readU2();
                    code.pcAddBackOne(offset );
                }
                break;
                case Opcode.jsr: {
                }
                break;
                case Opcode.ret: {
                }
                break;
                case Opcode.tableswitch: {
                }
                break;
                case Opcode.lookupswitch: {
                }
                break;
                case Opcode.ireturn: {
                }
                break;
                case Opcode.lreturn: {
                    JavaFrame curFrame = jThread.popFrame();
                    JavaFrame invokerFrame = jThread.getTopFrame();
                    long val = operandStack.popLong();
                    invokerFrame.operandStack.putLong(val);
                    return;
                }
                case Opcode.freturn: {
                }
                break;
                case Opcode.dreturn: {
                }
                break;
                case Opcode.areturn: {
                }
                break;
                case Opcode.return_: {
                    jThread.popFrame();
                    return;
                }
                case Opcode.getstatic: {
                    short staticIndex = code.consumeU2();
                    ClassFile classFile = javaClass.getClassFile();
                    CONSTANT_Base[] constant_bases = classFile.constant_pool.cp_info;
                    CONSTANT_Base constant_base = constant_bases[staticIndex - 1];
                    Ref fieldRef = processRef(javaClass, constant_base);

                    field_info field_info = parseFieldRef(fieldRef);
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
                case Opcode.putstatic: {
                }
                break;
                case Opcode.getfield: {
                    short fieldCpIndex = code.consumeU2();
                    CONSTANT_Base[] constant_bases = javaClass.getClassFile().constant_pool.cp_info;
                    CONSTANT_Base constant_fieldref = constant_bases[fieldCpIndex - 1];
                    getField(javaClass,constant_fieldref);
                }
                break;
                case Opcode.putfield: {
                    short fieldCpIndex = code.consumeU2();
                    CONSTANT_Base[] constant_bases = javaClass.getClassFile().constant_pool.cp_info;
                    CONSTANT_Base constant_fieldref = constant_bases[fieldCpIndex - 1];
                    putField(javaClass,constant_fieldref);
                }
                break;
                case Opcode.invokevirtual: {
                    short invokeIndex = code.consumeU2();
                    CONSTANT_Base[] constant_bases = javaClass.getClassFile().constant_pool.cp_info;
                    CONSTANT_Base constant_methodref = constant_bases[invokeIndex - 1];
                    invokeVirtual(javaClass,constant_methodref);
                }
                break;
                case Opcode.invokespecial: {
                    short invokeIndex = code.consumeU2();
                    CONSTANT_Base[] constant_bases = javaClass.getClassFile().constant_pool.cp_info;
                    CONSTANT_Base constant_methodref = constant_bases[invokeIndex - 1];
                    invokeSpecial(javaClass,constant_methodref);

                }
                break;
                case Opcode.invokestatic: {
                    short invokeIndex = code.consumeU2();
                    CONSTANT_Base[] constant_bases = javaClass.getClassFile().constant_pool.cp_info;
                    CONSTANT_Base constant_base = constant_bases[invokeIndex - 1];
                    invokeStatic(javaClass,constant_base);

                }
                break;
                case Opcode.invokeinterface: {
                }
                break;
                case Opcode.xxxunusedxxx: {
                }
                break;
                case Opcode.new_: {
                    int newIndex = code.consumeU2();
                    CONSTANT_Base[] constant_bases = javaClass.getClassFile().constant_pool.cp_info;
                    CONSTANT_Base constant_base = constant_bases[newIndex - 1];
                    int name_index = TypeUtils.byteArr2Int(((CONSTANT_Class) constant_base).name_index.u2);
                    CONSTANT_Utf8 constant_utf8 = (CONSTANT_Utf8) constant_bases[name_index - 1];
                    JObject jObject = execNew(javaClass,constant_utf8);
                    operandStack.putJObject(jObject);
                }
                break;
                case newarray: {
                    int arrayType = code.consumeU1();
                    int count = operandStack.popInt();
                    JObject jArray = newarray(arrayType,count);
                    operandStack.putJObject(jArray);
                }
                break;
                case Opcode.anewarray: {
                }
                break;
                case Opcode.arraylength: {
                    JObject arrayJObject = operandStack.popJObject();
                    ObjectFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayJObject.offset);
                    int size = getArraySize(arrayJObject.javaClass, arrayFields.slots.length);
                    operandStack.putInt(size);
                }
                break;
                case Opcode.athrow: {
                }
                break;
                case Opcode.checkcast: {
                    code.consumeU2();
                }
                break;
                case Opcode.instanceof_: {
                }
                break;
                case Opcode.monitorenter: {
                }
                break;
                case Opcode.monitorexit: {
                }
                break;
                case Opcode.wide: {
                }
                break;
                case Opcode.multianewarray: {
                }
                break;
                case Opcode.ifnull: {
                }
                break;
                case Opcode.ifnonnull: {
                }
                break;
                case Opcode.goto_w: {
                }
                break;
                case Opcode.jsr_w: {
                }
                break;
                case Opcode.breakpoint: {
                }
                break;
                case Opcode.impdep1: {
                }
                break;
                case Opcode.impdep2: {
                }
                break;
            }
        }
    }

    private int getArraySize(JavaClass javaClass, int length) {
        String className = javaClass.classPath;
        int size = length;

        if (className.startsWith("[J") || className.startsWith("[D")){
            size = size / 2;
        }
        return size;
    }


    private void getField(JavaClass javaClass, CONSTANT_Base constant_fieldref) {
        OperandStack operandStack = jThread.getTopFrame().operandStack;
        Ref fieldRef = processRef(javaClass, constant_fieldref);
        JavaClass classOfCurField = runTimeEnv.methodArea.findClass(fieldRef.className);
        if(classOfCurField == null){
            runTimeEnv.methodArea.loadClass(fieldRef.className);
            runTimeEnv.methodArea.linkClass(fieldRef.className);
            runTimeEnv.methodArea.initClass(fieldRef.className);
        }
        field_info field_info = classOfCurField.findField(fieldRef.refName,fieldRef.descriptorName);
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
    private void putField(JavaClass javaClass, CONSTANT_Base constant_fieldref) {
        OperandStack operandStack = jThread.getTopFrame().operandStack;
        Ref fieldRef = processRef(javaClass, constant_fieldref);
        field_info field_info = javaClass.findField(fieldRef.refName,fieldRef.descriptorName);
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
        }else if(s == 'L' || s == '['){
            /*bug*/
            JObject val = operandStack.popJObject();
            JObject jObject = operandStack.popJObject();
            ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
            objectFields.putJObject(field_info.slotId, val);
        }
//        else if( s == '['){
//            JArray val = operandStack.popJArray();
//            JObject jObject = operandStack.popJObject();
//            ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(jObject.offset);
//            arrayFields.putJArray(field_info.slotId, val);
//        }
    }


    /**
     * 解析字段
     * @param fieldRef
     */
    private field_info parseFieldRef(Ref fieldRef ) {
       // MethodArea methodArea = runTimeEnv.methodArea;
        JavaClass javaClass = runTimeEnv.methodArea.loadClass(fieldRef.className);
        runTimeEnv.methodArea.linkClass(fieldRef.className);
        runTimeEnv.methodArea.initClass(fieldRef.className);
        JavaClass javaClass1 = runTimeEnv.methodArea.findClass(fieldRef.className);
        field_info field_info = javaClass1.findField(fieldRef.refName, fieldRef.descriptorName);
        return field_info;
    }

    private void invokeSpecial(JavaClass javaClass, CONSTANT_Base constant_methodref) {
        Ref methodRef = processRef(javaClass, constant_methodref);

        method_info method_info = parseMethodRef(methodRef);
        /*调用传递参数 如(J)J*/
        Descriptor descriptor = processDescriptor(methodRef.descriptorName);
        JObject jObject = jThread.getTopFrame().operandStack.getJObjectFromTop(method_info.argSlotCount - 1);

        CallSite callSite = new CallSite();
        callSite.setCallSite( method_info);
        OperandStack invokerStack = jThread.getTopFrame().operandStack;
        jThread.pushFrame(callSite.max_stack, callSite.max_locals);
        JavaFrame curFrame = jThread.getTopFrame();
        LocalVars curLocalVars = curFrame.localVars;

        /*调用传递参数*/
        int slotCount = calParametersSlot(method_info, descriptor.parameters);

        for(int i = 0; i < slotCount; i++){
            curLocalVars.putSlot(slotCount - 1 - i, invokerStack.popSlot());
        }

        executeByteCode(jThread, method_info.javaClass, callSite.code, TypeUtils.byteArr2Int(callSite.code_length.u4));

    }


    private void invokeVirtual(JavaClass javaClass, CONSTANT_Base constant_methodref) {
        Ref methodRef = processRef(javaClass, constant_methodref);

        method_info method_info = parseMethodRef(methodRef);
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
        /*类似 Father object = new Son();son 存在方法时，需要调用son的方法，而不是father的*/
        if(!methodRef.className.equals(jObject.javaClass.classPath)){
            //method_info concreteClassMethod = jObject.javaClass.findMethod(methodRef.refName, methodRef.descriptorName);
            methodRef.className = jObject.javaClass.classPath;
            method_info = parseMethodRef(methodRef);
        }

        CallSite callSite = new CallSite();
        callSite.setCallSite( method_info);
        OperandStack invokerStack = jThread.getTopFrame().operandStack;
        jThread.pushFrame(callSite.max_stack, callSite.max_locals);
        JavaFrame curFrame = jThread.getTopFrame();
        LocalVars curLocalVars = curFrame.localVars;

        /*调用传递参数*/
        int slotCount = calParametersSlot(method_info, descriptor.parameters);

        for(int i = 0; i < slotCount; i++){
            curLocalVars.putSlot(slotCount - 1 - i, invokerStack.popSlot());
        }

        executeByteCode(jThread, method_info.javaClass, callSite.code, TypeUtils.byteArr2Int(callSite.code_length.u4));
    }

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
        }else {
            System.out.println("println " + descriptor);
        }
        operandStack.popJObject();
    }


    /**
     * 解析方法:将方法的argSlotCount和javaClass赋值
     * @param methodRef
     */
    private method_info parseMethodRef(Ref methodRef) {
        runTimeEnv.methodArea.loadClass(methodRef.className);
        runTimeEnv.methodArea.linkClass(methodRef.className);
        runTimeEnv.methodArea.initClass(methodRef.className);
        JavaClass javaClass = runTimeEnv.methodArea.findClass(methodRef.className);
        method_info method_info = javaClass.findMethod(methodRef.refName, methodRef.descriptorName);
        method_info.javaClass = javaClass;
        /*argSlotCount未赋值过，则赋值*/
        if(method_info.argSlotCount == -1){
            Descriptor descriptor = processDescriptor(methodRef.descriptorName);
            int slotCount = calParametersSlot(method_info, descriptor.parameters);
            method_info.argSlotCount = slotCount;

        }
        return method_info;
    }

    private void invokeStatic(JavaClass javaClass, CONSTANT_Base constant_base) {

        Ref ref = processRef(javaClass, constant_base);

       // method_info method_info = javaClass.findMethod(methodName, descriptorName);
        method_info method_info = javaClass.findMethod(ref.refName, ref.descriptorName);
        if (method_info == null){
            return ;
        }


        CallSite callSite = new CallSite();
        callSite.setCallSite( method_info);
        OperandStack invokerStack = jThread.getTopFrame().operandStack;
        jThread.pushFrame(callSite.max_stack, callSite.max_locals);
        JavaFrame curFrame = jThread.getTopFrame();
        LocalVars curLocalVars = curFrame.localVars;

        /*调用传递参数*/
        Descriptor descriptor = processDescriptor(ref.descriptorName);

        int slotCount = calParametersSlot(method_info, descriptor.parameters);

        for(int i = 0; i < slotCount; i++){
            curLocalVars.putSlot(slotCount - 1 - i, invokerStack.popSlot());
        }

        executeByteCode(jThread, javaClass, callSite.code, TypeUtils.byteArr2Int(callSite.code_length.u4));

    }

    /**
     * 传入CONSTANT_Base(子类：CONSTANT_Fieldref、CONSTANT_Methodref)，返回className，descriptorName，methodName(或fieldName)
     */
    public Ref processRef(JavaClass javaClass, CONSTANT_Base constant_base){
        Ref ref = new Ref();
        CONSTANT_Base[] constant_bases = javaClass.getClassFile().constant_pool.cp_info;

        int class_index = 0;
        int name_and_type_index = 0;
        if(constant_base instanceof CONSTANT_Methodref){
            CONSTANT_Methodref methodref  = (CONSTANT_Methodref) constant_base;
            class_index = TypeUtils.byteArr2Int(methodref.class_index.u2);
            name_and_type_index = TypeUtils.byteArr2Int(methodref.name_and_type_index.u2);
        }else if(constant_base instanceof CONSTANT_Fieldref){
            CONSTANT_Fieldref fieldref  = (CONSTANT_Fieldref) constant_base;
            class_index = TypeUtils.byteArr2Int(fieldref.class_index.u2);
            name_and_type_index = TypeUtils.byteArr2Int(fieldref.name_and_type_index.u2);
        }

        CONSTANT_Class constant_class = (CONSTANT_Class) constant_bases[class_index - 1];
        CONSTANT_NameAndType constant_nameAndType = (CONSTANT_NameAndType)constant_bases[name_and_type_index - 1];

        CONSTANT_Utf8 methodNameUtf8 = (CONSTANT_Utf8)constant_bases[TypeUtils.byteArr2Int(constant_nameAndType.name_index.u2) - 1];
        CONSTANT_Utf8 descriptorNameUtf8 = (CONSTANT_Utf8)constant_bases[TypeUtils.byteArr2Int(constant_nameAndType.descriptor_index.u2) - 1];
        CONSTANT_Utf8 classNameUtf8 = (CONSTANT_Utf8)constant_bases[TypeUtils.byteArr2Int(constant_class.name_index.u2) - 1];

        ref.className = TypeUtils.u12String(classNameUtf8.bytes);
        ref.descriptorName = TypeUtils.u12String(descriptorNameUtf8.bytes);
        ref.refName = TypeUtils.u12String(methodNameUtf8.bytes);
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
                    returnType = T_BYTE;
                }
                break;
                case 'D':{
                    returnType = T_BYTE;
                }
                break;
                case 'F':{
                    returnType = T_BYTE;
                }
                break;
                case 'I':{
                    returnType = T_BYTE;
                }
                break;
                case 'J':{
                    returnType = T_BYTE;
                }
                break;
                case 'S':{
                    returnType = T_BYTE;
                }
                break;
                case 'Z':{
                    returnType = T_BYTE;
                }
                break;
                case 'V':{
                    returnType = T_BYTE;
                }
                break;
                case '[': {
                    returnType = T_BYTE;
                }
                break;
                case 'L': {
                    returnType = T_BYTE;
                }
                break;
            }
            i++;
        }
        descriptor.parameters = parameters;
        descriptor.returnType = returnType;
        return descriptor;
    }

    private int calParametersSlot(method_info method_info, List<Integer> parameters){
        int parametersCount = parameters.size();

        int slotCount = parametersCount;
        for(int i = 0; i < parametersCount; i++){
            if(parameters.get(i) == TypeCode.T_LONG
                    ||parameters.get(i) == TypeCode.T_DOUBLE){
                slotCount ++;
            }
        }
        if(!MethodArea.isStatic(method_info.access_flags)){
            slotCount ++;/*'this' 引用*/
        }
        return slotCount;
    }

    private JObject execNew(JavaClass javaClass,CONSTANT_Utf8 constant_utf8) {
        String className = TypeUtils.u12String(constant_utf8.bytes);
        if(runTimeEnv.methodArea.findClass(className) == null){
            runTimeEnv.methodArea.loadClass(className);
            runTimeEnv.methodArea.linkClass(className);
            runTimeEnv.methodArea.initClass(className);
        }
        return runTimeEnv.javaHeap.createJObject(runTimeEnv.methodArea.findClass(className));
    }

    private JObject newarray( int arrayType, int count) {
        JavaClass arrayClass = getPrimitiveArrayClass(arrayType, count);

        return runTimeEnv.javaHeap.createJArray(arrayClass,arrayType, count);
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
