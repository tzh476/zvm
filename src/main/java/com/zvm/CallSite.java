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
    public u2 max_stack ;
    public u2 max_locals ;
    public u4 code_length;
    public u1[] code;
    public u2 exception_table_length;
    public exception_table[] exception_table;

    public CallSite(){
        max_stack = new u2();
        max_stack.u2 = new byte[1];
        max_locals = new u2();
        max_locals.u2 = new byte[1];
        code_length = new u4();
    }

    /**
     * 可能是native方法，需要设置max_stack、max_locals、注入字节码等
     * @param method_info
     * @param returnType
     */
    public void setCallSiteOrNative(method_info method_info, Integer returnType){
        access_flags = method_info.access_flags;
        /*对于native方法，注入字节码*/
        if(MethodArea.isNative(access_flags)){
            max_stack.u2 = new byte[]{0x4};
            max_locals.u2 = new byte[]{0x5};
            code_length.u4 = new byte[]{0x2};
            code = new u1[2];
            code[0] = new u1();
            code[0].u1 = new byte[1];
            code[1] = new u1();
            code[1].u1 = new byte[1];
            code[0].u1[0] = (byte) (0x0ff & 0xfe);

            if(TypeCode.T_EXTRA_VOID.equals(returnType)){
                code[1].u1[0] = (byte) (0x0ff & 0xb1);// return
            }else if (TypeCode.T_EXTRA_OBJECT.equals(returnType)
                    || TypeCode.T_EXTRA_ARRAY.equals(returnType)){
                code[1].u1[0] = (byte) (0x0ff & 0xb0); //areturn
            }else if(TypeCode.T_DOUBLE.equals(returnType)){
                code[1].u1[0] = (byte) (0x0ff & 0xaf); //dreturb
            }else if (TypeCode.T_FLOAT.equals(returnType)){
                code[1].u1[0] = (byte) (0x0ff & 0xae);//freturn
            }else if(TypeCode.T_LONG.equals(returnType)){
                code[1].u1[0] = (byte) (0x0ff & 0xad);//lreturn
            }else  {
                code[1].u1[0] = (byte) (0x0ff & 0xac); //ireturn
            }
            return;
        }
        setCallSite(method_info);
    }

    public void setCallSite(method_info method_info) {
        access_flags = method_info.access_flags;
        name_index = method_info.name_index;
        descriptor_index = method_info.descriptor_index;
        Integer count = TypeUtils.byteArr2Int(method_info.attribute_count.u2);

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
