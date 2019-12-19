package com.zvm.classfile.attribute.stackmaptable;

import com.zvm.basestruct.u1;
import com.zvm.basestruct.u2;

public class same_frame_extended  extends stack_map_frame {
    public u1 SAME_FRAME_EXTENDED = new u1();
public u1 frame_type = SAME_FRAME_EXTENDED; /* 251 */
public u2 offset_delta;
}
