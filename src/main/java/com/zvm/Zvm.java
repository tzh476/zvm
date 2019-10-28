package com.zvm;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Zvm {
    public static final Integer CLASS_BYTECODE_FILE_MAX = 63325;

    ClassFile[] classFiles ;



    public void readBytecode2ClassFile(String path){
        File classFile = new File(path);

        byte[] byteCode = new byte[CLASS_BYTECODE_FILE_MAX];

        try {
            FileInputStream in = new FileInputStream(classFile);
            in.read(byteCode);
        } catch (FileNotFoundException e) {
            System.out.println("file not found " + path);
            e.printStackTrace();
            return;
        } catch (IOException e) {
            System.out.println("读取 "+ path + " 失败");
            e.printStackTrace();
            return;
        }

        ClassFile classFile0 = new ClassFile();
        try {
            classFile0.processByteCode(byteCode);
            System.out.println(JSON.toJSONString(classFile0));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(JSON.toJSONString(classFile0));
        }
    }
}
