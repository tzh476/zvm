package com.zvm.interpreter;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 懒得写了，google了来源：
 * https://docs.atlassian.com/atlassian-plugins/2.5.5/atlassian-plugins-osgi/xref/com/atlassian/plugin/osgi/util/OpCodes.html
 * https://github.com/alibaba/atlas/blob/master/atlas-gradle-plugin/dexpatch/src/main/java/com/taobao/android/dx/cf/code/ByteOps.java
 */
public class Opcode1 implements Serializable {
      int NOP = 0x00;
      int ACONST_NULL = 0x01;
      int ICONST_M1 = 0x02;
      int ICONST_0 = 0x03;
      int ICONST_1 = 0x04;
      int ICONST_2 = 0x05;
      int ICONST_3 = 0x06;
      int ICONST_4 = 0x07;
      int ICONST_5 = 0x08;
      int LCONST_0 = 0x09;
      int BIPUSH = 0x10;
      int SIPUSH = 0x11;
      int LDC = 0x12;
      int LDC_W = 0x13;
      int LDC2_W = 0x14;
      int ILOAD = 0x15;
      int LLOAD = 0x16;
      int FLOAD = 0x17;
      int DLOAD = 0x18;
      int ALOAD = 0x19;
      int LLOAD_2 = 0x20;
      int LLOAD_3 = 0x21;
      int FLOAD_0 = 0x22;
      int FLOAD_1 = 0x23;
      int FLOAD_2 = 0x24;
      int FLOAD_3 = 0x25;
      int DLOAD_0 = 0x26;
      int DLOAD_1 = 0x27;
      int DLOAD_2 = 0x28;
      int DLOAD_3 = 0x29;
      int FALOAD = 0x30;
      int DALOAD = 0x31;
      int AALOAD = 0x32;
      int BALOAD = 0x33;
      int CALOAD = 0x34;
      int SALOAD = 0x35;
      int ISTORE = 0x36;
      int LSTORE = 0x37;
      int FSTORE = 0x38;
      int DSTORE = 0x39;
      int LSTORE_1 = 0x40;
      int LSTORE_2 = 0x41;
      int LSTORE_3 = 0x42;
      int FSTORE_0 = 0x43;
      int FSTORE_1 = 0x44;
      int FSTORE_2 = 0x45;
      int FSTORE_3 = 0x46;
      int DSTORE_0 = 0x47;
      int DSTORE_1 = 0x48;
      int DSTORE_2 = 0x49;
      int LASTORE = 0x50;
      int FASTORE = 0x51;
      int DASTORE = 0x52;
      int AASTORE = 0x53;
      int BASTORE = 0x54;
      int CASTORE = 0x55;
      int SASTORE = 0x56;
      int POP = 0x57;
      int POP2 = 0x58;
      int DUP = 0x59;
      int IADD = 0x60;
      int LADD = 0x61;
      int FADD = 0x62;
      int DADD = 0x63;
      int ISUB = 0x64;
      int LSUB = 0x65;
      int FSUB = 0x66;
      int DSUB = 0x67;
      int IMUL = 0x68;
      int LMUL = 0x69;
      int IREM = 0x70;
      int LREM = 0x71;
      int FREM = 0x72;
      int DREM = 0x73;
      int INEG = 0x74;
      int LNEG = 0x75;
      int FNEG = 0x76;
      int DNEG = 0x77;
      int ISHL = 0x78;
      int LSHL = 0x79;
      int IOR = 0x80;
      int LOR = 0x81;
      int IXOR = 0x82;
      int LXOR = 0x83;
      int IINC = 0x84;
      int I2L = 0x85;
      int I2F = 0x86;
      int I2D = 0x87;
      int L2I = 0x88;
      int L2F = 0x89;
      int D2F = 0x90;
      int I2B = 0x91;
      int I2C = 0x92;
      int I2S = 0x93;
      int LCMP = 0x94;
      int FCMPL = 0x95;
      int FCMPG = 0x96;
      int DCMPL = 0x97;
      int DCMPG = 0x98;
      int IFEQ = 0x99;
      int LCONST_1 = 0x0a;
      int FCONST_0 = 0x0b;
      int FCONST_1 = 0x0c;
      int FCONST_2 = 0x0d;
      int DCONST_0 = 0x0e;
      int DCONST_1 = 0x0f;
      int ILOAD_0 = 0x1a;
      int ILOAD_1 = 0x1b;
      int ILOAD_2 = 0x1c;
      int ILOAD_3 = 0x1d;
      int LLOAD_0 = 0x1e;
      int LLOAD_1 = 0x1f;
      int ALOAD_0 = 0x2a;
      int ALOAD_1 = 0x2b;
      int ALOAD_2 = 0x2c;
      int ALOAD_3 = 0x2d;
      int IALOAD = 0x2e;
      int LALOAD = 0x2f;
      int ASTORE = 0x3a;
      int ISTORE_0 = 0x3b;
      int ISTORE_1 = 0x3c;
      int ISTORE_2 = 0x3d;
      int ISTORE_3 = 0x3e;
      int LSTORE_0 = 0x3f;
      int DSTORE_3 = 0x4a;
      int ASTORE_0 = 0x4b;
      int ASTORE_1 = 0x4c;
      int ASTORE_2 = 0x4d;
      int ASTORE_3 = 0x4e;
      int IASTORE = 0x4f;
      int DUP_X1 = 0x5a;
      int DUP_X2 = 0x5b;
      int DUP2 = 0x5c;
      int DUP2_X1 = 0x5d;
      int DUP2_X2 = 0x5e;
      int SWAP = 0x5f;
      int FMUL = 0x6a;
      int DMUL = 0x6b;
      int IDIV = 0x6c;
      int LDIV = 0x6d;
      int FDIV = 0x6e;
      int DDIV = 0x6f;
      int ISHR = 0x7a;
      int LSHR = 0x7b;
      int IUSHR = 0x7c;
      int LUSHR = 0x7d;
      int IAND = 0x7e;
      int LAND = 0x7f;
      int L2D = 0x8a;
      int F2I = 0x8b;
      int F2L = 0x8c;
      int F2D = 0x8d;
      int D2I = 0x8e;
      int D2L = 0x8f;
      int IFNE = 0x9a;
      int IFLT = 0x9b;
      int IFGE = 0x9c;
      int IFGT = 0x9d;
      int IFLE = 0x9e;
      int IF_ICMPEQ = 0x9f;
      int IF_ICMPNE = 0xa0;
      int IF_ICMPLT = 0xa1;
      int IF_ICMPGE = 0xa2;
      int IF_ICMPGT = 0xa3;
      int IF_ICMPLE = 0xa4;
      int IF_ACMPEQ = 0xa5;
      int IF_ACMPNE = 0xa6;
      int GOTO_ = 0xa7;
      int JSR = 0xa8;
      int RET = 0xa9;
      int TABLESWITCH = 0xaa;
      int LOOKUPSWITCH = 0xab;
      int IRETURN = 0xac;
      int LRETURN = 0xad;
      int FRETURN = 0xae;
      int DRETURN = 0xaf;
      int ARETURN = 0xb0;
      int RETURN_ = 0xb1;
      int GETSTATIC = 0xb2;
      int PUTSTATIC = 0xb3;
      int GETFIELD = 0xb4;
      int PUTFIELD = 0xb5;
      int INVOKEVIRTUAL = 0xb6;
      int INVOKESPECIAL = 0xb7;
      int INVOKESTATIC = 0xb8;
      int INVOKEINTERFACE = 0xb9;
      int XXXUNUSEDXXX = 0xba;
      int NEW_ = 0xbb;
      int NEWARRAY = 0xbc;
      int ANEWARRAY = 0xbd;
      int ARRAYLENGTH = 0xbe;
      int ATHROW = 0xbf;
      int CHECKCAST = 0xc0;
      int INSTANCEOF_ = 0xc1;
      int MONITORENTER = 0xc2;
      int MONITOREXIT = 0xc3;
      int WIDE = 0xc4;
      int MULTIANEWARRAY = 0xc5;
      int IFNULL = 0xc6;
      int IFNONNULL = 0xc7;
      int GOTO_W = 0xc8;
      int JSR_W = 0xc9;
      int BREAKPOINT = 0xca;
      /*  int	impdep1			= 0xfe;*/
      int INVOKENATIVE = 0xfe;
      int IMPDEP2 = 0xff;


      static HashMap<String,Double> opcodeMap;


      static {
            Gson gson = new Gson();
            String jsonStr = gson.toJson(new Opcode1());
            opcodeMap = gson.fromJson(jsonStr, HashMap.class);
      }

      /**
       * 获得opcode助记符
       */
      public static String getMnemonic(int index){
            for (Map.Entry<String,Double> entry:opcodeMap.entrySet()) {
                  Double d = entry.getValue();
                  if(d == index){
                        return entry.getKey();
                  }
            }
            return null;
      }
}
