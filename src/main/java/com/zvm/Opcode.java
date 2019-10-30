package com.zvm;

/**
 * 懒得写了，google了来源：
 * https://docs.atlassian.com/atlassian-plugins/2.5.5/atlassian-plugins-osgi/xref/com/atlassian/plugin/osgi/util/OpCodes.html
 * https://github.com/alibaba/atlas/blob/master/atlas-gradle-plugin/dexpatch/src/main/java/com/taobao/android/dx/cf/code/ByteOps.java
 */
public class Opcode {
    final static int	nop				= 0x00;			// [No change]
    // no
    // operation
    final static int	aconst_null		= 0x01;			// ? null pushe
    // reference onto the stack
    final static int	iconst_m1		= 0x02;			// ? -1 loads t
    // value -1
    // onto the stack
    final static int	iconst_0		= 0x03;			// ? 0 loads th
    // value 0
    // onto the stack
    final static int	iconst_1		= 0x04;			// ? 1 loads th
    // value 1
    // onto the stack
    final static int	iconst_2		= 0x05;			// ? 2 loads th
    // value 2
    // onto the stack
    final static int	iconst_3		= 0x06;			// ? 3 loads th
    // value 3
    // onto the stack
    final static int	iconst_4		= 0x07;			// ? 4 loads th
    // value 4
    // onto the stack
    final static int	iconst_5		= 0x08;			// ? 5 loads th
    // value 5
    // onto the stack
    final static int	lconst_0		= 0x09;			// ? 0L pushes
    // 0 onto
    // the stack
    final static int	bipush			= 0x10;			// byte ? value
    // byte
    // onto the stack as an integer
    // value
    final static int	sipush			= 0x11;			// byte1, byte2
    // pushes a
    // signed integer (byte1 << 8 +
    // byte2) onto the stack
    final static int	ldc				= 0x12;			// index ? valu
    // a
    // constant #index from a
    // constant pool (String, int,
    // float or class type) onto the
    // stack
    final static int	ldc_w			= 0x13;			// indexbyte1,
    // indexbyte2 ?
    // value pushes a constant
    // #index from a constant pool
    // (String, int, float or class
    // type) onto the stack (wide
    // index is constructed as
    // indexbyte1 << 8 + indexbyte2)
    final static int	ldc2_w			= 0x14;			// indexbyte1,
    // indexbyte2 ?
    // value pushes a constant
    // #index from a constant pool
    // (double or long) onto the
    // stack (wide index is
    // constructed as indexbyte1 <<
    // 8 + indexbyte2)
    final static int	iload			= 0x15;			// index ? valu
    // an int
    // value from a variable #index
    final static int	lload			= 0x16;			// index ? valu
    // long
    // value from a local variable
    // #index
    final static int	fload			= 0x17;			// index ? valu
    // float
    // value from a local variable
    // #index
    final static int	dload			= 0x18;			// index ? valu
    // double
    // value from a local variable
    // #index
    final static int	aload			= 0x19;			// index ? obje
    // loads a
    // reference onto the stack from
    // a local variable #index
    final static int	lload_2			= 0x20;			// ? value load
    // value
    // from a local variable 2
    final static int	lload_3			= 0x21;			// ? value load
    // value
    // from a local variable 3
    final static int	fload_0			= 0x22;			// ? value load
    // value
    // from local variable 0
    final static int	fload_1			= 0x23;			// ? value load
    // value
    // from local variable 1
    final static int	fload_2			= 0x24;			// ? value load
    // value
    // from local variable 2
    final static int	fload_3			= 0x25;			// ? value load
    // value
    // from local variable 3
    final static int	dload_0			= 0x26;			// ? value load
    // double from
    // local variable 0
    final static int	dload_1			= 0x27;			// ? value load
    // double from
    // local variable 1
    final static int	dload_2			= 0x28;			// ? value load
    // double from
    // local variable 2
    final static int	dload_3			= 0x29;			// ? value load
    // double from
    // local variable 3
    final static int	faload			= 0x30;			// arrayref, in
    // value loads
    // a float from an array
    final static int	daload			= 0x31;			// arrayref, in
    // value loads
    // a double from an array
    final static int	aaload			= 0x32;			// arrayref, in
    // value loads
    // onto the stack a reference
    // from an array
    final static int	baload			= 0x33;			// arrayref, in
    // value loads
    // a byte or Boolean value from
    // an array
    final static int	caload			= 0x34;			// arrayref, in
    // value loads
    // a char from an array
    final static int	saload			= 0x35;			// arrayref, in
    // value load
    // int from array
    final static int	istore			= 0x36;			// index value
    // int value
    // into variable #index
    final static int	lstore			= 0x37;			// index value
    // long
    // value in a local variable
    // #index
    final static int	fstore			= 0x38;			// index value
    // a float
    // value into a local variable
    // #index
    final static int	dstore			= 0x39;			// index value
    // a double
    // value into a local variable
    // #index
    final static int	lstore_1		= 0x40;			// value ? stor
    // value in
    // a local variable 1
    final static int	lstore_2		= 0x41;			// value ? stor
    // value in
    // a local variable 2
    final static int	lstore_3		= 0x42;			// value ? stor
    // value in
    // a local variable 3
    final static int	fstore_0		= 0x43;			// value ? stor
    // float value
    // into local variable 0
    final static int	fstore_1		= 0x44;			// value ? stor
    // float value
    // into local variable 1
    final static int	fstore_2		= 0x45;			// value ? stor
    // float value
    // into local variable 2
    final static int	fstore_3		= 0x46;			// value ? stor
    // float value
    // into local variable 3
    final static int	dstore_0		= 0x47;			// value ? stor
    // double into
    // local variable 0
    final static int	dstore_1		= 0x48;			// value ? stor
    // double into
    // local variable 1
    final static int	dstore_2		= 0x49;			// value ? stor
    // double into
    // local variable 2
    final static int	lastore			= 0x50;			// arrayref, in
    // value ?
    // store a long to an array
    final static int	fastore			= 0x51;			// arreyref, in
    // value ?
    // stores a float in an array
    final static int	dastore			= 0x52;			// arrayref, in
    // value ?
    // stores a double into an array
    final static int	aastore			= 0x53;			// arrayref, in
    // value ?
    // stores into a reference to an
    // array
    final static int	bastore			= 0x54;			// arrayref, in
    // value ?
    // stores a byte or Boolean
    // value into an array
    final static int	castore			= 0x55;			// arrayref, in
    // value ?
    // stores a char into an array
    final static int	sastore			= 0x56;			// arrayref, in
    // value ?
    // store int to array
    final static int	pop				= 0x57;			// value ? disc
    // top
    // value on the stack
    final static int	pop2			= 0x58;			// {value2, val
    // discards
    // the top two values on the
    // stack (or one value, if it is
    // a double or long)
    final static int	dup				= 0x59;			// value ? valu
    // duplicates the value on top
    // of the stack
    final static int	iadd			= 0x60;			// value1, valu
    // result adds
    // two ints together
    final static int	ladd			= 0x61;			// value1, valu
    // result add
    // two longs
    final static int	fadd			= 0x62;			// value1, valu
    // result adds
    // two floats
    final static int	dadd			= 0x63;			// value1, valu
    // result adds
    // two doubles
    final static int	isub			= 0x64;			// value1, valu
    // result int
    // subtract
    final static int	lsub			= 0x65;			// value1, valu
    // result
    // subtract two longs
    final static int	fsub			= 0x66;			// value1, valu
    // result
    // subtracts two floats
    final static int	dsub			= 0x67;			// value1, valu
    // result
    // subtracts a double from
    // another
    final static int	imul			= 0x68;			// value1, valu
    // result
    // multiply two integers
    final static int	lmul			= 0x69;			// value1, valu
    // result
    // multiplies two longs
    final static int	irem			= 0x70;			// value1, valu
    // result
    // logical int remainder
    final static int	lrem			= 0x71;			// value1, valu
    // result
    // remainder of division of two
    // longs
    final static int	frem			= 0x72;			// value1, valu
    // result gets
    // the remainder from a division
    // between two floats
    final static int	drem			= 0x73;			// value1, valu
    // result gets
    // the remainder from a division
    // between two doubles
    final static int	ineg			= 0x74;			// value ? resu
    // int
    final static int	lneg			= 0x75;			// value ? resu
    // negates a lo
    final static int	fneg			= 0x76;			// value ? resu
    // negates a
    // float
    final static int	dneg			= 0x77;			// value ? resu
    // negates a
    // double
    final static int	ishl			= 0x78;			// value1, valu
    // result int
    // shift left
    final static int	lshl			= 0x79;			// value1, valu
    // result
    // bitwise shift left of a long
    // value1 by value2 positions
    final static int	ior				= 0x80;			// value1, valu
    // result
    // logical int or
    final static int	lor				= 0x81;			// value1, valu
    // result
    // bitwise or of two longs
    final static int	ixor			= 0x82;			// value1, valu
    // result int
    // xor
    final static int	lxor			= 0x83;			// value1, valu
    // result
    // bitwise exclusive or of two
    // longs
    final static int	iinc			= 0x84;			// index, const
    // change]
    // increment local variable
    // #index by signed byte const
    final static int	i2l				= 0x85;			// value ? resu
    // converts an
    // int into a long
    final static int	i2f				= 0x86;			// value ? resu
    // converts an
    // int into a float
    final static int	i2d				= 0x87;			// value ? resu
    // converts an
    // int into a double
    final static int	l2i				= 0x88;			// value ? resu
    // converts a
    // long to an int
    final static int	l2f				= 0x89;			// value ? resu
    // converts a
    // long to a float
    final static int	d2f				= 0x90;			// value ? resu
    // converts a
    // double to a float
    final static int	i2b				= 0x91;			// value ? resu
    // converts an
    // int into a byte
    final static int	i2c				= 0x92;			// value ? resu
    // converts an
    // int into a character
    final static int	i2s				= 0x93;			// value ? resu
    // converts an
    // int into a int
    final static int	lcmp			= 0x94;			// value1, valu
    // result
    // compares two longs values
    final static int	fcmpl			= 0x95;			// value1, valu
    // result
    // compares two floats
    final static int	fcmpg			= 0x96;			// value1, valu
    // result
    // compares two floats
    final static int	dcmpl			= 0x97;			// value1, valu
    // result
    // compares two doubles
    final static int	dcmpg			= 0x98;			// value1, valu
    // result
    // compares two doubles
    final static int	ifeq			= 0x99;			// branchbyte1,
    // branchbyte2
    // value ? if value is 0, branch
    // to instruction at
    // branchoffset (signed int
    // constructed from unsigned
    // bytes branchbyte1 << 8 +
    // branchbyte2)
    final static int	lconst_1		= 0x0a;			// ? 1L pushes
    // 1 onto
    // the stack
    final static int	fconst_0		= 0x0b;			// ? 0.0f pushe
    // the
    // stack
    final static int	fconst_1		= 0x0c;			// ? 1.0f pushe
    // the
    // stack
    final static int	fconst_2		= 0x0d;			// ? 2.0f pushe
    // the
    // stack
    final static int	dconst_0		= 0x0e;			// ? 0.0 pushes
    // constant 0.0
    // onto the stack
    final static int	dconst_1		= 0x0f;			// ? 1.0 pushes
    // constant 1.0
    // onto the stack
    final static int	iload_0			= 0x1a;			// ? value load
    // value
    // from variable 0
    final static int	iload_1			= 0x1b;			// ? value load
    // value
    // from variable 1
    final static int	iload_2			= 0x1c;			// ? value load
    // value
    // from variable 2
    final static int	iload_3			= 0x1d;			// ? value load
    // value
    // from variable 3
    final static int	lload_0			= 0x1e;			// ? value load
    // value
    // from a local variable 0
    final static int	lload_1			= 0x1f;			// ? value load
    // value
    // from a local variable 1
    final static int	aload_0			= 0x2a;			// ? objectref
    // reference
    // onto the stack from local
    // variable 0
    final static int	aload_1			= 0x2b;			// ? objectref
    // reference
    // onto the stack from local
    // variable 1
    final static int	aload_2			= 0x2c;			// ? objectref
    // reference
    // onto the stack from local
    // variable 2
    final static int	aload_3			= 0x2d;			// ? objectref
    // reference
    // onto the stack from local
    // variable 3
    final static int	iaload			= 0x2e;			// arrayref, in
    // value loads
    // an int from an array
    final static int	laload			= 0x2f;			// arrayref, in
    // value load
    // a long from an array
    final static int	astore			= 0x3a;			// index object
    // stores a
    // reference into a local
    // variable #index
    final static int	istore_0		= 0x3b;			// value ? stor
    // value into
    // variable 0
    final static int	istore_1		= 0x3c;			// value ? stor
    // value into
    // variable 1
    final static int	istore_2		= 0x3d;			// value ? stor
    // value into
    // variable 2
    final static int	istore_3		= 0x3e;			// value ? stor
    // value into
    // variable 3
    final static int	lstore_0		= 0x3f;			// value ? stor
    // value in
    // a local variable 0
    final static int	dstore_3		= 0x4a;			// value ? stor
    // double into
    // local variable 3
    final static int	astore_0		= 0x4b;			// objectref ?
    // reference into local variable
    // 0
    final static int	astore_1		= 0x4c;			// objectref ?
    // reference into local variable
    // 1
    final static int	astore_2		= 0x4d;			// objectref ?
    // reference into local variable
    // 2
    final static int	astore_3		= 0x4e;			// objectref ?
    // reference into local variable
    // 3
    final static int	iastore			= 0x4f;			// arrayref, in
    // value ?
    // stores an int into an array
    final static int	dup_x1			= 0x5a;			// value2, valu
    // value1,
    // value2, value1 inserts a copy
    // of the top value into the
    // stack two values from the top
    final static int	dup_x2			= 0x5b;			// value3, valu
    // value1 ?
    // value1, value3, value2,
    // value1 inserts a copy of the
    // top value into the stack two
    // (if value2 is double or long
    // it takes up the entry of
    // value3, too) or three values
    // (if value2 is neither double
    // nor long) from the top
    final static int	dup2			= 0x5c;			// {value2, val
    // {value2,
    // value1}, {value2, value1}
    // duplicate top two stack words
    // (two values, if value1 is not
    // double nor long; a single
    // value, if value1 is double or
    // long)
    final static int	dup2_x1			= 0x5d;			// value3, {val
    // value1} ?
    // {value2, value1}, value3,
    // {value2, value1} duplicate
    // two words and insert beneath
    // third word (see explanation
    // above)
    final static int	dup2_x2			= 0x5e;			// {value4, val
    // {value2,
    // value1} ? {value2, value1},
    // {value4, value3}, {value2,
    // value1} duplicate two words
    // and insert beneath fourth
    // word
    final static int	swap			= 0x5f;			// value2, valu
    // value1,
    // value2 swaps two top words on
    // the stack (note that value1
    // and value2 must not be double
    // or long)
    final static int	fmul			= 0x6a;			// value1, valu
    // result
    // multiplies two floats
    final static int	dmul			= 0x6b;			// value1, valu
    // result
    // multiplies two doubles
    final static int	idiv			= 0x6c;			// value1, valu
    // result
    // divides two integers
    final static int	ldiv			= 0x6d;			// value1, valu
    // result
    // divide two longs
    final static int	fdiv			= 0x6e;			// value1, valu
    // result
    // divides two floats
    final static int	ddiv			= 0x6f;			// value1, valu
    // result
    // divides two doubles
    final static int	ishr			= 0x7a;			// value1, valu
    // result int
    // shift right
    final static int	lshr			= 0x7b;			// value1, valu
    // result
    // bitwise shift right of a long
    // value1 by value2 positions
    final static int	iushr			= 0x7c;			// value1, valu
    // result int
    // shift right
    final static int	lushr			= 0x7d;			// value1, valu
    // result
    // bitwise shift right of a long
    // value1 by value2 positions,
    // unsigned
    final static int	iand			= 0x7e;			// value1, valu
    // result
    // performs a logical and on two
    // integers
    final static int	land			= 0x7f;			// value1, valu
    // result
    // bitwise and of two longs
    final static int	l2d				= 0x8a;			// value ? resu
    // converts a
    // long to a double
    final static int	f2i				= 0x8b;			// value ? resu
    // converts a
    // float to an int
    final static int	f2l				= 0x8c;			// value ? resu
    // converts a
    // float to a long
    final static int	f2d				= 0x8d;			// value ? resu
    // converts a
    // float to a double
    final static int	d2i				= 0x8e;			// value ? resu
    // converts a
    // double to an int
    final static int	d2l				= 0x8f;			// value ? resu
    // converts a
    // double to a long
    final static int	ifne			= 0x9a;			// branchbyte1,
    // branchbyte2
    // value ? if value is not 0,
    // branch to instruction at
    // branchoffset (signed int
    // constructed from unsigned
    // bytes branchbyte1 << 8 +
    // branchbyte2)
    final static int	iflt			= 0x9b;			// branchbyte1,
    // branchbyte2
    // value ? if value is less than
    // 0, branch to instruction at
    // branchoffset (signed int
    // constructed from unsigned
    // bytes branchbyte1 << 8 +
    // branchbyte2)
    final static int	ifge			= 0x9c;			// branchbyte1,
    // branchbyte2
    // value ? if value is greater
    // than or equal to 0, branch to
    // instruction at branchoffset
    // (signed int constructed
    // from unsigned bytes
    // branchbyte1 << 8 +
    // branchbyte2)
    final static int	ifgt			= 0x9d;			// branchbyte1,
    // branchbyte2
    // value ? if value is greater
    // than 0, branch to instruction
    // at branchoffset (signed int
    // constructed from unsigned
    // bytes branchbyte1 << 8 +
    // branchbyte2)
    final static int	ifle			= 0x9e;			// branchbyte1,
    // branchbyte2
    // value ? if value is less than
    // or equal to 0, branch to
    // instruction at branchoffset
    // (signed int constructed
    // from unsigned bytes
    // branchbyte1 << 8 +
    // branchbyte2)
    final static int	if_icmpeq		= 0x9f;			// branchbyte1,
    // branchbyte2
    // value1, value2 ? if ints are
    // equal, branch to instruction
    // at branchoffset (signed int
    // constructed from unsigned
    // bytes branchbyte1 << 8 +
    // branchbyte2)
    final static int	if_icmpne		= 0xa0;			// branchbyte1,
    // branchbyte2
    // value1, value2 ? if ints are
    // not equal, branch to
    // instruction at branchoffset
    // (signed int constructed
    // from unsigned bytes
    // branchbyte1 << 8 +
    // branchbyte2)
    final static int	if_icmplt		= 0xa1;			// branchbyte1,
    // branchbyte2
    // value1, value2 ? if value1 is
    // less than value2, branch to
    // instruction at branchoffset
    // (signed int constructed
    // from unsigned bytes
    // branchbyte1 << 8 +
    // branchbyte2)
    final static int	if_icmpge		= 0xa2;			// branchbyte1,
    // branchbyte2
    // value1, value2 ? if value1 is
    // greater than or equal to
    // value2, branch to instruction
    // at branchoffset (signed int
    // constructed from unsigned
    // bytes branchbyte1 << 8 +
    // branchbyte2)
    final static int	if_icmpgt		= 0xa3;			// branchbyte1,
    // branchbyte2
    // value1, value2 ? if value1 is
    // greater than value2, branch
    // to instruction at
    // branchoffset (signed int
    // constructed from unsigned
    // bytes branchbyte1 << 8 +
    // branchbyte2)
    final static int	if_icmple		= 0xa4;			// branchbyte1,
    // branchbyte2
    // value1, value2 ? if value1 is
    // less than or equal to value2,
    // branch to instruction at
    // branchoffset (signed int
    // constructed from unsigned
    // bytes branchbyte1 << 8 +
    // branchbyte2)
    final static int	if_acmpeq		= 0xa5;			// branchbyte1,
    // branchbyte2
    // value1, value2 ? if
    // references are equal, branch
    // to instruction at
    // branchoffset (signed int
    // constructed from unsigned
    // bytes branchbyte1 << 8 +
    // branchbyte2)
    final static int	if_acmpne		= 0xa6;			// branchbyte1,
    // branchbyte2
    // value1, value2 ? if
    // references are not equal,
    // branch to instruction at
    // branchoffset (signed int
    // constructed from unsigned
    // bytes branchbyte1 << 8 +
    // branchbyte2)
    final static int	goto_			= 0xa7;			// branchbyte1,
    // branchbyte2
    // change] goes to another
    // instruction at branchoffset
    // (signed int constructed
    // from unsigned bytes
    // branchbyte1 << 8 +
    // branchbyte2)
    final static int	jsr				= 0xa8;			// branchbyte1,
    // branchbyte2
    // address jump to subroutine at
    // branchoffset (signed int
    // constructed from unsigned
    // bytes branchbyte1 << 8 +
    // branchbyte2) and place the
    // return address on the stack
    final static int	ret				= 0xa9;			// index [No ch
    // continue
    // execution from address taken
    // from a local variable #index
    // (the asymmetry with jsr is
    // intentional)
    final static int	tableswitch		= 0xaa;			// [0-3 bytes p
    // defaultbyte1, defaultbyte2,
    // defaultbyte3, defaultbyte4,
    // lowbyte1, lowbyte2, lowbyte3,
    // lowbyte4, highbyte1,
    // highbyte2, highbyte3,
    // highbyte4, jump offsets...
    // index ? continue execution
    // from an address in the table
    // at offset index
    final static int	lookupswitch	= 0xab;			// <0-3 bytes p
    // defaultbyte1, defaultbyte2,
    // defaultbyte3, defaultbyte4,
    // npairs1, npairs2, npairs3,
    // npairs4, match-offset
    // pairs... key ? a target
    // address is looked up from a
    // table using a key and
    // execution continues from the
    // instruction at that address
    final static int	ireturn			= 0xac;			// value ? [emp
    // returns an
    // integer from a method
    final static int	lreturn			= 0xad;			// value ? [emp
    // returns a
    // long value
    final static int	freturn			= 0xae;			// value ? [emp
    // returns a
    // float
    final static int	dreturn			= 0xaf;			// value ? [emp
    // returns a
    // double from a method
    final static int	areturn			= 0xb0;			// objectref ?
    // returns a
    // reference from a method
    final static int	return_			= 0xb1;			// ? [empty] re
    // from
    // method
    final static int	getstatic		= 0xb2;			// index1, inde
    // value gets a
    // static field value of a
    // class, where the field is
    // identified by field reference
    // in the constant pool index
    // (index1 << 8 + index2)
    final static int	putstatic		= 0xb3;			// indexbyte1,
    // indexbyte2 v
    // ? set static field to value
    // in a class, where the field
    // is identified by a field
    // reference index in constant
    // pool (indexbyte1 << 8 +
    // indexbyte2)
    final static int	getfield		= 0xb4;			// index1, inde
    // objectref ?
    // value gets a field value of
    // an object objectref, where
    // the field is identified by
    // field reference in the
    // constant pool index (index1
    // << 8 + index2)
    final static int	putfield		= 0xb5;			// indexbyte1,
    // indexbyte2
    // objectref, value ? set field
    // to value in an object
    // objectref, where the field is
    // identified by a field
    // reference index in constant
    // pool (indexbyte1 << 8 +
    // indexbyte2)
    final static int	invokevirtual	= 0xb6;			// indexbyte1,
    // indexbyte2
    // objectref, [arg1, arg2, ...]
    // ? invoke virtual method on
    // object objectref, where the
    // method is identified by
    // method reference index in
    // constant pool (indexbyte1 <<
    // 8 + indexbyte2)
    final static int	invokespecial	= 0xb7;			// indexbyte1,
    // indexbyte2
    // objectref, [arg1, arg2, ...]
    // ? invoke instance method on
    // object objectref, where the
    // method is identified by
    // method reference index in
    // constant pool (indexbyte1 <<
    // 8 + indexbyte2)
    final static int	invokestatic	= 0xb8;			// indexbyte1,
    // indexbyte2 [
    // arg2, ...] ? invoke a static
    // method, where the method is
    // identified by method
    // reference index in constant
    // pool (indexbyte1 << 8 +
    // indexbyte2)
    final static int	invokeinterface	= 0xb9;			// indexbyte1,
    // indexbyte2,
    // count, 0 objectref, [arg1,
    // arg2, ...] ? invokes an
    // interface method on object
    // objectref, where the
    // interface method is
    // identified by method
    // reference index in constant
    // pool (indexbyte1 << 8 +
    // indexbyte2)
    final static int	xxxunusedxxx	= 0xba;			// this opcode
    // reserved "fo
    // historical reasons"
    final static int	new_			= 0xbb;			// indexbyte1,
    // indexbyte2 ?
    // objectref creates new object
    // of type identified by class
    // reference in constant pool
    // index (indexbyte1 << 8 +
    // indexbyte2)
    final static int	newarray		= 0xbc;			// atype count
    // arrayref
    // creates new array with count
    // elements of primitive type
    // identified by atype
    final static int	anewarray		= 0xbd;			// indexbyte1,
    // indexbyte2 c
    // ? arrayref creates a new
    // array of references of length
    // count and component type
    // identified by the class
    // reference index (indexbyte1
    // << 8 + indexbyte2) in the
    // constant pool
    final static int	arraylength		= 0xbe;			// arrayref ? l
    // gets the
    // length of an array
    final static int	athrow			= 0xbf;			// objectref ?
    // objectref throws an error or
    // exception (notice that the
    // rest of the stack is cleared,
    // leaving only a reference to
    // the Throwable)
    final static int	checkcast		= 0xc0;			// indexbyte1,
    // indexbyte2
    // objectref ? objectref checks
    // whether an objectref is of a
    // certain type, the class
    // reference of which is in the
    // constant pool at index
    // (indexbyte1 << 8 +
    // indexbyte2)
    final static int	instanceof_		= 0xc1;			// indexbyte1,
    // indexbyte2
    // objectref ? result determines
    // if an object objectref is of
    // a given type, identified by
    // class reference index in
    // constant pool (indexbyte1 <<
    // 8 + indexbyte2)
    final static int	monitorenter	= 0xc2;			// objectref ?
    // monitor for
    // object ("grab the lock" -
    // start of synchronized()
    // section)
    final static int	monitorexit		= 0xc3;			// objectref ?
    // monitor for
    // object ("release the lock" -
    // end of synchronized()
    // section)
    final static int	wide			= 0xc4;			// opcode, inde
    // indexbyte2
    final static int	multianewarray	= 0xc5;			// indexbyte1,
    // indexbyte2,
    // dimensions count1,
    // [count2,...] ? arrayref
    // create a new array of
    // dimensions dimensions with
    // elements of type identified
    // by class reference in
    // constant pool index
    // (indexbyte1 << 8 +
    // indexbyte2); the sizes of
    // each dimension is identified
    // by count1, [count2, etc]
    final static int	ifnull			= 0xc6;			// branchbyte1,
    // branchbyte2
    // value ? if value is null,
    // branch to instruction at
    // branchoffset (signed int
    // constructed from unsigned
    // bytes branchbyte1 << 8 +
    // branchbyte2)
    final static int	ifnonnull		= 0xc7;			// branchbyte1,
    // branchbyte2
    // value ? if value is not null,
    // branch to instruction at
    // branchoffset (signed int
    // constructed from unsigned
    // bytes branchbyte1 << 8 +
    // branchbyte2)
    final static int	goto_w			= 0xc8;			// branchbyte1,
    // branchbyte2,
    // branchbyte3, branchbyte4 [no
    // change] goes to another
    // instruction at branchoffset
    // (signed int constructed from
    // unsigned bytes branchbyte1 <<
    // 24 + branchbyte2 << 16 +
    // branchbyte3 << 8 +
    // branchbyte4)
    final static int	jsr_w			= 0xc9;			// branchbyte1,
    // branchbyte2,
    // branchbyte3, branchbyte4 ?
    // address jump to subroutine at
    // branchoffset (signed int
    // constructed from unsigned
    // bytes branchbyte1 << 24 +
    // branchbyte2 << 16 +
    // branchbyte3 << 8 +
    // branchbyte4) and place the
    // return address on the stack
    final static int	breakpoint		= 0xca;			// reserved for
    // breakpoints
    // Java debuggers; should not
    // appear in any class file
    final static int	impdep1			= 0xfe;			// reserved for
    // implementation-dependent
    // operations within debuggers;
    // should not appear in any
    // class file
    final static int	impdep2			= 0xff;			// reserved for
    // implementation-dependent
    // operations within debuggers;
    // should not appear in any
    // class file
}
