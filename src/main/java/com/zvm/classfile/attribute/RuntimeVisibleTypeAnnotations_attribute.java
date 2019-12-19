package com.zvm.classfile.attribute;

import com.zvm.basestruct.u2;
import com.zvm.basestruct.u4;
import com.zvm.classfile.attribute.RuntimeVisibleTypeAnnotations.type_annotation;

public class RuntimeVisibleTypeAnnotations_attribute extends Attribute_Base {//java8增加
public u2 attribute_name_index;
public u4 attribute_length;
public u2 num_annotations;
    type_annotation annotations[];
}
