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
        ObjectFields arrayFields = null;
        if (arrayType == TypeCode.T_LONG || arrayType == TypeCode.T_DOUBLE) {
            arrayFields = new ObjectFields(2 * count);
            arrayContainer.put(jObject.offset, arrayFields);
        } else {
            arrayFields = new ObjectFields(count);
            arrayContainer.put(jObject.offset, arrayFields);
        }
        return jObject;
//        if(arrayType == TypeCode.T_BOOLEAN){
//            arrayFields = new ArrayFields(count);
//            arrayContainer.put(jArray.offset, arrayFields);
//        }else if(arrayType == TypeCode.T_CHAR){
//            arrayFields = new ArrayFields(count);
//            arrayContainer.put(jArray.offset, arrayFields);
//        }else if(arrayType == TypeCode.T_FLOAT){
//            arrayFields = new ArrayFields(count);
//            arrayContainer.put(jArray.offset, arrayFields);
//        }else if(arrayType == TypeCode.T_DOUBLE){
//            arrayFields = new ArrayFields(2 * count);
//            arrayContainer.put(jArray.offset, arrayFields);
//        }else if(arrayType == TypeCode.T_BYTE){
//            arrayFields = new ArrayFields(count);
//            arrayContainer.put(jArray.offset, arrayFields);
//        }else if(arrayType == TypeCode.T_SHORT){
//            arrayFields = new ArrayFields(count);
//            arrayContainer.put(jArray.offset, arrayFields);
//        }else if(arrayType == TypeCode.T_INT){
//            arrayFields = new ArrayFields(count);
//            arrayContainer.put(jArray.offset, arrayFields);
//        }else if(arrayType == TypeCode.T_LONG){
//            arrayFields = new ArrayFields(2 * count);
//            arrayContainer.put(jArray.offset, arrayFields);
//        }
//        return jArray;
    }
}
