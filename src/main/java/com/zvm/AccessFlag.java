package com.zvm;

public class AccessFlag {
    public static Integer ACC_PUBLIC = 0x0001;
    public static Integer ACC_FINAL = 0x0010;//  不允许有子类。
    public static Integer ACC_SUPER = 0x0020;//当用到 invokespecial 指令时，需要特殊处理的父类方法。
    public static Integer ACC_INTERFACE = 0x0200 ;// 标识定义的是接口而不是类。
    public static Integer ACC_ABSTRACT = 0x0400 ; //不能被实例化。
    public static Integer  ACC_SYNTHETIC = 0x1000 ; //标识并非 Java 源码生成的代码。
    public static Integer ACC_ANNOTATION = 0x2000;//  标识注解类型
    public static Integer ACC_ENUM = 0x4000;//  标识枚举类型

    //ACC_PUBLIC  0x0001  public，方法可以从包外访问
    public static Integer ACC_PRIVATE = 0x0002  ;//private，方法只能本类中访问
    public static Integer ACC_PROTECTED = 0x0004 ;// protected，方法在自身和子类可以访问
    public static Integer ACC_STATIC = 0x0008 ;// static，静态方法
    //public static Integer ACC_FINAL = 0x0010  ;//final，方法不能被重写（覆盖）
    public static Integer  ACC_SYNCHRONIZED = 0x0020;//  synchronized，方法由管程同步
    public static Integer  ACC_BRIDGE = 0x0040 ;// bridge，方法由编译器产生
    public static Integer ACC_VARARGS = 0x0080;//  表示方法带有变长参数
    public static Integer  ACC_NATIVE = 0x0100 ;// native，方法引用非 java 语言的本地方法
    //public static Integer ACC_ABSTRACT = 0x0400 ;// abstract，方法没有具体实现
    public static Integer  ACC_STRICT = 0x0800  ;//strictfp，方法使用 FP-strict 浮点格式
    //public static Integer  ACC_SYNTHETIC = 0x1000;//  方法在源文件中不出现，由编译器产生


}
