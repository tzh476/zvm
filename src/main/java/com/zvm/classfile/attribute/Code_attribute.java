package com.zvm.classfile.attribute;

import com.zvm.basestruct.u1;
import com.zvm.basestruct.u2;
import com.zvm.basestruct.u4;

public class Code_attribute extends Attribute_Base {
    public u2 attribute_name_index;
    public u4 attribute_length;
    public u2 max_stack;
    public u2 max_locals;
    public u4 code_length;
    public u1[] code;
    public u2 exception_table_length;
    public com.zvm.classfile.attribute.code.exception_table[] exception_table;
    public u2 attribute_count;
    public Attribute_Base[] attributes;
}
