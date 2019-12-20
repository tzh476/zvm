Java实现简易JVM
-  主要这几个模块：
1. 读取class文件
2. 取opcode，解释执行程序
3. 方法调用、方法多态调用
4. 待续  

- 读取后可以**解析**为内存中classfile的类demo  
zvm\bytecode\Test.class  
zvm\bytecode\java\lang\System.class  
zvm\bytecode\java\io\PrintStream.class  
zvm\bytecode\com\zvm\javaclass\integer\Table1.class

- 可运行demo：
1. 循环运算，入栈出栈等  
zvm\javaclass\GaussTest.java  

2. 方法调用，包括getstatic、invokestatic、invokespecial指令  
zvm\bytecode\FibonacciTest.class  
zvm\bytecode\ch07\InvokeSpecialTest.class

3. 支持invokevirtual，方法多态调用，测试类：  
zvm\bytecode\ch07\InvokeVirtualTest.class


- 已实现指令
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
