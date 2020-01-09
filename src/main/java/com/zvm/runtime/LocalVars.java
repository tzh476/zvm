package com.zvm.runtime;

import com.zvm.runtime.struct.Slot;

/**
 * 局部变量表
 */
public class LocalVars extends Vars {
    public LocalVars(Integer maxLocals) {
        slots = new Slot[maxLocals];
        for(Integer i = 0; i < maxLocals; i++){
            slots[i] = new Slot();
        }
    }
}
