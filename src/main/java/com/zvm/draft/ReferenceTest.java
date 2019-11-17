package com.zvm.draft;

/**
 * 对象引用测试
 */
public class ReferenceTest {
    public int value = 100;
    Student student = new Student();
    public static void main(String[] args){
        test1();
    }

    /**
     * 正常打印
     */
    static void test0(){
        ReferenceTest r0 = new ReferenceTest();
        ReferenceTest r1 = r0;
        r1 = null;
        System.out.println(r0.value);
    }

    /**
     * r0.next.value不正常打印
     */
    static void test1(){
        ReferenceTest r0 = new ReferenceTest();
        ReferenceTest r1 = r0;
        r1.student = null;
        System.out.println(r0.value);
        System.out.println(r0.student.value);
    }
}
class Student{
    public int value = 100;
}
