package com.zvm.runtime;


public class OperandStack {
    Integer size;
    Slot[] slots;

    public OperandStack(Integer max_stack) {
        slots = new Slot[max_stack];
        for(Integer i = 0; i < max_stack; i++){
            slots[i] = new Slot();
        }
        size = 0;
    }

    public void putInt(int value){
        slots[size].value = value;
        size ++;
    }

    public int popInt(){
        size --;
        return slots[size].value;
    }

    public void putByte(byte value){
        slots[size].value = value;
        size ++;
    }

    public int getInt(){
        return slots[size - 1].value;
    }


}
