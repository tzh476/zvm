package com.zvm.classfile.attribute;

import com.zvm.basestruct.U2;
import com.zvm.basestruct.U4;
import com.zvm.classfile.attribute.RuntimeVisibleTypeAnnotations.TypeAnnotation;

public class RuntimeInvisibleTypeAnnotationsAttribute extends AttributeBase {
    public U2 attributeNameIndex;
    public U4 attributeLength;
    public U2 numAnnotations;
    TypeAnnotation[] annotations;
}
