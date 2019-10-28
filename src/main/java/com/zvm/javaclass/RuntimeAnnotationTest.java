package com.zvm.javaclass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*https://blog.csdn.net/zhaominpro/article/details/82558600*/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation1 {
    String color() default "red";
}


public class RuntimeAnnotationTest {

    @MyAnnotation1(color = "blue")
    private String text;
}