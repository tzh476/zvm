package com.zvm;

import com.zvm.basestruct.u1;

public class CodeUtils {
    public u1[] code;
    private int pc;

    public CodeUtils(u1[] code, int pc){
        this.code = code;
        this.pc = pc;
    }

    public byte consumeU1(){
        byte res = code[++pc].u1[0];
        return res;
    }

    public short consumeU2(){
        byte res0 = consumeU1();
        byte res1 = consumeU1();
        short res = (short) ((res0 << 8) | res1);
        return res;
    }

    public short readU2(){
        byte res0 = code[pc + 1].u1[0];
        byte res1 = code[pc + 2].u1[0];
        short res = (short) ((res0 << 8) | res1);
        return res;
    }

    public void pcAdd(int offset){
        pc += offset;
    }

    public int getPc(){
        return pc;
    }

}
