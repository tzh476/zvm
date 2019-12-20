package com.zvm.memory;

import com.zvm.runtime.JavaClass;
import com.zvm.runtime.struct.*;

public class ArrayFields {
    public JType[] primitiveTypes;
    public int arraySize;

    public JType[] getPrimitiveTypes() {
        return primitiveTypes;
    }

    public char[] trans2CharArr(){
        if(primitiveTypes instanceof JChar[]){
            JChar[] jChars = (JChar[])primitiveTypes;
            int len = jChars.length;
            char[] chars = new char[len];
            for(int i = 0; i < len; i++ ){
                chars[i] = jChars[i].value;
            }
            return chars;
        }
        return null;
    }

    public ArrayFields(byte[] bytes){
        primitiveTypes = newJByte(bytes);
        arraySize = bytes.length;
    }

    public ArrayFields(char[] chars){
        primitiveTypes = newJChar(chars);
        arraySize = chars.length;
    }
    public ArrayFields(short[] shorts){
        primitiveTypes = newJShort(shorts);
        arraySize = shorts.length;
    }

    public ArrayFields(int[] ints){
        primitiveTypes = newJInt(ints);
        arraySize = ints.length;

    }
    public ArrayFields(long[] longs){
        primitiveTypes = newJLong(longs);
        arraySize = longs.length;
    }

    public ArrayFields(float[] floats){
        primitiveTypes = newJFloat(floats);
        arraySize = floats.length;
    }

    public ArrayFields(double[] doubles){
        primitiveTypes = newJDouble(doubles);
        arraySize = doubles.length;
    }

    public ArrayFields(JObject[] objects, JavaClass baseClass){
        primitiveTypes = newJObject(objects, baseClass);
        arraySize = objects.length;
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

    JObject[] newJObject(JObject[] objects, JavaClass baseClass) {
        int len = objects.length;
        JObject[] jObjects = objects;
        for (int i = 0; i < len; i++) {
            jObjects[i] = new JObject();
            jObjects[i].javaClass = baseClass;
        }
        return jObjects;
    }

    public float getFloat(int index) {
        return ((JFloat) primitiveTypes[index]).value;
    }
    public double getDouble(int index) {
        return ((JDouble) primitiveTypes[index]).value;
    }
    public char getChar(int index) {
        return ((JChar) primitiveTypes[index]).value;
    }
    public byte getByte(int index) {
        return ((JByte) primitiveTypes[index]).value;
    }
    public short getShort(int index) {
        return ((JShort) primitiveTypes[index]).value;
    }
    public int getInt(int index) {
        return ((JInt) primitiveTypes[index]).value;
    }
    public long getLong(int index) {
        return ((JLong) primitiveTypes[index]).value;
    }
    public JObject getJObject(int index) {
        return (JObject) primitiveTypes[index];
    }

    public void putFloat(int index, float value) {
        ((JFloat) primitiveTypes[index]).value = value;
    }
    public void putDouble(int index, double value) {
        ((JDouble) primitiveTypes[index]).value = value;
    }
    public void putChar(int index, char value) {
        ((JChar) primitiveTypes[index]).value = value;
    }
    public void putShort(int index, short value) {
        ((JShort) primitiveTypes[index]).value = value;
    }
    public void putInt(int index, int value) {
        ((JInt) primitiveTypes[index]).value = value;
    }
    public void putLong(int index, long value) {
        ((JLong) primitiveTypes[index]).value = value;
    }
    public void putJOject(int index, JObject jObject){
        primitiveTypes[index] = jObject;
    }

    public void putCharArr(char[] chars) {
        JChar[] jChars = (JChar[]) primitiveTypes;
        int len = primitiveTypes.length;
        for (int i = 0; i < len; i++ ){
            jChars[i].value = chars[i];
        }
    }
}
