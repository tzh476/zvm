package com.zvm.memory;

import com.zvm.Cmd;
import com.zvm.gc.GC;
import com.zvm.runtime.JThread;
import com.zvm.runtime.JavaClass;
import com.zvm.basestruct.TypeCode;
import com.zvm.runtime.RunTimeEnv;
import com.zvm.runtime.struct.*;

import java.util.HashMap;
import java.util.Map;

import static com.zvm.Cmd.HEAP_MAX_SIZE;


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

    public JObject createJObject(JavaClass javaClass, RunTimeEnv runTimeEnv, JThread jThread) {
        Integer heapSize = getHeapSize();
        int needSize = javaClass.instanceFieldSlotCount * 4;
        if((heapSize +  needSize) > HEAP_MAX_SIZE){
            System.out.println("堆内存不足,启动垃圾回收");
            GC.gc(runTimeEnv, jThread);
        }
        if((getHeapSize()  + needSize ) > HEAP_MAX_SIZE){
            System.out.println("垃圾回收后，堆内存依旧不足");
            return null;
        }
        JObject jObject = new JObject();
        jObject.javaClass = javaClass;
        jObject.offset = objectContainer.size();
        ObjectFields objectFields = new ObjectFields(javaClass.instanceFieldSlotCount);
        objectContainer.put(jObject.offset, objectFields);
        System.out.println("总内存:" + HEAP_MAX_SIZE + " 分配："+needSize+"完成 " + "当前已使用:" + getHeapSize() );
        return jObject;
    }

    public JObject createJArray(JavaClass arrayClass, int arrayType, int count, RunTimeEnv runTimeEnv, JThread jThread) {
        Integer needSize = getCurrentArraySize(arrayType, count);
        Integer heapSize = getHeapSize();
        if(heapSize + needSize > HEAP_MAX_SIZE){
            System.out.println("总内存:" + HEAP_MAX_SIZE + " 已使用：" + heapSize + " 当前需分配："+needSize+" ");
            GC.gc(runTimeEnv, jThread);
            System.out.println("总内存:" + HEAP_MAX_SIZE + " 回收情况：" + heapSize + "->" + getHeapSize() + " 当前需分配："+needSize+" ");
        }
        if(getHeapSize() + needSize > HEAP_MAX_SIZE){
            return null;
        }
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
        //System.out.println(new Gson().toJson(arrayContainer));
        System.out.println("总内存:" + HEAP_MAX_SIZE + " 分配："+needSize+"完成 " + "当前已使用:" + getHeapSize() );
        return jObject;
    }

    private Integer getCurrentArraySize(int arrayType, int count) {
        Integer arraySize = 0;
        if(arrayType == TypeCode.T_BOOLEAN || arrayType == TypeCode.T_BYTE ){
            arraySize += count * 1;
        }else if(arrayType == TypeCode.T_CHAR){
            arraySize += count * 2;
        }else if(arrayType == TypeCode.T_DOUBLE || arrayType == TypeCode.T_LONG ){
            arraySize += count * 8;
        }else {
            arraySize += count * 4;
        }
        return arraySize;
    }

    /**
     * 获得当前堆的大小
     * @return
     */
    private Integer getHeapSize() {
        Integer heapSize = 0;
        for(Map.Entry<Integer,ObjectFields> entry:objectContainer.entrySet()){
            ObjectFields objectFields = entry.getValue();
            heapSize += objectFields.slots.length * 4;
        }

        for (Map.Entry<Integer,ArrayFields> entry:arrayContainer.entrySet()){
            ArrayFields arrayFields = entry.getValue();
            if( arrayFields.primitiveTypes == null || arrayFields.primitiveTypes.length == 0){
                continue;
            }
            JType primitiveType = arrayFields.primitiveTypes[0];
            if(primitiveType instanceof JByte  ){
                heapSize += 1 * arrayFields.arraySize;
            }else if(primitiveType instanceof JChar){
                heapSize += 2 * arrayFields.arraySize;
            }else if(primitiveType instanceof JLong || primitiveType instanceof JDouble){
                heapSize += 8 * arrayFields.arraySize;
            }else {
                heapSize += 4 * arrayFields.arraySize;
            }
        }

        return heapSize;
    }
}
