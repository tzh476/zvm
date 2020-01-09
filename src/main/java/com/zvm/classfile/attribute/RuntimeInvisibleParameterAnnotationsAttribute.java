package com.zvm.classfile.attribute;

import com.zvm.basestruct.U1;
import com.zvm.basestruct.U2;
import com.zvm.basestruct.U4;
import com.zvm.classfile.attribute.RuntimeVisibleParameterAnnotations.ParameterAnnotation;

public class RuntimeInvisibleParameterAnnotationsAttribute extends AttributeBase {
public U2 attributeNameIndex;
public U4 attributeLength;
public U1 numParameters;
    public ParameterAnnotation[] parameterAnnotations;
}
