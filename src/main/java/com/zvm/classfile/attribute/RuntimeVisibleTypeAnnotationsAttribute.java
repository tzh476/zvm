package com.zvm.classfile.attribute;

import com.zvm.basestruct.U2;
import com.zvm.basestruct.U4;
import com.zvm.classfile.attribute.RuntimeVisibleTypeAnnotations.TypeAnnotation;

/**
 * java8增加
 */
public class RuntimeVisibleTypeAnnotationsAttribute extends AttributeBase {
    public U2 attributeNameIndex;
    public U4 attributeLength;
    public U2 numAnnotations;
    TypeAnnotation[] annotations;
}
