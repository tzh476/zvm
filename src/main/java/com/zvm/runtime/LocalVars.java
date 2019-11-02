package com.zvm.runtime;

public class LocalVars {
    Slot[] slots;
    public LocalVars(Integer max_locals) {
        slots = new Slot[max_locals];
        for(Integer i = 0; i < max_locals; i++){
            slots[i] = new Slot();
        }
    }

    public void putIntByIndex(int index, int value){
        slots[index].value = value ;
    }

    public int getIntByIndex(int index){
        return slots[index].value;
    }
}
