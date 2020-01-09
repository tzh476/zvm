package com.zvm.classfile;

import com.zvm.basestruct.U2;
import com.zvm.runtime.JavaClass;
import com.zvm.classfile.attribute.AttributeBase;

public class MethodInfo {
    public U2 accessFlags;
    public U2 nameIndex;
    public U2 descriptorIndex;
    public U2 attributeCount;
    public AttributeBase[] attributes;

    public int argSlotCount = -1;
    public JavaClass javaClass;
}
