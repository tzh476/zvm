package com.zvm.classfile.attribute.stackmaptable;

import com.zvm.basestruct.u1;
import com.zvm.basestruct.u2;
import com.zvm.classfile.attribute.stackmaptable.stack_map_frame;

public class chop_frame  extends stack_map_frame {
    public u1 CHOP = new u1();
public u1 frame_type = CHOP; /* 248-250 */
public u2 offset_delta;
}
