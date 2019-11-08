package com.zvm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.zvm.JavaClass.CLASS_BYTECODE_FILE_MAX;

public class MethodArea {
    //public JavaClass[] javaClasses = new JavaClass[10];
    Map<String,JavaClass> javaClasses = new HashMap<>();
    ZvmClassLoader zvmClassLoader = new ZvmClassLoader();

    /**
     * 加载类
     * @param classPath
     */
    public JavaClass loadClass(String classPath){
        JavaClass javaClass = new JavaClass(classPath);

        byte[] bytecode = readClass(classPath);

        javaClass.readBytecode2ClassFile(bytecode);
        javaClasses.put(classPath, javaClass);
        return javaClass;
    }

    private byte[] readClass(String path)  {
        path = Cmd.processPath(path);

        File classFile = new File(path);

        byte[] byteCode= new byte[CLASS_BYTECODE_FILE_MAX];

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
        return byteCode;
    }

    public JavaClass findClass(String className){
        boolean hasClass =  javaClasses.containsKey(className);
        if(hasClass){
            return javaClasses.get(className);
        }
        return null;
    }

    /**
     * 链接类
     */
    public void linkClass(){
        verification();
        preparation();
        resolved();
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
    public void resolved(){

    }

    /**
     * 初始化类
     */
    public void initClass(){

    }
}
