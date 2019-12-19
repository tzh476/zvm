package com.zvm.classfile.attribute;

import com.zvm.basestruct.u2;
import com.zvm.basestruct.u4;
import com.zvm.classfile.attribute.lineNumberTable.line_number;

public class LineNumberTable_attribute extends Attribute_Base {
public u2 attribute_name_index;
public u4 attribute_length;
public u2 line_number_table_length;
    public line_number[] line_number_table;
}
