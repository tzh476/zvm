Java实现简易JVM
#  主要这几个模块：
<details>
    <summary>1. 读取并解析class文件，如String、Thread等类(支持jdk8及以下)</summary>
    
    部分类可能在demo运行时用到:
    + `zvm\bytecode\java\lang\System.class `
    + `zvm\bytecode\java\io\PrintStream.class  `
    + `zvm\bytecode\java\lang\Thread.class`
    + `zvm\bytecode\com\zvm\javaclass\integer\Table1.class`(注解相关)
</details>
<details>
<summary>2. 取opcode，解释执行程序。循环运算，入栈出栈</summary>

执行样例：
```java
public class GaussTest {
    public GaussTest() {
    }
    public static void main(String[] args) {
        int sum = 0;
        for(int i = 5; i <= 20; i += 10) {
            sum += i;
        }
        System.out.println(sum);
    }
}
```

输出结果：
```java
file path : GaussTest
20
```
</details>
<details>
<summary>3. 方法调用(静态方法、构造方法、实例方法(支持继承多态))</summary>

1. 静态递归方法执行样例(invokestatic)：
```java
public class FibonacciTest {
    public static void main(String[] args) {
        long x = fibonacci(8);
        System.out.println(x);
    }
    private static long fibonacci(long n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }
}
```

输出结果：
```java
file path : FibonacciTest
21
```

2. 构造方法调用(invokespecial)
```java
public class FibonacciTest {
    public static void main(String[] args) {
        long x = fibonacci(8);
        System.out.println(x);
    }
    private static long fibonacci(long n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }
}
```

输出结果：
```java
file path : FibonacciTest
21
```

3. 调用实例方法，支持继承多态(invokevirtual)
```java
public class InvokeVirtualTest {
    public static void main(String[] args) {
        Vector2D v2 = new Vector2D(2.1, 2.2);
        Vector2D v3 = new Vector3D(3.1, 3.2, 3.3);
        v2.multiply(2);
        v3.multiply(3);
        System.out.println(v2.x);
        System.out.println(v2.y);
        System.out.println(v3.x);
        System.out.println(v3.y);
        System.out.println(((Vector3D)v3).z);
    }
}
```

输出结果：
```java
file path : ch07/InvokeVirtualTest
4.2
4.4
9.3
9.600000000000001
9.899999999999999
```
</details>
<details>
    <summary>4. 方法调用、方法多态调用</summary>
    
    部分类可能在demo运行时用到:
    + `java.lang.String`
    + `java.lang.StringBuilder`
    + `java.lang.Throwable`
    + `java.lang.Math(::random())`
    + `java.lang.Runnable`
    + `java.lang.Thread`
    + 
</details>


# 已实现指令(绝大部分实现了)
1. 加载(load)、存储(store)指令,将数据在局部变量表和操作数栈中来回传输:  
局部变量表->操作数栈：dload,dload_n; iload,iload_n; lload,lload_n; aload,aload_n  
操作数栈->局部变量表：dstore,dstore_n; istore,istore_n; lstore,lstore_n; astore,astore_n  
常量到操作数栈: bipush,ldc,ldc_w,ldc2_w,iconst_n

2. 运算指令:  
加法：iadd,ladd  
减法: lsub  
乘法: dmul
自增: iinc  
比较: lcmp  

3. 对象创建和操作：  
创建实例: new  
访问类或实例字段:getstatic,getfield,putfield
 
4. 操作数栈管理：  
dup  

5. 控制转移:  
条件分支: ifeq,ifne,iflt,ifge,ifgt,ifle,if_icmpgt  
无条件分支: goto_

6. 方法调用和返回  
invokevirtual: 调用对象实例方法，根据对象实际类型分派  
invokespecial：特殊处理的实例方法：实例初始化方法，父类方法   
invokestatic：调用类方法  


- 备注：
1. 由jdk1.8.0_45\jre\lib\rt.jar中的java文件夹得到zvm\bytecode\java文件夹

- 文档、书籍参考：
1. java虚拟机规范：https://docs.oracle.com/javase/specs/jvms/se8/jvms8.pdf
2. 周志明的《深入理解java虚拟机》
3. java虚拟机规范(java se7)中文版
4. java虚拟机规范(java se8)中文版
5. 《自己动手写Java虚拟机》

- 代码参考：
1. c++实现的java虚拟机：https://github.com/kelthuzadx/yvm
2. go实现jvm：https://github.com/zxh0/jvmgo-book
3. Hotspot源码：https://github.com/tzh476/Hotspot

- 工具
1. 类解析工具：https://github.com/zxh0/classpy
