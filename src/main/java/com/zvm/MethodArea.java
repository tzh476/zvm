package com.zvm;

import java.util.HashMap;
import java.util.Map;

public class MethodArea {


    //public JavaClass[] javaClasses = new JavaClass[10];
    Map<String,JavaClass> javaClasses = new HashMap<>();

    /**
     * 加载类
     * @param classPath
     */
    public JavaClass loadClass(String classPath){
        JavaClass javaClass = new JavaClass(classPath);
        javaClass.readBytecode2ClassFile(classPath);
        javaClasses.put(classPath, javaClass);
        return javaClass;
    }

    /**
     * 链接类
     */
    public void linkClass(){
        verification();
        preparation();
        resolution();
    }

    /**
     * 验证
     */
    public void verification(){

    }

    /**
     * 准备
     */
    public void preparation(){

    }

    /**
     * 解析
     */
    public void resolution(){

    }

    /**
     * 初始化类
     */
    public void initClass(){

    }
}
