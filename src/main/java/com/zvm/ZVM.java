package com.zvm;

import com.zvm.interpreter.Interpreter;
import com.zvm.runtime.JavaClass;
import com.zvm.runtime.RunTimeEnv;

public class ZVM {
    public static RunTimeEnv zvmEnv;
    public ZVM(RunTimeEnv zvmEnv){
        ZVM.zvmEnv = zvmEnv;
    }

    public void callMain(String main, String descriptor, String classPath){
        JavaClass javaClass = zvmEnv.methodArea.loadClass(classPath);
        Interpreter interpreter = new Interpreter(zvmEnv);
        interpreter.initInstructions();
        zvmEnv.methodArea.linkClass(classPath);
        interpreter.invokeByName(javaClass, main, descriptor);
    }


}
