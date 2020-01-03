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
        registerNative(CONSTANT.ARRAYCOPY_CLASSNAME, CONSTANT.ARRAYCOPY_DESCRIPTOR, CONSTANT.ARRAYCOPY_METHODNAME);
    }

    public void registerNative(String className, String descriptorName, String methodName){
        runTimeEnv.nativeMethods.add(className + "~" +descriptorName + "~" + methodName);
    }

    public static boolean hasNativeClass(Ref ref){
        String method = new StringBuilder(ref.className).append("~").append(ref.descriptorName).append("~").append(ref.refName).toString();
        if(RunTimeEnv.nativeMethods.contains(method)){
            return true;
        }
        return false;
    }
}
