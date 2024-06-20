package com.itclass.exam.manager.service;

import com.itclass.exam.model.vo.system.ValidateCodeVo;

public interface ValidateCodeService {

    //生成图片验证码
    ValidateCodeVo generateValidateCode();
}
