package com.zvm;

public class Cmd {
    /*
     * -Xjre F:\LAMP\Java\jdk1.8.0_45\jre -userClassPath F:\projects\zvm\bytecode FibonacciTest
     * */
    public static String Xjre;
    public static String userClassPath;  /*classpath*/

    public static String curClassName;
    public static String curClassNamePath;

    public final static String XJRE = "-Xjre";
    public final static String CP = "-cp";

    public static void processCmd(String[] args) {
        /*
         * -Xjre F:\LAMP\Java\jdk1.8.0_45\jre -userClassPath F:\projects\zvm\bytecode FibonacciTest
         * */
        int len = args.length;
        for (int i = 0; i < len; i++) {
            switch (args[i]) {
                case Cmd.XJRE: {
                    i++;
                    Cmd.Xjre = args[i];
                    if(Xjre != null && !Xjre.endsWith("\\")){
                        Xjre = Xjre + "\\";
                    }
                }
                break;
                case Cmd.CP: {
                    i++;
                    Cmd.userClassPath = args[i];
                    if(userClassPath != null && !userClassPath.endsWith("\\")){
                        userClassPath = userClassPath + "\\";
                    }
                }
                break;
            }
        }

        curClassName = args[len - 1];
        curClassNamePath = userClassPath + curClassName.replace(".", "\\") + ".class";
    }

    public static String processPath(String path) {
        if(path == null || path.isEmpty()){
            return null;
        }
        if(path.endsWith(".class")){
            return path;
        }

        path = userClassPath + path + ".class";

        return path;
    }
}
