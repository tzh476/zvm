package com.zvm.runtime;


import com.zvm.utils.Vars;
import com.zvm.runtime.struct.Slot;

public class StaticVars extends Vars {
    public StaticVars(Integer staticSlotCount) {
        slots = new Slot[staticSlotCount];
        for(Integer i = 0; i < staticSlotCount; i++){
            slots[i] = new Slot();
        }
    }
}
