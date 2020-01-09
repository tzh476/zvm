package com.zvm.classfile.attribute.stackmaptable;

import com.zvm.basestruct.U1;
import com.zvm.basestruct.U2;
import com.zvm.classfile.attribute.stackmaptable.verificationtypeinfo.VerificationTypeInfo;

public class FullFrame extends StackMapFrame {
    public U1 fullFrame = new U1();
public U1 frameType = fullFrame; /* 255 */
public U2 offsetDelta;
public U2 numberOfLocals;
    public VerificationTypeInfo[] locals ;
public U2 numberOfStackItems;
    public VerificationTypeInfo[] stack;
}
