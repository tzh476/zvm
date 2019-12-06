//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package java.lang;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.nio.channels.Channel;
import java.nio.channels.spi.SelectorProvider;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyPermission;
import sun.misc.JavaLangAccess;
import sun.misc.SharedSecrets;
import sun.misc.VM;
import sun.misc.Version;
import sun.nio.ch.Interruptible;
import sun.reflect.CallerSensitive;
import sun.reflect.ConstantPool;
import sun.reflect.Reflection;
import sun.reflect.annotation.AnnotationType;
import sun.security.util.SecurityConstants;

public final class System {
    public static final InputStream in;
    public static final PrintStream out;
    public static final PrintStream err;
    private static volatile SecurityManager security;
    private static volatile Console cons;
    private static Properties props;
    private static String lineSeparator;

    private static native void registerNatives();

    private System() {
    }

    public static void setIn(InputStream var0) {
        checkIO();
        setIn0(var0);
    }

    public static void setOut(PrintStream var0) {
        checkIO();
        setOut0(var0);
    }

    public static void setErr(PrintStream var0) {
        checkIO();
        setErr0(var0);
    }

    public static Console console() {
        if (cons == null) {
            Class var0 = System.class;
            synchronized(System.class) {
                cons = SharedSecrets.getJavaIOAccess().console();
            }
        }

        return cons;
    }

    public static Channel inheritedChannel() throws IOException {
        return SelectorProvider.provider().inheritedChannel();
    }

    private static void checkIO() {
        SecurityManager var0 = getSecurityManager();
        if (var0 != null) {
            var0.checkPermission(new RuntimePermission("setIO"));
        }

    }

    private static native void setIn0(InputStream var0);

    private static native void setOut0(PrintStream var0);

    private static native void setErr0(PrintStream var0);

    public static void setSecurityManager(SecurityManager var0) {
        try {
            var0.checkPackageAccess("java.lang");
        } catch (Exception var2) {
            ;
        }

        setSecurityManager0(var0);
    }

