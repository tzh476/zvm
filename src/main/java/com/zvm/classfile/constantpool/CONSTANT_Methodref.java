package com.zvm.classfile.constantpool;

import com.zvm.basestruct.u1;
import com.zvm.basestruct.u2;

public class CONSTANT_Methodref extends CONSTANT_Base{
    public u1 tag;
    public u2 class_index;
    public u2 name_and_type_index;
}
