package com.zvm.interpreter;

import com.google.gson.Gson;
import com.zvm.basestruct.U1;
import com.zvm.classfile.*;
import com.zvm.classfile.constantpool.*;
import com.zvm.instruction.Instruction;
import com.zvm.instruction.Opcode;
import com.zvm.instruction.Opcode1;
import com.zvm.instruction.loadandstore.constant.*;
import com.zvm.instruction.loadandstore.load.*;
import com.zvm.instruction.loadandstore.store.Dstore;
import com.zvm.instruction.loadandstore.store.Fstore;
import com.zvm.instruction.loadandstore.store.Istore;
import com.zvm.instruction.loadandstore.store.Lstore;
import com.zvm.instruction.methodinvocation.InvokeSpecial;
import com.zvm.instruction.methodinvocation.InvokeStatic;
import com.zvm.instruction.methodinvocation.InvokeVirtual;
import com.zvm.instruction.objectcreatemanipulate.*;
import com.zvm.jnative.NativeUtils;
import com.zvm.memory.ArrayFields;
import com.zvm.memory.MethodArea;
import com.zvm.runtime.*;
import com.zvm.runtime.struct.JObject;
import com.zvm.basestruct.TypeCode;
import com.zvm.utils.TypeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zvm.basestruct.TypeCode.*;

public class Interpreter {

    public RunTimeEnv runTimeEnv;
    public JThread jThread ;
    public Map<Integer, Instruction> instructionMap;


    public Interpreter(RunTimeEnv runTimeEnv){
        this.runTimeEnv = runTimeEnv;
        jThread = new JThread();
    }

    /**
     * 初始化所有指令
     */
    public void initInstructions(){
        instructionMap = new HashMap<>();
        /** 对象创建和操作指令*/
        instructionMap.put(Opcode.NEW_, new New());
        instructionMap.put(Opcode.GETSTATIC, new GetStatic());
        instructionMap.put(Opcode.PUTSTATIC, new PutStatic());
        instructionMap.put(Opcode.GETFIELD, new GetField());
        instructionMap.put(Opcode.PUTFIELD, new PutField());
        instructionMap.put(Opcode.NEWARRAY, new NewArray());
        instructionMap.put(Opcode.ANEWARRAY, new ANewArray());

        /** 加载和存储指令*/
        instructionMap.put(Opcode.NOP, new Nop());
        instructionMap.put(Opcode.LDC, new Ldc());
        instructionMap.put(Opcode.LDC_W, new Ldc2_W());
        instructionMap.put(Opcode.LDC2_W, new Ldc2_W());
        instructionMap.put(Opcode.ACONST_NULL, new Aconst_null());
        instructionMap.put(Opcode.ICONST_0, new Iconst_0());
        instructionMap.put(Opcode.ICONST_1, new Iconst_1());
        instructionMap.put(Opcode.ICONST_2, new Iconst_2());
        instructionMap.put(Opcode.ICONST_3, new Iconst_3());
        instructionMap.put(Opcode.ICONST_4, new Iconst_4());
        instructionMap.put(Opcode.ICONST_5, new Iconst_5());
        instructionMap.put(Opcode.ICONST_M1, new Iconst_M1());
        instructionMap.put(Opcode.FCONST_0, new Fconst_0());
        instructionMap.put(Opcode.FCONST_1, new Fconst_1());
        instructionMap.put(Opcode.FCONST_2, new Fconst_2());
        instructionMap.put(Opcode.DCONST_0, new Dconst_0());
        instructionMap.put(Opcode.DCONST_1, new Dconst_1());
        instructionMap.put(Opcode.LCONST_0, new Lconst_0());
        instructionMap.put(Opcode.LCONST_1, new Lconst_1());
        instructionMap.put(Opcode.BIPUSH, new Bipush());
        instructionMap.put(Opcode.SIPUSH, new Sipush());
        instructionMap.put(Opcode.ILOAD, new Iload());
        instructionMap.put(Opcode.ILOAD_0, new Iload_0());
        instructionMap.put(Opcode.ILOAD_1, new Iload_1());
        instructionMap.put(Opcode.ILOAD_2, new Iload_2());
        instructionMap.put(Opcode.ILOAD_3, new Iload_3());
        instructionMap.put(Opcode.FLOAD, new Fload());
        instructionMap.put(Opcode.FLOAD_0, new Fload_0());
        instructionMap.put(Opcode.FLOAD_1, new Fload_1());
        instructionMap.put(Opcode.FLOAD_2, new Fload_2());
        instructionMap.put(Opcode.FLOAD_3, new Fload_3());
        instructionMap.put(Opcode.DLOAD, new Dload());
        instructionMap.put(Opcode.DLOAD_0, new Dload_0());
        instructionMap.put(Opcode.DLOAD_1, new Dload_1());
        instructionMap.put(Opcode.DLOAD_2, new Dload_2());
        instructionMap.put(Opcode.DLOAD_3, new Dload_3());
        instructionMap.put(Opcode.ALOAD, new Aload());
        instructionMap.put(Opcode.ALOAD_0, new Aload_0());
        instructionMap.put(Opcode.ALOAD_1, new Aload_1());
        instructionMap.put(Opcode.ALOAD_2, new Aload_2());
        instructionMap.put(Opcode.ALOAD_3, new Aload_3());
        instructionMap.put(Opcode.LLOAD, new Lload());
        instructionMap.put(Opcode.LLOAD_0, new Lload_0());
        instructionMap.put(Opcode.LLOAD_1, new Lload_1());
        instructionMap.put(Opcode.LLOAD_2, new Lload_2());
        instructionMap.put(Opcode.LLOAD_3, new Lload_3());
        instructionMap.put(Opcode.IALOAD, new Iaload());
        instructionMap.put(Opcode.AALOAD, new Aaload());
        instructionMap.put(Opcode.DALOAD, new Daload());
        instructionMap.put(Opcode.FALOAD, new Faload());
        instructionMap.put(Opcode.BALOAD, new Baload());
        instructionMap.put(Opcode.CALOAD, new Caload());
        instructionMap.put(Opcode.SALOAD, new Saload());

        instructionMap.put(Opcode.ISTORE, new Istore());
        instructionMap.put(Opcode.FSTORE, new Fstore());
        instructionMap.put(Opcode.LSTORE, new Lstore());
        instructionMap.put(Opcode.DSTORE, new Dstore());




        /** 方法调用和返回指令*/
        instructionMap.put(Opcode.INVOKESTATIC, new InvokeStatic());
        instructionMap.put(Opcode.INVOKESPECIAL, new InvokeSpecial());
        instructionMap.put(Opcode.INVOKEVIRTUAL, new InvokeVirtual());

    }

