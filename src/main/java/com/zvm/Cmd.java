package com.zvm;

public class Cmd {
    /** -Xjre F:\LAMP\Java\jdk1.8.0_45\jre -userClassPath F:\projects\zvm\bytecode FibonacciTest
     */
    public static String Xjre;

    public static String userClassPath;
    public static String curClassName;
    public static String curClassNamePath;

    public final static String XJRE = "-Xjre";
    public final static String CP = "-cp";

    public final static Integer HEAP_MAX_SIZE = 4 * 8000;


    public final static String UNIX_SLASH = "/";
    public final static String WINDOWS_SLASH = "\\";

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
                    Xjre = processOsSlash(Xjre);
                }
                break;
                case Cmd.CP: {
                    i++;
                    Cmd.userClassPath = args[i];
                    userClassPath = processOsSlash(userClassPath);
                }
                break;
                default:
            }
        }

        curClassName = args[len - 1];
        if(isWindows()){
            curClassNamePath = userClassPath + curClassName.replace(".", WINDOWS_SLASH) + ".class";
            curClassName = curClassName.replace(".", WINDOWS_SLASH) ;
        }else {
            curClassNamePath = userClassPath + curClassName.replace(".", UNIX_SLASH) + ".class";
            curClassName = curClassName.replace(".", UNIX_SLASH) ;
        }
    }

    private static String processOsSlash(String path) {
        if(path == null || path.length() == 0){
            return "";
        }
        if(!path.endsWith(UNIX_SLASH) || !path.endsWith(WINDOWS_SLASH)) {
            path += WINDOWS_SLASH;
        }
        if(isWindows()){
            path = path.replace(UNIX_SLASH, WINDOWS_SLASH);
        }else {
            path = path.replace(WINDOWS_SLASH, UNIX_SLASH);
        }
        return path;
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

    private static boolean isWindows(){
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName.contains("window")){
            return true;
        }
        return false;
    }
}
