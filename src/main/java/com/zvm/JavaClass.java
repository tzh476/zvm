package com.zvm;

import com.alibaba.fastjson.JSON;
import com.zvm.basestruct.u2;
import com.zvm.runtime.struct.Slot;

import java.util.List;


/**
 * classFile是读取字节码为有规范的数据结构。
 * JavaClass作为运行时，来对classFile的读取入口
 */
public class JavaClass {
    public static final Integer CLASS_BYTECODE_FILE_MAX = 63325;
    public int staticSlotCount;
    public StaticVars staticVars;

    private String classPath;
    private ClassFile classFile;


    public JavaClass(String classPath){
        this.classPath = classPath;
    }

    public method_info findMethod(String methodName, String methodDescriptor){

        Integer methodSize = TypeUtils.byteArr2Int(classFile.methods_count.u2);
        for(Integer i = 0; i < methodSize; i++){

            String cpMethodName = getString(classFile.methods[i].name_index);
            String cpMethodDescriptor = getString(classFile.methods[i].descriptor_index);

            if(TypeUtils.compare(cpMethodName, methodName) && TypeUtils.compare(cpMethodDescriptor, methodDescriptor)){
                return classFile.methods[i];
            }
        }
        return null;
    }

    public field_info findField(String fieldName, String fieldDescriptor){

        Integer fieldSize = TypeUtils.byteArr2Int(classFile.field_count.u2);
        for(Integer i = 0; i < fieldSize; i++){

            String cpFieldName = getString(classFile.fields[i].name_index);
            String cpFieldDescriptor = getString(classFile.fields[i].descriptor_index);

            if(TypeUtils.compare(cpFieldName, fieldName) && TypeUtils.compare(cpFieldDescriptor, fieldDescriptor)){
                return classFile.fields[i];
            }
        }
        return null;
    }

    public ClassFile readBytecode2ClassFile(byte[] byteCode){


        this.classFile = new ClassFile();
        try {
            this.classFile.processByteCode(byteCode);
            System.out.println(JSON.toJSONString(this.classFile));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(JSON.toJSONString(this.classFile));
        }

        return this.classFile;
    }


    public String getString(u2 index){
        CONSTANT_Utf8 constant_utf8 = (CONSTANT_Utf8) classFile.constant_pool.cp_info[TypeUtils.byteArr2Int(index.u2) - 1];
        return TypeUtils.u12String(constant_utf8.bytes);
    }

    public void setClassFile(ClassFile classFile){
        this.classFile = classFile;
    }
    public ClassFile getClassFile(){
        return this.classFile;
    }
}