package com.zvm.runtime;

import com.zvm.runtime.struct.Slot;

/**
 * 保存对象的所有字段
 */
public class ObjectFields extends Vars {
    public ObjectFields(Integer num) {
        slots = new Slot[num];
        for(Integer i = 0; i < num; i++){
            slots[i] = new Slot();
        }
    }
}
