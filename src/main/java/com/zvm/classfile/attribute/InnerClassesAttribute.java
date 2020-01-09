package com.zvm.classfile.attribute;

import com.zvm.basestruct.U2;
import com.zvm.basestruct.U4;
import com.zvm.classfile.attribute.innerClasses.Classes;

public class InnerClassesAttribute extends AttributeBase {
public U2 attributeNameIndex;
public U4 attributeLength;
public U2 numberOfClasses;
    public Classes[] classes;
}
