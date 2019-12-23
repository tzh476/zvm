package com.zvm;

import com.zvm.interpreter.Interpreter;
import com.zvm.jnative.java.lang.CONSTANT;
import com.zvm.runtime.JavaClass;
import com.zvm.runtime.RunTimeEnv;

public class ZVM {
    public static RunTimeEnv zvmEnv;
    public ZVM(RunTimeEnv zvmEnv){
        this.zvmEnv = zvmEnv;
    }

    public void callMain(String main, String descriptor, String classPath){
        JavaClass javaClass = zvmEnv.methodArea.loadClass(classPath);
        Interpreter interpreter = new Interpreter(zvmEnv);
        interpreter.invokeByName(javaClass, main, descriptor);
    }

    /**
     * 注册本地方法
     */
    public void registerNatives() {
        //native.Register(jlSystem, "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", arraycopy)
        registerNative(CONSTANT.ARRAYCOPY_CLASSNAME, CONSTANT.ARRAYCOPY_DESCRIPTOR, CONSTANT.ARRAYCOPY_METHODNAME);
    }

    public void registerNative(String className, String descriptorName, String methodName){
        zvmEnv.nativeMethods.add(className + "~" +descriptorName + "~" + methodName);
    }

}
