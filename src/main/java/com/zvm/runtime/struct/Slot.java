package com.zvm.runtime.struct;

/**
 *
 */
public class Slot {
    /**
     * value用于填充基本数据类型,double、long需要两个slot填充，对应jType为空
     */
    public int value;
    /**
     * jType用于表示对象
     */
    public JType jType;
}
