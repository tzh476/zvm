package com.zvm.classfile.attribute;

import com.zvm.basestruct.u2;
import com.zvm.basestruct.u4;
import com.zvm.classfile.attribute.stackmaptable.stack_map_frame;

public class StackMapTable_attribute extends Attribute_Base {
    public u2 attribute_name_index;
    public u4 attribute_length;
    public u2 number_of_entries;
    public stack_map_frame[] entries;
}
