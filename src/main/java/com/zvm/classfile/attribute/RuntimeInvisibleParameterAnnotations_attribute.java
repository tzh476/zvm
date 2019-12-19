package com.zvm.classfile.attribute;

import com.zvm.basestruct.u1;
import com.zvm.basestruct.u2;
import com.zvm.basestruct.u4;
import com.zvm.classfile.attribute.RuntimeVisibleParameterAnnotations.parameter_annotation;

public class RuntimeInvisibleParameterAnnotations_attribute  extends Attribute_Base {
public u2 attribute_name_index;
public u4 attribute_length;
public u1 num_parameters;
    public parameter_annotation[] parameter_annotations;
}
