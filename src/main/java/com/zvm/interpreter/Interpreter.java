package com.zvm.interpreter;

import com.google.gson.Gson;
import com.zvm.basestruct.U1;
import com.zvm.classfile.*;
import com.zvm.instruction.Instruction;
import com.zvm.instruction.Opcode;
import com.zvm.instruction.synchronization.MonitorEnter;
import com.zvm.instruction.synchronization.MonitorExit;
import com.zvm.instruction.arithmetic.arithmetic.*;
import com.zvm.instruction.arithmetic.bitwise.*;
import com.zvm.instruction.arithmetic.logic.*;
import com.zvm.instruction.arithmetic.relation.*;
import com.zvm.instruction.arithmetic.unary.Iinc;
import com.zvm.instruction.controltransfer.*;
import com.zvm.instruction.exception.Athrow;
import com.zvm.instruction.loadandstore.Wide;
import com.zvm.instruction.loadandstore.constant.*;
import com.zvm.instruction.loadandstore.load.*;
import com.zvm.instruction.loadandstore.store.*;
import com.zvm.instruction.methodinvocation.*;
import com.zvm.instruction.objectcreatemanipulate.*;
import com.zvm.instruction.oprandstack.*;
import com.zvm.instruction.typeconversion.*;
import com.zvm.runtime.*;
import com.zvm.runtime.struct.JObject;
import com.zvm.utils.TypeUtils;

