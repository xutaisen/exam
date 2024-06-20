package com.itclass.exam.manager.controller;

import com.itclass.exam.manager.service.FileUploadService;
import com.itclass.exam.model.vo.common.Result;
import com.itclass.exam.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**上传文件
 * @author: 徐泰森
 * @create: 2024-03-25 12:53
 **/
@RestController
@RequestMapping("/admin/system")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService ;

    @PostMapping(value = "/fileUpload")
    public Result fileUpload(MultipartFile file){
        //获取上传的文件---MultipartFile file

        //调用service方法返回minion路径
        String url = fileUploadService.fileUpload(file);

        return Result.build(url, ResultCodeEnum.SUCCESS);
    }

}