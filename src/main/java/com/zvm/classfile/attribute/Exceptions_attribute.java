package com.zvm.classfile.attribute;

import com.zvm.basestruct.u2;
import com.zvm.basestruct.u4;
import com.zvm.classfile.attribute.Attribute_Base;

public class Exceptions_attribute extends Attribute_Base {
public u2 attribute_name_index;
public u4 attribute_length;
public u2 number_of_exceptions;
    public u2[] exception_index_table;
}
