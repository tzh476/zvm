package com.zvm.runtime;

import com.zvm.runtime.struct.*;

public class ArrayFields {
    public JType[] primitiveType;
    public int arraySize;



    public ArrayFields(byte[] bytes){
        primitiveType = newJByte(bytes);
        arraySize = bytes.length;
    }

    public ArrayFields(char[] chars){
        primitiveType = newJChar(chars);
        arraySize = chars.length;
    }
    public ArrayFields(short[] shorts){
        primitiveType = newJShort(shorts);
        arraySize = shorts.length;
    }

    public ArrayFields(int[] ints){
        primitiveType = newJInt(ints);
        arraySize = ints.length;

    }
    public ArrayFields(long[] longs){
        primitiveType = newJLong(longs);
        arraySize = longs.length;
    }

    public ArrayFields(float[] floats){
        primitiveType = newJFloat(floats);
        arraySize = floats.length;
    }

    public ArrayFields(double[] doubles){
        primitiveType = newJDouble(doubles);
        arraySize = doubles.length;
    }

    JByte[] newJByte(byte[] bytes){
        int len = bytes.length;
        JByte[] jBytes = new JByte[len];
        for(int i = 0; i < len; i ++){
            jBytes[i] = new JByte(bytes[i]);
        }
        return jBytes;
    }


    JChar[] newJChar(char[] chars) {
        int len = chars.length;
        JChar[] jChars = new JChar[len];
        for (int i = 0; i < len; i++) {
            jChars[i] = new JChar(chars[i]);
        }
        return jChars;
    }

    JDouble[] newJDouble(double[] doubles) {
        int len = doubles.length;
        JDouble[] jDoubles = new JDouble[len];
        for (int i = 0; i < len; i++) {
            jDoubles[i] = new JDouble(doubles[i]);
        }
        return jDoubles;
    }
    JFloat[] newJFloat(float[] floats) {
        int len = floats.length;
        JFloat[] jFloats = new JFloat[len];
        for (int i = 0; i < len; i++) {
            jFloats[i] = new JFloat(floats[i]);
        }
        return jFloats;
    }
    JInt[] newJInt(int[] ints) {
        int len = ints.length;
        JInt[] jInts = new JInt[len];
        for (int i = 0; i < len; i++) {
            jInts[i] = new JInt(ints[i]);
        }
        return jInts;
    }
    JShort[] newJShort(short[] shorts) {
        int len = shorts.length;
        JShort[] jShorts = new JShort[len];
        for (int i = 0; i < len; i++) {
            jShorts[i] = new JShort(shorts[i]);
        }
        return jShorts;
    }
    JLong[] newJLong(long[] longs) {
        int len = longs.length;
        JLong[] jLongs = new JLong[len];
        for (int i = 0; i < len; i++) {
            jLongs[i] = new JLong(longs[i]);
        }
        return jLongs;
    }

    JObject[] newJObject(JObject[] objects) {
        int len = objects.length;
        JObject[] jObjects = objects;
//        for (int i = 0; i < len; i++) {
//            jObjects[i] = new JObject(objects[i]);
//        }
        return jObjects;
    }

    public float getFloat(int index) {
        return ((JFloat)primitiveType[index]).value;
    }
    public double getDouble(int index) {
        return ((JDouble)primitiveType[index]).value;
    }
    public byte getByte(int index) {
        return ((JByte)primitiveType[index]).value;
    }
    public short getShort(int index) {
        return ((JShort)primitiveType[index]).value;
    }
    public int getInt(int index) {
        return ((JInt)primitiveType[index]).value;
    }
    public long getLong(int index) {
        return ((JLong)primitiveType[index]).value;
    }
    public JObject getJObject(int index) {
        return (JObject)primitiveType[index];
    }

    public void putFloat(int index, float value) {
        ((JFloat)primitiveType[index]).value = value;
    }
    public void putDouble(int index, double value) {
        ((JDouble)primitiveType[index]).value = value;
    }
    public void putChar(int index, char value) {
        ((JChar)primitiveType[index]).value = value;
    }
    public void putShort(int index, short value) {
        ((JShort)primitiveType[index]).value = value;
    }
    public void putInt(int index, int value) {
        ((JLong)primitiveType[index]).value = value;
    }
    public void putLong(int index, long value) {
        ((JLong)primitiveType[index]).value = value;
    }
    public void putJOject(int index, JObject jObject){
        primitiveType[index] = jObject;
    }
    
}
