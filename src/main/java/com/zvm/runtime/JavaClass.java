package com.zvm.runtime;

import com.alibaba.fastjson.JSON;
import com.zvm.classfile.constantpool.ConstantUtf8;
import com.zvm.utils.TypeUtils;
import com.zvm.ZVM;
import com.zvm.basestruct.U2;
import com.zvm.classfile.ClassFile;
import com.zvm.classfile.FieldInfo;
import com.zvm.classfile.MethodInfo;


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

    public MethodInfo findMethod(String methodName, String methodDescriptor){

        Integer methodSize = TypeUtils.byteArr2Int(classFile.methodsCount.u2);
        for(Integer i = 0; i < methodSize; i++){

            String cpMethodName = constantUtf8Index2String(classFile.methods[i].nameIndex);
            String cpMethodDescriptor = constantUtf8Index2String(classFile.methods[i].descriptorIndex);

            if(TypeUtils.compare(cpMethodName, methodName) && TypeUtils.compare(cpMethodDescriptor, methodDescriptor)){
                return classFile.methods[i];
            }
        }
        return null;
    }

    public FieldInfo findField(String fieldName, String fieldDescriptor){

        Integer fieldSize = TypeUtils.byteArr2Int(classFile.fieldCount.u2);
        for(Integer i = 0; i < fieldSize; i++){

            String cpFieldName = constantUtf8Index2String(classFile.fields[i].nameIndex);
            String cpFieldDescriptor = constantUtf8Index2String(classFile.fields[i].descriptorIndex);
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

    /**
     * 传入ConstantUtf8 类型的index，在常量池中获得对应字符串
     * @param index
     * @return
     */
    public String constantUtf8Index2String(U2 index){
        ConstantUtf8 constantUtf8 = (ConstantUtf8) classFile.constantPool.cpInfo[TypeUtils.byteArr2Int(index.u2) - 1];
        return TypeUtils.u12String(constantUtf8.bytes);
    }

    public void setClassFile(ClassFile classFile){
        this.classFile = classFile;
    }
    public ClassFile getClassFile(){
        return this.classFile;
    }
}