    public void invokeByName(JavaClass javaClass, String name, String descriptor){
        MethodInfo method_info = javaClass.findMethod(name, descriptor);
        if (method_info == null){
            return ;
        }
        CallSite callSite = new CallSite();
        callSite.setCallSite( method_info);

        jThread.pushFrame(callSite.maxStack, callSite.maxLocals);
        executeByteCode(jThread, javaClass, callSite);
    }

    public void executeByteCode(JThread jThread, JavaClass javaClass, CallSite callSite) {
        U1[] codeRaw = callSite.code;
        int codeLength = TypeUtils.byteArr2Int(callSite.codeLength.u4);
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;
        CodeUtils code = new CodeUtils(codeRaw, 0);
        for (; code.getPc() < codeLength; code.pcAdd(1)) {
            int opcodeInt = TypeUtils.byteArr2Int(codeRaw[code.getPc()].u1);
            Gson gson = new Gson();
            //System.out.println("pc = " + code.getPc() + " operandStack size "+ operandStack.size);
            //System.out.println("pc = " + code.getPc() + " operandStack "+gson.toJson(operandStack));
            //System.out.println("pc = " + code.getPc() + " localVars size "+ localVars.slots.length);
            //System.out.println("pc = " + code.getPc() + " localVars " + gson.toJson(localVars));
            //System.out.println();
            //System.out.println("pc = " + code.getPc() + " opcode:" + Opcode1.getMnemonic(opcodeInt));

            Instruction instruction = instructionMap.get(opcodeInt);
            if(instruction != null){
                instruction.execute(runTimeEnv, jThread, javaClass, callSite, this, code);
                continue;
            }

            switch (opcodeInt) {

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

                }
                break;
                case Opcode.PUTSTATIC: {

                }
                break;
                case Opcode.GETFIELD: {

                }
                break;
                case Opcode.PUTFIELD: {

                }
                break;
                case Opcode.INVOKEVIRTUAL: {

                }
                break;
                case Opcode.INVOKESPECIAL: {
                    short invokeIndex = code.consumeU2();
                    ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
                    ConstantBase constant_methodref = constant_bases[invokeIndex - 1];
                    Ref methodRef = JavaClass.processRef(javaClass, constant_methodref);

                    invokeSpecial(methodRef);

                }
                break;
                case Opcode.INVOKESTATIC: {

                }
                break;
                case Opcode.INVOKEINTERFACE: {
                }
                break;
                case Opcode.XXXUNUSEDXXX: {
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
                    invokeNative(javaClass, callSite);
                }
                break;
                case Opcode.IMPDEP2: {
                }
                break;
            }
        }
    }


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
        executeByteCode(jThread, method_info.javaClass, callSite);
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

    /**
     * 调用native方法,暂时只支持arraycopy
     * @param javaClass
     * @param callSite
     */
    private void invokeNative(JavaClass javaClass, CallSite callSite) {
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

    /**
     * 计算方法的参数占用Slot的个数
     * @param method_info
     * @param parameters
     * @return
     */
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



}
