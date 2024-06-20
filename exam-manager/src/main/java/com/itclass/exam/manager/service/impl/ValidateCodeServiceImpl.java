package com.itclass.exam.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.itclass.exam.manager.service.ValidateCodeService;
import com.itclass.exam.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: 徐泰森
 * @create: 2024-03-21 17:22
 **/
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //生成图片验证码
    @Override
    public ValidateCodeVo generateValidateCode() {

        //1.调用工具类生成图片验证码 hutool
        //四个参数分别是宽 高 验证码位数 干扰线数量
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 2);
        String codeValue = circleCaptcha.getCode();//验证码的值
        String imageBase64 = circleCaptcha.getImageBase64();//用base64编码把验证码生成的图片

        //2.把验证码存储到Redis里，设置Redis的key value（验证码  设置过期时间5分钟
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:validate" + key,
                codeValue,
                5,
                TimeUnit.MINUTES);
        System.out.println("生成图片验证码");

        //3.返回ValidateCodeVo对象
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);
        System.out.println("返回图片验证码");
        return validateCodeVo;

    }
}