package com.itheima.utils;

import java.util.UUID;

public class UUIDUtils {
    //UUID，生成主键
    public static  String  getUUID(){
        //UUID.randomUUID().toString() ，会生成一个32长的字符串，带有-字符，
        String uuid = UUID.randomUUID().toString();
        //把-字符替换为没有，再返回
        return uuid.replace("-","");
    }
}
