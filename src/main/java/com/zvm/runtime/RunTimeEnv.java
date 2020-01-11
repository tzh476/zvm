package com.zvm.runtime;

import com.zvm.gc.GC;
import com.zvm.jnative.NativeMethod;
import com.zvm.memory.JavaHeap;
import com.zvm.memory.MethodArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * zvm运行环境
 */
public class RunTimeEnv {

    public JavaHeap javaHeap;
    public MethodArea methodArea;
    public static Map<String, NativeMethod> nativeMethodMap;

    public GC gc;

    public RunTimeEnv(){
        methodArea = new MethodArea();
        javaHeap = new JavaHeap();
        nativeMethodMap = new HashMap<>();
        gc = new GC();
    }

}
