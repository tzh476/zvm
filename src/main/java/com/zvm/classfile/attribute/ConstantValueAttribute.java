package com.zvm.classfile.attribute;

import com.zvm.basestruct.U2;
import com.zvm.basestruct.U4;

public class ConstantValueAttribute extends AttributeBase {
    public U2 attributeNameIndex;
    public U4 attributeLength;
    public U2 constantvalueIndex;
}
