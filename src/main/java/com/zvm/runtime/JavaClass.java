package com.zvm.runtime;

import com.alibaba.fastjson.JSON;
import com.zvm.utils.TypeUtils;
import com.zvm.ZVM;
import com.zvm.basestruct.u2;
import com.zvm.classfile.constantpool.CONSTANT_Utf8;
import com.zvm.classfile.ClassFile;
import com.zvm.classfile.field_info;
import com.zvm.classfile.method_info;


/**
 * classFile是读取字节码为有规范的数据结构。
 */
public class JavaClass {
    public static final Integer CLASS_BYTECODE_FILE_MAX = 63325;
    public int staticFieldSlotCount;
    public int instanceFieldSlotCount;
    public StaticVars staticVars;
    public String superClassName;
   // public JavaClass superClass;


    public String classPath;
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

        /*找不到在父类中找*/
        JavaClass superClass = ZVM.zvmEnv.methodArea.findClass(this.superClassName);
        if(superClass != null){
            return superClass.findField(fieldName, fieldDescriptor);
        }
        return null;
    }

    public ClassFile readBytecode2ClassFile(byte[] byteCode){


        this.classFile = new ClassFile();
        try {
            this.classFile.processByteCode(byteCode);
            //System.out.println(JSON.toJSONString(this.classFile));
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
