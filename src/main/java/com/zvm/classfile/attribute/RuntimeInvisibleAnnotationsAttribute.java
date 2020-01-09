package com.zvm.classfile.attribute;

import com.zvm.basestruct.U2;
import com.zvm.basestruct.U4;
import com.zvm.classfile.attribute.runtimeVisibleAnnotations.Annotation;

public class RuntimeInvisibleAnnotationsAttribute extends AttributeBase {
public U2 attributeNameIndex;
public U4 attributeLength;
public U2 numAnnotations;
    public Annotation[] annotations;
}
