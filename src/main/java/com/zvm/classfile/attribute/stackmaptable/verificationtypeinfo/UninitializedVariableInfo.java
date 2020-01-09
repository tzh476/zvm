package com.zvm.classfile.attribute.stackmaptable.verificationtypeinfo;

import com.zvm.basestruct.U1;
import com.zvm.basestruct.U2;

public class UninitializedVariableInfo extends VerificationTypeInfo {
    public U1 itemUninitialized = new U1();
public U1 tag = itemUninitialized; /* 8 */
public U2 offset;
}
