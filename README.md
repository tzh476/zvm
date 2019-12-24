Java实现简易JVM
#  主要这几个模块：
<details>
    <summary>1. 读取并解析class文件，如String、Thread等类(支持jdk8及以下)</summary>
    
部分类可能在demo运行时用到:
+ `zvm\bytecode\java\lang\System.class `
+ `zvm\bytecode\java\io\PrintStream.class  `
+ `zvm\bytecode\java\lang\Thread.class`
+ `zvm\bytecode\com\zvm\javaclass\integer\Table1.class(注解相关)`
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

- 静态递归方法执行样例(invokestatic)：
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

- 构造方法调用(invokespecial)
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

- 调用实例方法，支持继承多态(invokevirtual)
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
<summary>4. 数组</summary>

- 一维int数组冒泡排序：
```java
public class BubbleSortTest {
    public static void main(String[] args) {
        int[] arr = {
            22, 84, 77, 11, 95,  9, 78, 56, 
            36, 97, 65, 36, 10, 24 ,92, 48
        };
        //printArray(arr);
        bubbleSort(arr);
        //System.out.println(123456789);
        printArray(arr);
    }
    private static void bubbleSort(int[] arr) {
        boolean swapped = true;
        int j = 0;
        int tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < arr.length - j; i++) {
                if (arr[i] > arr[i + 1]) {
                    tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                    swapped = true;
                }
            }
        }
    }
    private static void printArray(int[] arr) {
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
```
输出结果：
```java
file path : ch08/BubbleSortTest
9
10
...
```

- 一维double数组冒泡排序
```java
public class DoubleBubbleSortTest {
    public static void main(String[] args) {
        double[] arr = {
            22.2, 84.4, 77.5, 11.2, 95.3,  9.2, 78.2, 56.2,
            36.1, 97.1, 65.1, 36.1, 10.3, 24.3 ,92.3, 48.3
        };

        //printArray(arr);
        bubbleSort(arr);
        //System.out.println(123456789);
        printArray(arr);
    }
    private static void bubbleSort(double[] arr) {
        boolean swapped = true;
        int j = 0;
        double tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < arr.length - j; i++) {
                if (arr[i] > arr[i + 1]) {
                    tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                    swapped = true;
                }
            }
        }
    }
    private static void printArray(double[] arr) {
        for (double i : arr) {
            System.out.println(i);
        }
    }
}

```

输出结果：
```java
file path : ch08/DoubleBubbleSortTest
9.2
10.3
11.2
22.2
24.3
...
```

</details>

<details>
<summary>5. 字符串和字符串数组</summary>

- 字符串加法，涉及类有java/lang/StringBuilder、java/lang/AbstractStringBuilder、java/lang/Math
、java/util/Arrays、java/io/FilterOutputStream、java/io/OutputStream、 java/io/PrintStream、java/lang/String：
```java
public class StringBuilderTest {
    public static void main(String[] args) {
        String hello = "hello,";
        String world = "world!";
        String str = hello + world;
        System.out.println(str);
    }
}
```
输出结果：
```java
file path : ch09/StringBuilderTest
总内存:8912 分配：8完成 当前已使用:8
总内存:8912 分配：12完成 当前已使用:20
...
hello,world!
...
```

- 字符串数组
```java
public class ArrayDemo {
    public static void main(String[] args) {
        int[] a1 = new int[10];       // newarray
        String[] a2 = new String[10]; // anewarray
        //int[][] a3 = new int[10][10]; // multianewarray
        int x = a1.length;            // arraylength
        a1[0] = 100;                  // iastore
        int y = a1[0];                // iaload
        a2[0] = "0abc";                // aastore
        String s = a2[0];             // aaload
        System.out.println( s);
        a2[1] = "1xxxxyyxyy";
        a2[2] = "2xxxxyyxyy";

        for(int i = 0; i < 3; i++){
            System.out.println(a2[i] + " stringbuilderTest");
        }
    }
}
```

输出结果：
```java
file path : ch09/ArrayDemo
总内存:8912 分配：40完成 当前已使用:40
...
0abc
总内存:8912 分配：8完成 当前已使用:104
总内存:8912 分配：20完成 当前已使用:124
...
0abc stringbuilderTest
总内存:8912 分配：8完成 当前已使用:364
总内存:8912 分配：32完成 当前已使用:396
...
1xxxxyyxyy stringbuilderTest
总内存:8912 分配：8完成 当前已使用:580
总内存:8912 分配：32完成 当前已使用:612
...
2xxxxyyxyy stringbuilderTest
...
```
</details>

<details>
<summary>6. 调用本地方法</summary>

- 只实现了这个方法println，里面调用了arraycopy
```java
public class StringBuilderTest {
    public static void main(String[] args) {
        String hello = "hello,";
        String world = "world!";
        String str = hello + world;
        System.out.println(str);
    }
}
```
输出结果：
```java
file path : ch09/StringBuilderTest
hello,world!
...
```
</details>

<details>
<summary>7. gc(标记清除算法)</summary>

- 在zvm\src\main\java\com\zvm\memory\JavaHeap.java的HEAP_MAX_SIZE(此例中为32)的大小，
```java
public class GCTest1 {
    private static final int SIZE = 3;
    public static void main(String[] args){
        test0();
        test1();
        test2();
    }
    private static void test0() {
        /*字符串会创建22 byte + 8byte的数组:8byte:为String对象，22byte为char[11]*/
        //System.out.println("test0 start");
        int[] arr = new int[SIZE];
        for (int i = 0; i < SIZE; i++){
            arr[i] = 100 + i;
        }
        //System.out.println("test0 start");
    }

    private static void test1() {
        //System.out.println("test1 start");
        int[] arr = new int[SIZE];
        for (int i = 0; i < SIZE; i++){
            arr[i] = 100 + i;
        }
        //System.out.println("test1 start");
    }

    private static void test2() {
        //System.out.println("test2 start");
        int[] arr = new int[SIZE];
        for (int i = 0; i < SIZE; i++){
            arr[i] = 100 + i;
        }
        //System.out.println("test2 start");
    }
}
```
输出结果：
```java
file path : gc/GCTest1
总内存:32 分配：12完成 当前已使用:12
总内存:32 分配：12完成 当前已使用:24
总内存:32 已使用：24 当前需分配：12 
总内存:32 回收情况：24->0 当前需分配：12 
总内存:32 分配：12完成 当前已使用:12
...
```
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
