package com.zvm.classfile.attribute;

import com.zvm.basestruct.U1;
import com.zvm.basestruct.U2;
import com.zvm.basestruct.U4;
import com.zvm.classfile.attribute.code.ExceptionTable;

public class CodeAttribute extends AttributeBase {
    public U2 attributeNameIndex;
    public U4 attributeLength;
    public U2 maxStack;
    public U2 maxLocals;
    public U4 codeLength;
    public U1[] code;
    public U2 exceptionTableLength;
    public ExceptionTable[] exceptionTables;
    public U2 attributeCount;
    public AttributeBase[] attributes;
}
