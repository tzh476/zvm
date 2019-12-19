package com.zvm.classfile.constantpool;

import com.zvm.basestruct.u1;
import com.zvm.basestruct.u2;

public class CONSTANT_InvokeDynamic extends CONSTANT_Base{
    public u1 tag;
    public u2 bootstrap_method_attr_index;
    public u2 name_and_type_index;
}
