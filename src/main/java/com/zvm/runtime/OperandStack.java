package com.zvm.runtime;


import com.zvm.utils.TypeUtils;
import com.zvm.runtime.struct.JObject;
import com.zvm.runtime.struct.Slot;

/**
 * 执行方法时有局部变量表和操作数栈。OperandStack表示操作数栈
 */
public class OperandStack {
    public Integer size;
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
        slots[size].value = (int) value;/*low bytes*/
        slots[size + 1].value = (int) (value >> 32);/*high bytes*/
        size += 2;
    }

    public long popLong(){
        size -= 2;
        int low = slots[size].value;
        int high = slots[size + 1].value;
        //return (((long)high) << 32) | ((long)low);
        return (((long)high) << 32) | ((long)low & 0x0ffffffffL
        );
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

    public float popFloat() {
        int value = popInt();
        return TypeUtils.int2Float(value);
    }

    /*传递引用时需要特殊考虑*/
    public Slot popSlot(){
        size --;
        Slot slot = slots[size];
        //slots[size].jType = null;/*会将jType对象值设为null，可能有其他地方引用了这个jType*/
        //slots[size] = null;/*后面的opcode给slots[size].jType赋值时，报空指针*/
        slots[size] = new Slot();
       // return slots[size];
        return slot;
    }

    public void putSlot(Slot slot){
        slots[size] = slot;
        size ++;
    }

    public void putJObject(JObject jObject){
        slots[size].jType = jObject;
        size ++;
    }

    /*传递引用时需要特殊考虑*/
    public JObject popJObject(){
        size --;
        JObject jObject = (JObject) slots[size].jType;
        //slots[size].jType = null;/*会将jType对象值设为null，可能有其他地方引用了这个jType*/
        //slots[size] = null;/*后面的opcode给slots[size].jType赋值时，报空指针*/
        slots[size] = new Slot();
        return jObject;
    }

//    public void putJArray(JArray jArray){
//        slots[size].jType = jArray;
//        size ++;
//    }
//
//    public JArray popJArray(){
//        size --;
//        JArray jArray = (JArray) slots[size].jType;
//        //slots[size].jType = null;/*会将jType对象值设为null，可能有其他地方引用了这个jType*/
//        //slots[size] = null;/*后面的opcode给slots[size].jType赋值时，报空指针*/
//        slots[size] = new Slot();
//        return jArray;
//    }

    public JObject getJObjectFromTop(int index){
        return (JObject) slots[size - 1 - index].jType;
    }

    public Slot getSlot(){
        return slots[size - 1];
    }
}
