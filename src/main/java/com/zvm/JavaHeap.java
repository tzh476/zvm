package com.zvm;

import com.zvm.basestruct.u2;
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
    public Map<Integer, List<JType>> objectContainer = new HashMap<>();


    /**
     * 保存各实例对象的字段
     * @param javaClass
     * @return
     */

    public JObject createJObject(JavaClass javaClass) {
        JObject jObject = new JObject();
        jObject.javaClass = javaClass;
        jObject.offset = objectContainer.size();
        List<JType> instanceFields = new ArrayList<>();

        field_info[] field_infos = javaClass.getClassFile().fields;
        for(field_info field_info:field_infos){
            u2 access_flags = field_info.access_flags;
            instanceFields.add(new JType());
        }

        objectContainer.put(jObject.offset, instanceFields);
        return jObject;
    }
}
