package com.zvm;

import com.alibaba.fastjson.JSON;
import com.zvm.basestruct.u2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * classFile是读取字节码为有规范的数据结构。
 * JavaClass作为运行时，来对classFile的读取入口
 */
public class JavaClass {
    public static final Integer CLASS_BYTECODE_FILE_MAX = 63325;

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

    public ClassFile readBytecode2ClassFile(String path){
        File classFile = new File(path);

        byte[] byteCode = new byte[CLASS_BYTECODE_FILE_MAX];

        try {
            FileInputStream in = new FileInputStream(classFile);
            in.read(byteCode);
        } catch (FileNotFoundException e) {
            System.out.println("file not found " + path);
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.out.println("读取 "+ path + " 失败");
            e.printStackTrace();
            return null;
        }

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
