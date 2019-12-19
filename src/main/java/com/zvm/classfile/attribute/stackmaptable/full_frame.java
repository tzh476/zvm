package com.zvm.classfile.attribute.stackmaptable;

import com.zvm.basestruct.u1;
import com.zvm.basestruct.u2;
import com.zvm.classfile.attribute.stackmaptable.verificationtypeinfo.verification_type_info;

public class full_frame  extends stack_map_frame {
    public u1 FULL_FRAME = new u1();
public u1 frame_type = FULL_FRAME; /* 255 */
public u2 offset_delta;
public u2 number_of_locals;
    public verification_type_info[] locals ;
public u2 number_of_stack_items;
    public verification_type_info[] stack;
}
