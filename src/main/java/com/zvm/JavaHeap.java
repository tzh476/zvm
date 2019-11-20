package com.zvm;

import com.zvm.runtime.ObjectFields;
import com.zvm.runtime.struct.JObject;

import java.util.HashMap;
import java.util.Map;


public class JavaHeap {
    /**
     * 保存对象
     */
    public Map<Integer, ObjectFields> objectContainer = new HashMap<>();
    public Map<Integer, ObjectFields> arrayContainer = new HashMap<>();


    /**
     * 保存各实例对象的字段
     *
     * @param javaClass
     * @return
     */

    public JObject createJObject(JavaClass javaClass) {
        JObject jObject = new JObject();
        jObject.javaClass = javaClass;
        jObject.offset = objectContainer.size();
        ObjectFields objectFields = new ObjectFields(javaClass.instanceFieldSlotCount);
        objectContainer.put(jObject.offset, objectFields);
        return jObject;
    }

    public JObject createJArray(JavaClass arrayClass, int arrayType, int count) {

        JObject jObject = new JObject();
        jObject.offset = arrayContainer.size();
        jObject.javaClass = arrayClass;
        ObjectFields arrayFields = null;
        int needSlotCount = count;
//        if (arrayType == TypeCode.T_LONG || arrayType == TypeCode.T_DOUBLE) {
//            arrayFields = new ObjectFields(2 * count);
//            arrayContainer.put(jObject.offset, arrayFields);
//        } else {
//            arrayFields = new ObjectFields(count);
//            arrayContainer.put(jObject.offset, arrayFields);
//        }
//        return jObject;
        if(arrayType == TypeCode.T_BOOLEAN){
            /*一个boolean占用1个字节，即1/4slot*/
            if(needSlotCount % 4 == 0 ){
                needSlotCount = needSlotCount / 4;
            }else{
                needSlotCount = needSlotCount / 4 + 1;
            }
            arrayFields = new ObjectFields(needSlotCount / 4);
            arrayContainer.put(jObject.offset, arrayFields);
        }else if(arrayType == TypeCode.T_CHAR){
            /*一个char占用2个字节，即1/2slot*/
            if(needSlotCount % 2 == 0 ){
                needSlotCount = needSlotCount / 2;
            }else{
                needSlotCount = needSlotCount / 2 + 1;
            }
            arrayFields = new ObjectFields(needSlotCount / 2);
            arrayContainer.put(jObject.offset, arrayFields);
        }else if(arrayType == TypeCode.T_FLOAT){
            arrayFields = new ObjectFields(needSlotCount );
            arrayContainer.put(jObject.offset, arrayFields);
        }else if(arrayType == TypeCode.T_DOUBLE){
            /*一个double占用8个字节，即2slot*/
            needSlotCount = 2 * needSlotCount;
            arrayFields = new ObjectFields(needSlotCount );
            arrayContainer.put(jObject.offset, arrayFields);
        }else if(arrayType == TypeCode.T_BYTE){
            /*一个byte占用1个字节，即1/4slot*/
            if(needSlotCount % 4 == 0 ){
                needSlotCount = needSlotCount / 4;
            }else{
                needSlotCount = needSlotCount / 4 + 1;
            }
            arrayFields = new ObjectFields(needSlotCount / 4);
            arrayContainer.put(jObject.offset, arrayFields);
        }else if(arrayType == TypeCode.T_SHORT){
            /*一个short占用2个字节，即1/2slot*/
            if(needSlotCount % 2 == 0 ){
                needSlotCount = needSlotCount / 2;
            }else{
                needSlotCount = needSlotCount / 2 + 1;
            }
            arrayFields = new ObjectFields(needSlotCount / 2);
            arrayContainer.put(jObject.offset, arrayFields);
        }else if(arrayType == TypeCode.T_INT){
            arrayFields = new ObjectFields(needSlotCount);
            arrayContainer.put(jObject.offset, arrayFields);
        }else if(arrayType == TypeCode.T_LONG){
            /*一个long占用8个字节，即2slot*/
            needSlotCount = 2 * needSlotCount;
            arrayFields = new ObjectFields(needSlotCount );
            arrayContainer.put(jObject.offset, arrayFields);
        }
        arrayFields.arraySize = count;
        return jObject;
    }
}
