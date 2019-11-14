package com.zvm;

import com.zvm.basestruct.u2;
import com.zvm.runtime.ObjectFields;
import com.zvm.runtime.struct.JObject;
import com.zvm.runtime.struct.JType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JavaHeap {
    /**
     * 保存对象
     */
    public Map<Integer, ObjectFields> objectContainer = new HashMap<>();


    /**
     * 保存各实例对象的字段
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
}
