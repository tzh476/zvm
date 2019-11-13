java实现jvm
-  主要这几个模块：
1. 读取class文件
2. 取opcode，解释执行程序
3. 函数调用
4. 待续  

- 读取后可以**解析**为内存中classfile的类demo  
zvm\bytecode\Test.class  
zvm\bytecode\java\lang\System.class  
zvm\bytecode\java\io\PrintStream.class  

- 可运行demo：
1. 循环运算，入栈出栈等  
zvm\javaclass\GaussTest.java  

2. 函数调用，包括getstatic、invokestatic、invokevirtual指令  
zvm\javaclass\FibonacciTest.java  

- 已实现指令
1. 待续  

- 备注：
1. 由jdk1.8.0_45\jre\lib\rt.jar中的java文件夹得到zvm\bytecode\java文件夹

- 文档、书籍参考：
1. java虚拟机规范：https://docs.oracle.com/javase/specs/jvms/se8/jvms8.pdf
2. 周志明的《深入理解java虚拟机》
3. java虚拟机规范(java se7)中文版
4. java虚拟机规范(java se8)中文版
5. 自己动手写Java虚拟机

- 代码参考：
1. c++实现的java虚拟机：https://github.com/kelthuzadx/yvm
2. go实现jvm：https://github.com/zxh0/jvmgo-book
3. Hotspot源码：https://github.com/tzh476/Hotspot

- 工具
1. 类解析工具：https://github.com/zxh0/classpy
