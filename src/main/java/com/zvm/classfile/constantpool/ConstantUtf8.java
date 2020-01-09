package com.zvm.classfile.constantpool;

import com.zvm.basestruct.U1;
import com.zvm.basestruct.U2;

public class ConstantUtf8 extends ConstantBase {
    public U1 tag;
    public U2 length;
    public U1[] bytes;
}
