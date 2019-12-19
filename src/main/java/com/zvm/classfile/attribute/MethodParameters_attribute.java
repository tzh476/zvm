package com.zvm.classfile.attribute;

import com.zvm.basestruct.u1;
import com.zvm.basestruct.u2;
import com.zvm.basestruct.u4;
import com.zvm.classfile.attribute.MethodParameters.parameter;

public class MethodParameters_attribute extends Attribute_Base {
public u2 attribute_name_index;
public u4 attribute_length;
public u1 parameters_count;
    parameter[] parameters;
}
