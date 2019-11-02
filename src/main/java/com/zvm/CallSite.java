package com.zvm;

import com.zvm.basestruct.u1;
import com.zvm.basestruct.u2;
import com.zvm.basestruct.u4;

/**
 * 作为方法调用的运行时的具体描述
 */
public class CallSite {
    public u2 access_flags;
    public u2 name_index;
    public u2 descriptor_index;
    public u2 max_stack;
    public u2 max_locals;
    public u4 code_length;
    public u1[] code;
    public u2 exception_table_length;
    public exception_table[] exception_table;

    public void setCallSite(method_info method_info) {
        access_flags = method_info.access_flags;
        name_index = method_info.name_index;
        descriptor_index = method_info.descriptor_index;
        Integer count = TypeUtils.byte2Int(method_info.attribute_count.u2);
        for(Integer i = 0; i < count; i++){
            if(method_info.attributes[i] instanceof Code_attribute){
                Code_attribute code_attribute = (Code_attribute) method_info.attributes[i];
                max_stack = code_attribute.max_stack;
                max_locals = code_attribute.max_locals;
                code_length = code_attribute.code_length;
                code = code_attribute.code;
                exception_table_length = code_attribute.exception_table_length;
                exception_table = code_attribute.exception_table;
                break;
            }
        }
    }
}
