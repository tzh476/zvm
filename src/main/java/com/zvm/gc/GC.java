package com.zvm.gc;

import com.zvm.memory.ArrayFields;
import com.zvm.memory.JavaHeap;
import com.zvm.memory.ObjectFields;
import com.zvm.runtime.*;
import com.zvm.runtime.struct.JArray;
import com.zvm.runtime.struct.JObject;
import com.zvm.runtime.struct.JType;
import com.zvm.runtime.struct.Slot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GC {

    public static List<Integer> usedObjectList;
    public static List<Integer> usedArrayList;


    public GC(){
        usedObjectList = new ArrayList<>();
        usedArrayList = new ArrayList<>();
    }

    /**
     *
     * @param runTimeEnv
     */
    public static void gc(RunTimeEnv runTimeEnv, JThread jThread) {
        markAndSweep(runTimeEnv, jThread);
    }

    private static void markAndSweep(RunTimeEnv runTimeEnv, JThread jThread) {
        ThreadStack threadStack = jThread.getStack();
        JavaFrame curFrame = threadStack.topFrame;
        JavaHeap javaHeap = runTimeEnv.javaHeap;
        while (curFrame != null){
            OperandStack operandStack = curFrame.operandStack;
            int size = operandStack.size;
            for(int i = 0; i < size; i ++){
                Slot slot = operandStack.getSlot();
                JType jType = slot.jType;
                mark(jType, javaHeap);
            }

            LocalVars localVars = curFrame.localVars;
            Slot[] localVarsSlots = localVars.slots;
            for(Slot localVar:localVarsSlots){
                mark(localVar.jType, javaHeap);
            }

            curFrame = curFrame.lowerFrame;
        }

        /*静态成员变量的回收，待完成*/

        /*清除*/
        sweep(javaHeap);
    }



    /**
     * jType表示对象或数组
     * @param jType
     * @param javaHeap
     */
    private static void mark(JType jType, JavaHeap javaHeap) {
        if(jType instanceof JObject){
            int offset = ((JObject) jType).offset;
            usedObjectList.add(offset);
            Map<Integer, ObjectFields> objectContainer = javaHeap.objectContainer;
            ObjectFields objectFields = objectContainer.get(offset);
            Slot[] fields = objectFields.slots;
            for(Slot field:fields){
                JType jType1 = field.jType;
                mark(jType1, javaHeap);
            }
        }else if (jType instanceof JArray){
            int offset = ((JObject) jType).offset;
            usedArrayList.add(offset);
            Map<Integer, ArrayFields> arrayContainer = javaHeap.arrayContainer;
            ArrayFields arrayFields = arrayContainer.get(offset);
            JType[] fields = arrayFields.primitiveTypes;
            for(JType field:fields){
                JType jType1 = field;
                mark(jType1, javaHeap);
            }
        }
    }

    /**
     * 清除
     */
    private static void sweep(JavaHeap javaHeap) {
        Map<Integer, ObjectFields> objectContainer = javaHeap.objectContainer;
        Map<Integer, ArrayFields> arrayContainer = javaHeap.arrayContainer;

        for(Map.Entry entry:objectContainer.entrySet()){
            if(!usedObjectList.contains(entry.getKey())){
                objectContainer.remove(entry.getKey());
            }
        }

        for(Map.Entry entry:objectContainer.entrySet()){
            if(!usedArrayList.contains(entry.getKey())){
                arrayContainer.remove(entry.getKey());
            }
        }
    }
}
