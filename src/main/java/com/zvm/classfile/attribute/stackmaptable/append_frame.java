package com.zvm.classfile.attribute.stackmaptable;

import com.zvm.basestruct.u1;
import com.zvm.basestruct.u2;
import com.zvm.classfile.attribute.stackmaptable.verificationtypeinfo.verification_type_info;

public class append_frame  extends stack_map_frame {
    public u1 APPEND = new u1();
public u1 frame_type = APPEND; /* 252-254 */
public u2 offset_delta;
    public verification_type_info[] locals ;
}
