package com.zvm.classfile.constantpool;

import com.zvm.basestruct.U1;
import com.zvm.basestruct.U4;

public class ConstantDouble extends ConstantBase {
    public U1 tag;
    public U4 highBytes;
    public U4 lowBytes;
}
