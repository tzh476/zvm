package com.zvm;

import com.zvm.jnative.java.lang.CONSTANT;

import java.util.ArrayList;
import java.util.List;

/**
 * zvm运行环境
 */
public class RunTimeEnv {

    public JavaHeap javaHeap;
    public MethodArea methodArea;
    public List<String> nativeMethods;


    public RunTimeEnv(){
        methodArea = new MethodArea();
        javaHeap = new JavaHeap();
        nativeMethods = new ArrayList<>();
    }

    public void invokeNativeMethod(String className, String descriptorName, String methodName){
        String key = className + "~" +descriptorName + "~" + methodName;
        if(!nativeMethods.contains(key)){
            return;
        }
        //registerNative("java/lang/System", "(Ljava/lang/Object;ILjava/lang/Object;II)V", "arraycopy");
        if(CONSTANT.ARRAYCOPY_CLASSNAME.equals(className) && CONSTANT.ARRAYCOPY_DESCRIPTOR.equals(descriptorName)
                && CONSTANT.ARRAYCOPY_METHODNAME.equals(methodName)){
           // com.zvm.jnative.java.lang.System.arraycopy();
        }

    }
}
