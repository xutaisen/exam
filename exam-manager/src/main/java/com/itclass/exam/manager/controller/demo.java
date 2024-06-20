package com.itclass.exam.manager.controller;


import com.itclass.exam.model.dto.exam.AssginBankDto;


import com.itclass.exam.model.vo.exam.RecordVo;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 徐泰森
 * @create: 2024-03-19 16:00
 **/
public class demo {
    public static void main(String[] args) {
//        //连接本地的 Redis 服务
//        Jedis jedis = new Jedis("localhost",6379);
//        //取值
//        jedis.set("myKey","abc");
//        System.out.println("redis取cmd命令建立的值name:  " + jedis.get("myKey"));

//        Integer a = 100;
//        Integer b = 100;
//        Integer c = 128;
//        Integer d = 128;
//        System.out.println(a==b);
//        System.out.println(c==d);


        List<String> stringList = new ArrayList<>();
        stringList.add("我");
        stringList.add("是");
        stringList.add("大");
        stringList.add("帅");
        stringList.add("哥");

        String[] s = new String[stringList.size()];

        for (int i = 0; i < stringList.size(); i++) {
            s[i] = stringList.get(i);
        }

        for (int i = 0; i < stringList.size(); i++) {
            System.out.println(s[i]);
        }

        String[] strings = new String[]{"我", "是", "大", "帅", "哥"};

        List<RecordVo> recordVoList = new ArrayList<>();
        RecordVo recordVo1 = new RecordVo();
        RecordVo recordVo2 = new RecordVo();
        RecordVo recordVo3 = new RecordVo();
        RecordVo recordVo4 = new RecordVo();
        RecordVo recordVo5 = new RecordVo();
        recordVoList.add(recordVo1);
        recordVoList.add(recordVo2);
        recordVoList.add(recordVo3);
        recordVoList.add(recordVo4);
        recordVoList.add(recordVo5);

        int index = 0;

        for (RecordVo recordVo : recordVoList) {
            recordVo.setQuContent(strings[index]);
            index++;
        }


        System.out.println(recordVoList);


    }

}