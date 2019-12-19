package com.zvm.classfile.attribute;

import com.zvm.basestruct.u2;
import com.zvm.basestruct.u4;
import com.zvm.classfile.attribute.runtimeVisibleAnnotations.annotation;

public class RuntimeVisibleAnnotations_attribute extends Attribute_Base {
public u2 attribute_name_index;
public u4 attribute_length;
public u2 num_annotations;
    public annotation[] annotations;
}
