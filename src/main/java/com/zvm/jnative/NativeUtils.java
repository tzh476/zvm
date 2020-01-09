package com.zvm.jnative;

import com.zvm.interpreter.Ref;
import com.zvm.runtime.RunTimeEnv;


public class NativeUtils {
    private RunTimeEnv runTimeEnv;

    public NativeUtils(RunTimeEnv runTimeEnv){
        this.runTimeEnv = runTimeEnv;
    }
    /**
     * 注册本地方法
     */
    public void registerNatives() {
        //native.Register(jlSystem, "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", arraycopy)
        registerNative(Constant.ARRAYCOPY_CLASSNAME, Constant.ARRAYCOPY_DESCRIPTOR, Constant.ARRAYCOPY_METHODNAME);
    }

    public void registerNative(String className, String descriptorName, String methodName){
        RunTimeEnv.nativeMethods.add(className + "~" +descriptorName + "~" + methodName);
    }

    public static boolean hasNativeClass(Ref ref){
        String method = new StringBuilder(ref.className).append("~").append(ref.descriptorName).append("~").append(ref.refName).toString();
        if(RunTimeEnv.nativeMethods.contains(method)){
            return true;
        }
        return false;
    }
}
