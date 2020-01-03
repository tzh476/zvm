package com.zvm.memory;

import com.zvm.runtime.Vars;
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

    /*保存数组的大小*/
    //public int arraySize;

//    public long getLongInArray(int index){
//        /*long数组中，一个long占2个slot，定位是，偏移*2*/
//        index = index * 2;
//        int low = slots[index].value;
//        int high = slots[index + 1].value;
//        //return (((long)high) << 32) | ((long)low);
//        return (((long)high) << 32) | ((long)low & 0x0ffffffffL);
//    }
//    public void putLongInArray(int index, long value){
//        /*long数组中，一个long占2个slot，定位是，偏移*2*/
//        index = index * 2;
//        slots[index].value = (int) value;
//        slots[index + 1].value = (int) (value >> 32);
//    }
//
//    public void putDoubleInArray(int index, double value) {
//        putLongInArray(index, Double.doubleToRawLongBits(value));
//    }
//
//    public double getDoubleInArray(int index){
//        /*double数组中，一个double占2个slot，定位是，偏移*2*/
//        index = index * 2;
//        long value = getLongByIndex(index);
//        return Double.longBitsToDouble(value);
//    }
}
