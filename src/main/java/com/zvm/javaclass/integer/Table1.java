package com.zvm.javaclass.integer;

import java.lang.annotation.*;
/*
* 参考：
* https://stackoverflow.com/questions/1458535/which-types-can-be-used-for-java-annotation-members
* http://web.archive.org/web/20131216093805/https://blogs.oracle.com/toddfast/entry/creating_nested_complex_java_annotations
* https://www.cnblogs.com/exmyth/p/11394004.html
* */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Table {

    String names();
    int say();
    byte sayByte();
    char sayChar();
    double sayDouble();
    short sayShort();
    Class sayClass();
    Son saySon();

    int age=5;
    char c = 'd';
    short sh = 12;
    double d = 3.14;

}
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface  Son {
    String hello();
}

public class Table1 {

    public static final Son son = new Son(){
        @Override
        public String hello() {
            return null;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }
//
//        @Override
//        public String names() {
//            return "sss";
//        }
    };

    @Table(names = "asd",say=250,sayByte = 127, sayChar = 'v',sayDouble = 3.14,sayShort = 666,sayClass = Table1.class, saySon=@Son( hello = "132"))
    public void getinfo(){
        System.out.println("info的方法");
        //  System.out.println(Table.isAnn);

    }



    public void mm() throws NoSuchMethodException, SecurityException {
        Table1 ttTable1 = new Table1();
        Annotation[] annotations = ttTable1.getClass().getMethod("getinfo").getAnnotations();
        for (Annotation tag : annotations) {
            if (tag instanceof Table) {
                System.out.println("Table is " + tag);
                System.out.println("tag.getname==" + ((Table) tag).names());
                System.out.println("tag.getsay==" + ((Table) tag).say());
                System.out.println("tag.sayByte==" + ((Table) tag).sayByte());
                System.out.println("tag.sayChar==" + ((Table) tag).sayChar());
                System.out.println("tag.sayDouble==" + ((Table) tag).sayDouble());
                System.out.println("tag.sayShort==" + ((Table) tag).sayShort());
                System.out.println("tag.sayClass==" + ((Table) tag).sayClass());
                System.out.println("tag.saySon==" + ((Table) tag).saySon());

                System.out.println(((Table) tag).age);
                System.out.println(((Table) tag).c);
                System.out.println(((Table) tag).sh);
                System.out.println(((Table) tag).d);

            }
        }
    }
    public static void main(String[] args){
        Table1 table1 = new Table1();
        try {
            table1.mm();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
//        原文链接：https://blog.csdn.net/jgnewbie/article/details/78401691