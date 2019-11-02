package com.zvm.draft;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

public class MainTest {
    public static void main(String[] args){
        Gson gson = new Gson();
        System.out.println(gson.toJson(new Opcode1()));
    }

    /**
     * fastjson转对象为json丢失所有属性
     */
    public void fastJsonTest(){
        Opcode2 opcode2 = new Opcode2();
        System.out.println(opcode2.dup_x1);
        System.out.println(opcode2.toString());
        System.out.println(JSON.toJSONString(opcode2));
        Gson gson = new Gson();
        System.out.println(gson.toJson(opcode2));
    }
}
class Opcode2{
    int dup_x1 =1110;
}