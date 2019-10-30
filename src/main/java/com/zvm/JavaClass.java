package com.zvm;

/**
 * classFile是读取字节码为有规范的数据结构。
 * JavaClass作为运行时，来对classFile的读取入口
 */
public class JavaClass {
    private ClassFile classFile;

    public method_info findMethod(String methodName, String methodDescriptor){

        Integer methodSize = TypeUtils.byte2Int(classFile.methods_count.u2);
        for(Integer i = 0; i < methodSize; i++){

            String cpMethodName = getString(classFile.methods[i].name_index);
            String cpMethodDescriptor = getString(classFile.methods[i].descriptor_index);

            if(TypeUtils.compare(cpMethodName, methodName) && TypeUtils.compare(cpMethodDescriptor, cpMethodDescriptor)){
                return classFile.methods[i];
            }
        }
        return null;
    }

    public String getString(u2 index){
        CONSTANT_Utf8 constant_utf8 = (CONSTANT_Utf8) classFile.constant_pool.cp_info[TypeUtils.byte2Int(index.u2)];
        return TypeUtils.u12String(constant_utf8.bytes);
    }

    public void setClassFile(ClassFile classFile){
        this.classFile = classFile;
    }
}
