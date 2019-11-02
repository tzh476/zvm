package com.zvm.draft;

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
      int	nop				= 0x00;
      int	aconst_null		= 0x01;
      int	iconst_m1		= 0x02;
      int	iconst_0		= 0x03;
      int	iconst_1		= 0x04;
      int	iconst_2		= 0x05;
      int	iconst_3		= 0x06;
      int	iconst_4		= 0x07;
      int	iconst_5		= 0x08;
      int	lconst_0		= 0x09;
      int	bipush			= 0x10;
      int	sipush			= 0x11;
      int	ldc				= 0x12;
      int	ldc_w			= 0x13;
      int	ldc2_w			= 0x14;
      int	iload			= 0x15;
      int	lload			= 0x16;
      int	fload			= 0x17;
      int	dload			= 0x18;
      int	aload			= 0x19;
      int	lload_2			= 0x20;
      int	lload_3			= 0x21;
      int	fload_0			= 0x22;
      int	fload_1			= 0x23;
      int	fload_2			= 0x24;
      int	fload_3			= 0x25;
      int	dload_0			= 0x26;
      int	dload_1			= 0x27;
      int	dload_2			= 0x28;
      int	dload_3			= 0x29;
      int	faload			= 0x30;
      int	daload			= 0x31;
      int	aaload			= 0x32;
      int	baload			= 0x33;
      int	caload			= 0x34;
      int	saload			= 0x35;
      int	istore			= 0x36;
      int	lstore			= 0x37;
      int	fstore			= 0x38;
      int	dstore			= 0x39;
      int	lstore_1		= 0x40;
      int	lstore_2		= 0x41;
      int	lstore_3		= 0x42;
      int	fstore_0		= 0x43;
      int	fstore_1		= 0x44;
      int	fstore_2		= 0x45;
      int	fstore_3		= 0x46;
      int	dstore_0		= 0x47;
      int	dstore_1		= 0x48;
      int	dstore_2		= 0x49;
      int	lastore			= 0x50;
      int	fastore			= 0x51;
      int	dastore			= 0x52;
      int	aastore			= 0x53;
      int	bastore			= 0x54;
      int	castore			= 0x55;
      int	sastore			= 0x56;
      int	pop				= 0x57;
      int	pop2			= 0x58;
      int	dup				= 0x59;
      int	iadd			= 0x60;
      int	ladd			= 0x61;
      int	fadd			= 0x62;
      int	dadd			= 0x63;
      int	isub			= 0x64;
      int	lsub			= 0x65;
      int	fsub			= 0x66;
      int	dsub			= 0x67;
      int	imul			= 0x68;
      int	lmul			= 0x69;
      int	irem			= 0x70;
      int	lrem			= 0x71;
      int	frem			= 0x72;
      int	drem			= 0x73;
      int	ineg			= 0x74;
      int	lneg			= 0x75;
      int	fneg			= 0x76;
      int	dneg			= 0x77;
      int	ishl			= 0x78;
      int	lshl			= 0x79;
      int	ior				= 0x80;
      int	lor				= 0x81;
      int	ixor			= 0x82;
      int	lxor			= 0x83;
      int	iinc			= 0x84;
      int	i2l				= 0x85;
      int	i2f				= 0x86;
      int	i2d				= 0x87;
      int	l2i				= 0x88;
      int	l2f				= 0x89;
      int	d2f				= 0x90;
      int	i2b				= 0x91;
      int	i2c				= 0x92;
      int	i2s				= 0x93;
      int	lcmp			= 0x94;
      int	fcmpl			= 0x95;
      int	fcmpg			= 0x96;
      int	dcmpl			= 0x97;
      int	dcmpg			= 0x98;
      int	ifeq			= 0x99;
      int	lconst_1		= 0x0a;
      int	fconst_0		= 0x0b;
      int	fconst_1		= 0x0c;
      int	fconst_2		= 0x0d;
      int	dconst_0		= 0x0e;
      int	dconst_1		= 0x0f;
      int	iload_0			= 0x1a;
      int	iload_1			= 0x1b;
      int	iload_2			= 0x1c;
      int	iload_3			= 0x1d;
      int	lload_0			= 0x1e;
      int	lload_1			= 0x1f;
      int	aload_0			= 0x2a;
      int	aload_1			= 0x2b;
      int	aload_2			= 0x2c;
      int	aload_3			= 0x2d;
      int	iaload			= 0x2e;
      int	laload			= 0x2f;
      int	astore			= 0x3a;
      int	istore_0		= 0x3b;
      int	istore_1		= 0x3c;
      int	istore_2		= 0x3d;
      int	istore_3		= 0x3e;
      int	lstore_0		= 0x3f;
      int	dstore_3		= 0x4a;
      int	astore_0		= 0x4b;
      int	astore_1		= 0x4c;
      int	astore_2		= 0x4d;
      int	astore_3		= 0x4e;
      int	iastore			= 0x4f;
      int	dup_x1			= 0x5a;
      int	dup_x2			= 0x5b;
      int	dup2			= 0x5c;
      int	dup2_x1			= 0x5d;
      int	dup2_x2			= 0x5e;
      int	swap			= 0x5f;
      int	fmul			= 0x6a;
      int	dmul			= 0x6b;
      int	idiv			= 0x6c;
      int	ldiv			= 0x6d;
      int	fdiv			= 0x6e;
      int	ddiv			= 0x6f;
      int	ishr			= 0x7a;
      int	lshr			= 0x7b;
      int	iushr			= 0x7c;
      int	lushr			= 0x7d;
      int	iand			= 0x7e;
      int	land			= 0x7f;
      int	l2d				= 0x8a;
      int	f2i				= 0x8b;
      int	f2l				= 0x8c;
      int	f2d				= 0x8d;
      int	d2i				= 0x8e;
      int	d2l				= 0x8f;
      int	ifne			= 0x9a;
      int	iflt			= 0x9b;
      int	ifge			= 0x9c;
      int	ifgt			= 0x9d;
      int	ifle			= 0x9e;
      int	if_icmpeq		= 0x9f;
      int	if_icmpne		= 0xa0;
      int	if_icmplt		= 0xa1;
      int	if_icmpge		= 0xa2;
      int	if_icmpgt		= 0xa3;
      int	if_icmple		= 0xa4;
      int	if_acmpeq		= 0xa5;
      int	if_acmpne		= 0xa6;
      int	goto_			= 0xa7;
      int	jsr				= 0xa8;
      int	ret				= 0xa9;
      int	tableswitch		= 0xaa;
      int	lookupswitch	= 0xab;
      int	ireturn			= 0xac;
      int	lreturn			= 0xad;
      int	freturn			= 0xae;
      int	dreturn			= 0xaf;
      int	areturn			= 0xb0;
      int	return_			= 0xb1;
      int	get		= 0xb2;
      int	put		= 0xb3;
      int	getfield		= 0xb4;
      int	putfield		= 0xb5;
      int	invokevirtual	= 0xb6;
      int	invokespecial	= 0xb7;
      int	invoke	= 0xb8;
      int	invokeinterface	= 0xb9;
      int	xxxunusedxxx	= 0xba;
      int	new_			= 0xbb;
      int	newarray		= 0xbc;
      int	anewarray		= 0xbd;
      int	arraylength		= 0xbe;
      int	athrow			= 0xbf;
      int	checkcast		= 0xc0;
      int	instanceof_		= 0xc1;
      int	monitorenter	= 0xc2;
      int	monitorexit		= 0xc3;
      int	wide			= 0xc4;
      int	multianewarray	= 0xc5;
      int	ifnull			= 0xc6;
      int	ifnonnull		= 0xc7;
      int	goto_w			= 0xc8;
      int	jsr_w			= 0xc9;
      int	breakpoint		= 0xca;
      int	impdep1			= 0xfe;
      int	impdep2			= 0xff;


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
