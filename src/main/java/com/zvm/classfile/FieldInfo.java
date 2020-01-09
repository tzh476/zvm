package com.zvm.classfile;

import com.zvm.basestruct.U2;
import com.zvm.classfile.attribute.AttributeBase;
import com.zvm.runtime.JavaClass;

public class FieldInfo {
    public U2 accessFlags;
    public U2 nameIndex;
    public U2 descriptorIndex;
    public U2 attributeCount;
    public AttributeBase[] attributes;

    public int slotId;
    public int constValueIndex;

    public JavaClass javaClass;

}
