package com.zvm.runtime;


import com.zvm.TypeUtils;
import com.zvm.runtime.struct.JObject;
import com.zvm.runtime.struct.Slot;

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

    public void putLong(int high, int low){
        slots[size].value = low;
        slots[size + 1].value = high;
        size += 2;
    }

    public void putLong(long value){
        slots[size].value = (int) value;
        slots[size + 1].value = (int) (value >> 32);
        size += 2;
    }

    public long popLong(){
        size -= 2;
        int low = slots[size].value;
        int high = slots[size + 1].value;
        return (((long)high) << 32) | ((long)low);
    }

    public void putDouble(double value){
        putLong(Double.doubleToRawLongBits(value));
    }

    public double popDouble(){
        long value = popLong();
        return Double.longBitsToDouble(value);
    }

    public void putByte(byte value){
        slots[size].value = value;
        size ++;
    }

    public int getInt(){
        return slots[size - 1].value;
    }

    public void putFloat(float value) {
        slots[size].value = TypeUtils.float2Int(value);
        size ++;
    }

    public Slot popSlot(){
        size --;
        return slots[size];
    }

    public void putSlot(Slot slot){
        slots[size] = slot;
        size ++;
    }

    public void putJObject(JObject jObject){
        slots[size].jType = jObject;
        size ++;
    }

    public Slot getSlot(){
        return slots[size - 1];
    }
}
