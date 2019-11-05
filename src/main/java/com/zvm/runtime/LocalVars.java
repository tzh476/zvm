package com.zvm.runtime;

import com.zvm.runtime.struct.Slot;

public class LocalVars {
    Slot[] slots;
    public LocalVars(Integer max_locals) {
        slots = new Slot[max_locals];
        for(Integer i = 0; i < max_locals; i++){
            slots[i] = new Slot();
        }
    }

    public void putSlot(Integer index, Slot slot){
        slots[index] = slot;
    }

    public void putIntByIndex(int index, int value){
        slots[index].value = value ;
    }

    public int getIntByIndex(int index){
        return slots[index].value;
    }
    public long getLongByIndex(int index){
        int low = slots[index].value;
        int high = slots[index + 1].value;
        return (((long)high) << 32) | ((long)low);
    }

    public void putLong(int index, int high, int low){
        slots[index].value = low;
        slots[index + 1].value = high;
    }
    public void putLong(int index, long value){
        slots[index].value = (int) value;
        slots[index + 1].value = (int) (value >> 32);
    }
}
