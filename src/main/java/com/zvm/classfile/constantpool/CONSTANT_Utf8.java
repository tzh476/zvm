package com.zvm.classfile.constantpool;

import com.zvm.basestruct.u1;
import com.zvm.basestruct.u2;

public class CONSTANT_Utf8 extends CONSTANT_Base{
    public u1 tag;
    public u2 length;
    public u1[] bytes;
}
