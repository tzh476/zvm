package com.zvm.classfile;

import com.zvm.basestruct.u2;
import com.zvm.classfile.attribute.Attribute_Base;

public class field_info{
    public u2 access_flags;
    public u2 name_index;
    public u2 descriptor_index;
    public u2 attribute_count;
    public Attribute_Base[] attributes;

    public int slotId;
    public int constValueIndex;
}
