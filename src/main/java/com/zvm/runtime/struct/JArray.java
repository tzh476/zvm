package com.zvm.runtime.struct;

/**
 * 暂未使用
 */
public class JArray extends JType {

    /**
     * 数组的长度*/
    public int length = 0;

    /**
     * 数组在heap的偏移量
     */
    public Integer offset;
}
