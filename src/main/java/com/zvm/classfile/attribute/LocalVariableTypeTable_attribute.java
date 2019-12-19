package com.zvm.classfile.attribute;

import com.zvm.basestruct.u2;
import com.zvm.basestruct.u4;
import com.zvm.classfile.attribute.LocalVariableTypeTable.local_variable_type;

public class LocalVariableTypeTable_attribute extends Attribute_Base {
public u2 attribute_name_index;
public u4 attribute_length;
public u2 local_variable_type_table_length;
    public local_variable_type[] local_variable_type_table;
}