    private static synchronized void setSecurityManager0(final SecurityManager var0) {
        SecurityManager var1 = getSecurityManager();
        if (var1 != null) {
            var1.checkPermission(new RuntimePermission("setSecurityManager"));
        }

        if (var0 != null && var0.getClass().getClassLoader() != null) {
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                public Object run() {
                    var0.getClass().getProtectionDomain().implies(SecurityConstants.ALL_PERMISSION);
                    return null;
                }
            });
        }

        security = var0;
    }

    public static SecurityManager getSecurityManager() {
        return security;
    }

    public static native long currentTimeMillis();

    public static native long nanoTime();

    public static native void arraycopy(Object var0, int var1, Object var2, int var3, int var4);

    public static native int identityHashCode(Object var0);

    private static native Properties initProperties(Properties var0);

    public static Properties getProperties() {
        SecurityManager var0 = getSecurityManager();
        if (var0 != null) {
            var0.checkPropertiesAccess();
        }

        return props;
    }

    public static String lineSeparator() {
        return lineSeparator;
    }

    public static void setProperties(Properties var0) {
        SecurityManager var1 = getSecurityManager();
        if (var1 != null) {
            var1.checkPropertiesAccess();
        }

        if (var0 == null) {
            var0 = new Properties();
            initProperties(var0);
        }

        props = var0;
    }

    public static String getProperty(String var0) {
        checkKey(var0);
        SecurityManager var1 = getSecurityManager();
        if (var1 != null) {
            var1.checkPropertyAccess(var0);
        }

        return props.getProperty(var0);
    }

    public static String getProperty(String var0, String var1) {
        checkKey(var0);
        SecurityManager var2 = getSecurityManager();
        if (var2 != null) {
            var2.checkPropertyAccess(var0);
        }

        return props.getProperty(var0, var1);
    }

    public static String setProperty(String var0, String var1) {
        checkKey(var0);
        SecurityManager var2 = getSecurityManager();
        if (var2 != null) {
            var2.checkPermission(new PropertyPermission(var0, "write"));
        }

        return (String)props.setProperty(var0, var1);
    }

    public static String clearProperty(String var0) {
        checkKey(var0);
        SecurityManager var1 = getSecurityManager();
        if (var1 != null) {
            var1.checkPermission(new PropertyPermission(var0, "write"));
        }

        return (String)props.remove(var0);
    }

    private static void checkKey(String var0) {
        if (var0 == null) {
            throw new NullPointerException("key can't be null");
        } else if (var0.equals("")) {
            throw new IllegalArgumentException("key can't be empty");
        }
    }

    public static String getenv(String var0) {
        SecurityManager var1 = getSecurityManager();
        if (var1 != null) {
            var1.checkPermission(new RuntimePermission("getenv." + var0));
        }

        return ProcessEnvironment.getenv(var0);
    }

    public static Map<String, String> getenv() {
        SecurityManager var0 = getSecurityManager();
        if (var0 != null) {
            var0.checkPermission(new RuntimePermission("getenv.*"));
        }

        return ProcessEnvironment.getenv();
    }

    public static void exit(int var0) {
        Runtime.getRuntime().exit(var0);
    }

    public static void gc() {
        Runtime.getRuntime().gc();
    }

    public static void runFinalization() {
        Runtime.getRuntime().runFinalization();
    }

    /** @deprecated */
    @Deprecated
    public static void runFinalizersOnExit(boolean var0) {
        Runtime.runFinalizersOnExit(var0);
    }

    @CallerSensitive
    public static void load(String var0) {
        Runtime.getRuntime().load0(Reflection.getCallerClass(), var0);
    }

    @CallerSensitive
    public static void loadLibrary(String var0) {
        Runtime.getRuntime().loadLibrary0(Reflection.getCallerClass(), var0);
    }

    public static native String mapLibraryName(String var0);

    private static PrintStream newPrintStream(FileOutputStream var0, String var1) {
        if (var1 != null) {
            try {
                return new PrintStream(new BufferedOutputStream(var0, 128), true, var1);
            } catch (UnsupportedEncodingException var3) {
                ;
            }
        }

        return new PrintStream(new BufferedOutputStream(var0, 128), true);
    }

    private static void initializeSystemClass() {
        props = new Properties();
        initProperties(props);
        VM.saveAndRemoveProperties(props);
        lineSeparator = props.getProperty("line.separator");
        Version.init();
        FileInputStream var0 = new FileInputStream(FileDescriptor.in);
        FileOutputStream var1 = new FileOutputStream(FileDescriptor.out);
        FileOutputStream var2 = new FileOutputStream(FileDescriptor.err);
        setIn0(new BufferedInputStream(var0));
        setOut0(newPrintStream(var1, props.getProperty("sun.stdout.encoding")));
        setErr0(newPrintStream(var2, props.getProperty("sun.stderr.encoding")));
        loadLibrary("zip");
        Terminator.setup();
        VM.initializeOSEnvironment();
        Thread var3 = Thread.currentThread();
        var3.getThreadGroup().add(var3);
        setJavaLangAccess();
        VM.booted();
    }

    private static void setJavaLangAccess() {
        SharedSecrets.setJavaLangAccess(new JavaLangAccess() {
            public ConstantPool getConstantPool(Class<?> var1) {
                return var1.getConstantPool();
            }

            public boolean casAnnotationType(Class<?> var1, AnnotationType var2, AnnotationType var3) {
                return var1.casAnnotationType(var2, var3);
            }

            public AnnotationType getAnnotationType(Class<?> var1) {
                return var1.getAnnotationType();
            }

            public Map<Class<? extends Annotation>, Annotation> getDeclaredAnnotationMap(Class<?> var1) {
                return var1.getDeclaredAnnotationMap();
            }

            public byte[] getRawClassAnnotations(Class<?> var1) {
                return var1.getRawAnnotations();
            }

            public byte[] getRawClassTypeAnnotations(Class<?> var1) {
                return var1.getRawTypeAnnotations();
            }

            public byte[] getRawExecutableTypeAnnotations(Executable var1) {
                return Class.getExecutableTypeAnnotationBytes(var1);
            }

            public <E extends Enum<E>> E[] getEnumConstantsShared(Class<E> var1) {
                //return (Enum[])var1.getEnumConstantsShared();
                return (E[]) var1.getEnumConstantsShared();
            }

            public void blockedOn(Thread var1, Interruptible var2) {
                var1.blockedOn(var2);
            }

            public void registerShutdownHook(int var1, boolean var2, Runnable var3) {
                Shutdown.add(var1, var2, var3);
            }

            public int getStackTraceDepth(Throwable var1) {
                return var1.getStackTraceDepth();
            }

            public StackTraceElement getStackTraceElement(Throwable var1, int var2) {
                return var1.getStackTraceElement(var2);
            }

            public String newStringUnsafe(char[] var1) {
                return new String(var1, true);
            }

            public Thread newThreadWithAcc(Runnable var1, AccessControlContext var2) {
                return new Thread(var1, var2);
            }

            public void invokeFinalize(Object var1) throws Throwable {
                var1.finalize();
            }
        });
    }

    static {
        registerNatives();
        in = null;
        out = null;
        err = null;
        security = null;
        cons = null;
    }
}
