package com.zvm.classfile.attribute;

import com.zvm.basestruct.u2;
import com.zvm.basestruct.u4;
import com.zvm.classfile.attribute.LocalVariableTable.local_variable;

public class LocalVariableTable_attribute extends Attribute_Base {
public u2 attribute_name_index;
public u4 attribute_length;
public u2 local_variable_table_length;
    public local_variable[] local_variable_table;
}
