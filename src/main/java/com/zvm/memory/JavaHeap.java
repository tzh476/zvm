package com.zvm.memory;

import com.zvm.runtime.JavaClass;
import com.zvm.basestruct.TypeCode;
import com.zvm.runtime.struct.JObject;

import java.util.HashMap;
import java.util.Map;


public class JavaHeap {
    /**
     * 保存对象
     */
    public Map<Integer, ObjectFields> objectContainer = new HashMap<>();
    public Map<Integer, ArrayFields> arrayContainer = new HashMap<>();


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
        ArrayFields arrayFields = null;

        if(arrayType == TypeCode.T_BOOLEAN){
            arrayFields = new ArrayFields(new byte[count]);
            arrayContainer.put(jObject.offset, arrayFields);
        }else if(arrayType == TypeCode.T_CHAR){
            arrayFields = new ArrayFields(new char[count]);
            arrayContainer.put(jObject.offset, arrayFields);
        }else if(arrayType == TypeCode.T_FLOAT){
            arrayFields = new ArrayFields(new float[count]);
            arrayContainer.put(jObject.offset, arrayFields);
        }else if(arrayType == TypeCode.T_DOUBLE){
            arrayFields = new ArrayFields(new double[count]);
            arrayContainer.put(jObject.offset, arrayFields);
        }else if(arrayType == TypeCode.T_BYTE){
            arrayFields = new ArrayFields(new byte[count]);
            arrayContainer.put(jObject.offset, arrayFields);
        }else if(arrayType == TypeCode.T_SHORT){
            arrayFields = new ArrayFields(new short[count]);
            arrayContainer.put(jObject.offset, arrayFields);
        }else if(arrayType == TypeCode.T_INT){
            arrayFields = new ArrayFields(new int[count]);
            arrayContainer.put(jObject.offset, arrayFields);
        }else if(arrayType == TypeCode.T_LONG){
            arrayFields = new ArrayFields(new long[count]);
            arrayContainer.put(jObject.offset, arrayFields);
        }else if(arrayType == TypeCode.T_EXTRA_OBJECT){
            arrayFields = new ArrayFields(new JObject[count],arrayClass );
            arrayContainer.put(jObject.offset, arrayFields);
        }
        return jObject;
    }
}
