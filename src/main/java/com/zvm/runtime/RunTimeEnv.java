package com.zvm.runtime;

import com.zvm.gc.GC;
import com.zvm.jnative.CONSTANT;
import com.zvm.memory.JavaHeap;
import com.zvm.memory.MethodArea;

import java.util.ArrayList;
import java.util.List;

/**
 * zvm运行环境
 */
public class RunTimeEnv {

    public JavaHeap javaHeap;
    public MethodArea methodArea;
    public static List<String> nativeMethods;
    public GC gc;

    public RunTimeEnv(){
        methodArea = new MethodArea();
        javaHeap = new JavaHeap();
        nativeMethods = new ArrayList<>();
        gc = new GC();
    }

}
