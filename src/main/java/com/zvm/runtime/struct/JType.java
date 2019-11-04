package com.zvm.runtime.struct;

public class JType {

}
class Void extends JType{

}
class IntType extends JType{
    int value;
    public IntType(int value){
        this.value = value;
    }
}
class FloatType extends JType{
    float value;
    public FloatType(int value){
        this.value = value;
    }
}


class DoubleType extends JType{
    double value;
    public DoubleType(int value){
        this.value = value;
    }
}
class LongType extends JType{
    long value;
    public LongType(int value){
        this.value = value;
    }
}