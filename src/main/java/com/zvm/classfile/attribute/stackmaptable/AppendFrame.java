package com.zvm.classfile.attribute.stackmaptable;

import com.zvm.basestruct.U1;
import com.zvm.basestruct.U2;
import com.zvm.classfile.attribute.stackmaptable.verificationtypeinfo.VerificationTypeInfo;

public class AppendFrame extends StackMapFrame {
    public U1 append = new U1();
    /**
     *  252-254
     */
    public U1 frameType = append;
    public U2 offsetDelta;
    public VerificationTypeInfo[] locals ;
}
