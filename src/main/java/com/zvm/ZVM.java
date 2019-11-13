package com.zvm;

public class ZVM {
    private RunTimeEnv zvmEnv;
    public ZVM(RunTimeEnv zvmEnv){
        this.zvmEnv = zvmEnv;
    }

    public void callMain(String main, String descriptor, String classPath){
        JavaClass javaClass = zvmEnv.methodArea.loadClass(classPath);
        Interpreter interpreter = new Interpreter(zvmEnv);
        interpreter.invokeByName(javaClass, main, descriptor);
//        loadClass(userClassPath);
//        linkClass();
//        initClass();
    }


}
