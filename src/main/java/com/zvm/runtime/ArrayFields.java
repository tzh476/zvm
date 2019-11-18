package com.zvm.runtime;

import com.zvm.runtime.struct.Slot;

public class ArrayFields extends Vars {
    public ArrayFields(Integer num) {
        slots = new Slot[num];
        for(Integer i = 0; i < num; i++){
            slots[i] = new Slot();
        }
    }
}