import java.util.HashMap;
import java.util.Map;

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
        instructionMap.put(Opcode.ARRAYLENGTH, new ArrayLength());
        instructionMap.put(Opcode.CHECKCAST, new CheckCast());
        instructionMap.put(Opcode.INSTANCEOF_, new Instanceof_());
        instructionMap.put(Opcode.MULTIANEWARRAY, new MultiANewArray());

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
        instructionMap.put(Opcode.ISTORE_0, new Istore_0());
        instructionMap.put(Opcode.ISTORE_1, new Istore_1());
        instructionMap.put(Opcode.ISTORE_2, new Istore_2());
        instructionMap.put(Opcode.ISTORE_3, new Istore_3());

        instructionMap.put(Opcode.FSTORE, new Fstore());
        instructionMap.put(Opcode.FSTORE_0, new Fstore_0());
        instructionMap.put(Opcode.FSTORE_1, new Fstore_1());
        instructionMap.put(Opcode.FSTORE_2, new Fstore_2());
        instructionMap.put(Opcode.FSTORE_3, new Fstore_3());

        instructionMap.put(Opcode.ASTORE, new Astore());
        instructionMap.put(Opcode.ASTORE_0, new Astore_0());
        instructionMap.put(Opcode.ASTORE_1, new Astore_1());
        instructionMap.put(Opcode.ASTORE_2, new Astore_2());
        instructionMap.put(Opcode.ASTORE_3, new Astore_3());

        instructionMap.put(Opcode.LSTORE, new Lstore());
        instructionMap.put(Opcode.LSTORE_0, new Lstore_0());
        instructionMap.put(Opcode.LSTORE_1, new Lstore_1());
        instructionMap.put(Opcode.LSTORE_2, new Lstore_2());
        instructionMap.put(Opcode.LSTORE_3, new Lstore_3());

        instructionMap.put(Opcode.DSTORE, new Dstore());
        instructionMap.put(Opcode.DSTORE_0, new Dstore_0());
        instructionMap.put(Opcode.DSTORE_1, new Dstore_1());
        instructionMap.put(Opcode.DSTORE_2, new Dstore_2());
        instructionMap.put(Opcode.DSTORE_3, new Dstore_3());

        instructionMap.put(Opcode.AASTORE, new Aastore());
        instructionMap.put(Opcode.BASTORE, new Bastore());
        instructionMap.put(Opcode.CASTORE, new Castore());
        instructionMap.put(Opcode.DASTORE, new Dastore());
        instructionMap.put(Opcode.FASTORE, new Fastore());
        instructionMap.put(Opcode.IASTORE, new Iastore());
        instructionMap.put(Opcode.LASTORE, new Lastore());
        instructionMap.put(Opcode.SASTORE, new Sastore());
        instructionMap.put(Opcode.WIDE, new Wide());

        /** 方法调用和返回指令*/
        instructionMap.put(Opcode.INVOKESTATIC, new InvokeStatic());
        instructionMap.put(Opcode.INVOKESPECIAL, new InvokeSpecial());
        instructionMap.put(Opcode.INVOKEVIRTUAL, new InvokeVirtual());
        instructionMap.put(Opcode.INVOKENATIVE, new InvokeNative());
        instructionMap.put(Opcode.INVOKEINTERFACE, new InvokeInterface());
        instructionMap.put(Opcode.INVOKEDYNAMIC, new InvokeDynamic());

        /** 运算指令*/
        instructionMap.put(Opcode.IADD, new Iadd());
        instructionMap.put(Opcode.FADD, new Fadd());
        instructionMap.put(Opcode.LADD, new Ladd());
        instructionMap.put(Opcode.DADD, new Dadd());
        instructionMap.put(Opcode.ISUB, new Isub());
        instructionMap.put(Opcode.FSUB, new Fsub());
        instructionMap.put(Opcode.LSUB, new Lsub());
        instructionMap.put(Opcode.DSUB, new Dsub());

        instructionMap.put(Opcode.IMUL, new Imul());
        instructionMap.put(Opcode.LMUL, new Lmul());
        instructionMap.put(Opcode.FMUL, new Fmul());
        instructionMap.put(Opcode.DMUL, new Dmul());
        instructionMap.put(Opcode.IDIV, new Idiv());
        instructionMap.put(Opcode.LDIV, new Ldiv());
        instructionMap.put(Opcode.FDIV, new Fdiv());
        instructionMap.put(Opcode.DDIV, new Ddiv());

        instructionMap.put(Opcode.DCMPG, new Dcmpg());
        instructionMap.put(Opcode.DCMPL, new Dcmpl());
        instructionMap.put(Opcode.FCMPG, new Fcmpg());
        instructionMap.put(Opcode.FCMPL, new Fcmpl());
        instructionMap.put(Opcode.LCMP, new Lcmp());

        instructionMap.put(Opcode.IINC, new Iinc());
            /** 求余 */
        instructionMap.put(Opcode.IREM, new Irem());
        instructionMap.put(Opcode.LREM, new Lrem());
        instructionMap.put(Opcode.FREM, new Frem());
        instructionMap.put(Opcode.DREM, new Drem());
            /** 取反 */
        instructionMap.put(Opcode.INEG, new Ineg());
        instructionMap.put(Opcode.LNEG, new Lneg());
        instructionMap.put(Opcode.FNEG, new Fneg());
        instructionMap.put(Opcode.DNEG, new Dneg());
            /** 位移 */
        instructionMap.put(Opcode.ISHL, new Ishl());
        instructionMap.put(Opcode.LSHL, new Lshl());
        instructionMap.put(Opcode.ISHR, new Ishr());
        instructionMap.put(Opcode.LSHR, new Lshr());
        instructionMap.put(Opcode.IUSHR, new Iushr());
        instructionMap.put(Opcode.LUSHR, new Lushr());

        instructionMap.put(Opcode.IOR, new Ior());
        instructionMap.put(Opcode.LOR, new Lor());
        instructionMap.put(Opcode.IXOR, new Ixor());
        instructionMap.put(Opcode.LXOR, new Lxor());
        instructionMap.put(Opcode.IAND, new Iand());
        instructionMap.put(Opcode.LAND, new Land());


        /** 控制转移 */
        instructionMap.put(Opcode.IFEQ, new Ifeq());
        instructionMap.put(Opcode.IFGE, new Ifge());
        instructionMap.put(Opcode.IFGT, new Ifgt());
        instructionMap.put(Opcode.IFLE, new Ifle());
        instructionMap.put(Opcode.IFLT, new Iflt());
        instructionMap.put(Opcode.IFNE, new Ifne());
        instructionMap.put(Opcode.IFNULL, new Ifnull());
        instructionMap.put(Opcode.IFNONNULL, new Ifnonnull());

        instructionMap.put(Opcode.IF_ICMPEQ, new If_Icmpeq());
        instructionMap.put(Opcode.IF_ICMPGE, new If_Icmpge());
        instructionMap.put(Opcode.IF_ICMPGT, new If_Icmpgt());
        instructionMap.put(Opcode.IF_ICMPLE, new If_Icmple());
        instructionMap.put(Opcode.IF_ICMPLT, new If_Icmplt());
        instructionMap.put(Opcode.IF_ICMPNE, new If_Icmpne());
        instructionMap.put(Opcode.IF_ACMPEQ, new If_Acmpeq());
        instructionMap.put(Opcode.IF_ACMPNE, new If_Acmpne());
        instructionMap.put(Opcode.GOTO_, new Goto_());
        instructionMap.put(Opcode.GOTO_W, new Goto_W());
        instructionMap.put(Opcode.JSR, new Jsr());
        instructionMap.put(Opcode.JSR_W, new Jsr_W());
        instructionMap.put(Opcode.RET, new Ret());
        instructionMap.put(Opcode.TABLESWITCH, new TableSwitch());
        instructionMap.put(Opcode.LOOKUPSWITCH, new LookupSwitch());

        /** 操作数栈管理指令*/
        instructionMap.put(Opcode.DUP, new Dup());
        instructionMap.put(Opcode.DUP_X1, new Dup_X1());
        instructionMap.put(Opcode.DUP_X2, new Dup_X2());
        instructionMap.put(Opcode.DUP2, new Dup2());
        instructionMap.put(Opcode.DUP2_X1, new Dup2_X1());
        instructionMap.put(Opcode.DUP2_X2, new Dup2_X2());
        instructionMap.put(Opcode.POP, new Pop());
        instructionMap.put(Opcode.POP2, new Pop2());
        instructionMap.put(Opcode.SWAP, new Swap());

        /** 类型转换 */
        instructionMap.put(Opcode.D2F, new D2f());
        instructionMap.put(Opcode.D2I, new D2i());
        instructionMap.put(Opcode.D2L, new D2l());
        instructionMap.put(Opcode.F2D, new F2d());
        instructionMap.put(Opcode.F2I, new F2i());
        instructionMap.put(Opcode.F2L, new F2l());
        instructionMap.put(Opcode.I2B, new I2b());
        instructionMap.put(Opcode.I2C, new I2c());
        instructionMap.put(Opcode.I2D, new I2d());
        instructionMap.put(Opcode.I2F, new I2f());
        instructionMap.put(Opcode.I2L, new I2l());
        instructionMap.put(Opcode.I2S, new I2s());
        instructionMap.put(Opcode.L2D, new L2d());
        instructionMap.put(Opcode.L2F, new L2f());
        instructionMap.put(Opcode.L2I, new L2i());

        /** 抛出错误 */
        instructionMap.put(Opcode.ATHROW, new Athrow());

        /** 同步 */
        instructionMap.put(Opcode.MONITORENTER, new MonitorEnter());
        instructionMap.put(Opcode.MONITOREXIT, new MonitorExit());

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
            }
        }
    }


    public void invokeSpecial(Ref methodRef) {
        Instruction instruction = instructionMap.get(Opcode.INVOKESPECIAL);
        ((InvokeSpecial)instruction).invokeSpecial(runTimeEnv, jThread,this, methodRef);
    }


}
