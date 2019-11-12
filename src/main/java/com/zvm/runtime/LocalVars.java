package com.zvm.runtime;

import com.zvm.runtime.struct.Slot;

public class LocalVars extends Vars{
    public LocalVars(Integer max_locals) {
        slots = new Slot[max_locals];
        for(Integer i = 0; i < max_locals; i++){
            slots[i] = new Slot();
        }
    }
}
