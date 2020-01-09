package com.zvm.classfile.attribute.stackmaptable;

import com.zvm.basestruct.U1;
import com.zvm.basestruct.U2;

public class ChopFrame extends StackMapFrame {
    public U1 chop = new U1();
public U1 frameType = chop; /* 248-250 */
public U2 offsetDelta;
}
