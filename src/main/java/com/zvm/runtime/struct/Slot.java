package com.zvm.runtime.struct;

/**
 * value用于填充基本数据类型,double、long需要两个slot填充，对应jType为空
 * jType用于表示对象
 */
public class Slot {
    public int value;
    public JType jType;
}
