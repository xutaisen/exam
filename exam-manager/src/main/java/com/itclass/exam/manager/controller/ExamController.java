package com.itclass.exam.manager.controller;

import com.github.pagehelper.PageInfo;
import com.itclass.exam.manager.service.ExamService;
import com.itclass.exam.model.dto.exam.*;
import com.itclass.exam.model.dto.system.AssginMenuDto;
import com.itclass.exam.model.dto.system.SysUserDto;
import com.itclass.exam.model.entity.exam1.*;
import com.itclass.exam.model.entity.exam1.Record;
import com.itclass.exam.model.entity.system.SysUser;
import com.itclass.exam.model.vo.common.Result;
import com.itclass.exam.model.vo.common.ResultCodeEnum;
import com.itclass.exam.model.vo.exam.PaperQuVo;
import com.itclass.exam.model.vo.exam.QuestionBankVo;
import com.itclass.exam.model.vo.exam.RecordVo;
import com.itclass.exam.util.AuthContextUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 在线考试的接口
 *
 * @author: 徐泰森
 * @create: 2024-04-07 17:13
 **/
@RestController
@RequestMapping(value = "/public")
public class ExamController {

    @Autowired
    private ExamService examService;

    /**
     * 考试条件查询分页接口
     */
    @GetMapping("/getExamInfo/{pageNum}/{pageSize}")
    public Result findByPageExam(ExamDto examDto,
                                 @PathVariable(value = "pageNum") Integer pageNum,
                                 @PathVariable(value = "pageSize") Integer pageSize) {
        PageInfo<Exam> pageInfo = examService.findByPage(examDto, pageNum, pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }


    /**
     * 成绩条件查询分页接口
     */
    @GetMapping("/getGradeInfo/{pageNum}/{pageSize}")
    public Result findByPageGrade(GradeDto gradeDto,
                                  @PathVariable(value = "pageNum") Integer pageNum,
                                  @PathVariable(value = "pageSize") Integer pageSize) {
        String userNowName = AuthContextUtil.get().getUserName();
        gradeDto.setUserName(userNowName);
        PageInfo<Record> pageInfo = examService.findByPageGrade(gradeDto, pageNum, pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }


//====================================题库接口==============================================

    /**
     * 条件查询题库
     */
    @GetMapping("/questionBankFind/{pageNum}/{pageSize}")
    public Result questionBankFind(QuestionBankDto questionBankDto,
                                   @PathVariable(value = "pageNum") Integer pageNum,
                                   @PathVariable(value = "pageSize") Integer pageSize) {

        PageInfo<QuestionBankVo> pageInfo = examService.questionBankFind(questionBankDto, pageNum, pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }


    /**
     * 添加题库
     */
    @PostMapping(value = "/insertBank")
    public Result insertBank(@RequestBody QuestionBank questionBank) {

        examService.insertBank(questionBank);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 删除题库（逻辑删除
     */
    @DeleteMapping("/deleteByName/{bankName}")
    public Result deleteByName(@PathVariable(value = "bankName") String bankName) {
        examService.deleteByName(bankName);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    //=============================试题接口===========================

    /**
     * 分页查询试题
     */
    @GetMapping("/findByPageQusetion/{pageNum}/{pageSize}")
    public Result findByPageQusetion(QuestionDto questionDto,
                                     @PathVariable(value = "pageNum") Integer pageNum,
                                     @PathVariable(value = "pageSize") Integer pageSiz) {
        System.out.println(questionDto);
        PageInfo<Question> pageInfo = examService.findByPageQusetion(questionDto, pageNum, pageSiz);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 查询所有题库(用于下拉框
     */
    @GetMapping("/getAllBank")
    public Result getAllBank() {
        List<QuestionBank> questionBankList = examService.getAllBank();
        return Result.build(questionBankList, ResultCodeEnum.SUCCESS);
    }

    /**
     * 查询题库and已分配的题库
     */
    @GetMapping("/findAllBanks/{questionId}")
    public Result findAllBanks(@PathVariable(value = "questionId") Integer questionId) {
        Map<String, Object> map = examService.findAllBanks(questionId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }


    /**
     * 分配题库
     */
    @PostMapping("/doAssignBank")
    public Result doAssignBank(@RequestBody AssginBankDto assginBankDto) {
        examService.doAssignBank(assginBankDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 删除题库
     */
    @DeleteMapping("/deleteQuestionById/{questionId}")
    public Result deleteQuestionById(@PathVariable(value = "questionId") Integer questionId) {
        examService.deleteQuestionById(questionId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 删除考试
     */
    @DeleteMapping("/deleteExamByName/{examName}")
    public Result deleteExamByName(@PathVariable(value = "examName") String examName) {
        examService.deleteExamByName(examName);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    /**
     * 考试管理接口（教师）
     */
    @GetMapping("/findByPageManage/{pageNum}/{pageSize}")
    public Result findByPageManage(ExamTeacherDto examTeacherDto,
                                   @PathVariable(value = "pageNum") Integer pageNum,
                                   @PathVariable(value = "pageSize") Integer pageSize) {
        PageInfo<Exam> pageInfo = examService.findByPageManage(examTeacherDto, pageNum, pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }


    /**
     * 无分页查试题
     */
    @GetMapping("/questionNoPage")
    public Result questionNoPage(QuestionDto questionDto) {
        List<Question> questionList = examService.questionNoPage(questionDto);
        return Result.build(questionList, ResultCodeEnum.SUCCESS);
    }


    /**
     * 添加考试
     */
    @PostMapping(value = "/addExamName")
    public Result addExamName(@RequestBody Exam exam) {

        examService.addExamName(exam);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    /**
     * 添加考试的题目
     */
    @PostMapping(value = "/addExamQuestion")
    public Result addExamQuestion(@RequestBody InsertExamDto insertExamDto) {

        examService.addExamQuestion(insertExamDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    /**
     * 添加题目
     */
    @PostMapping(value = "/insertTitle")
    public Result insertTitle(@RequestBody InsertQuestionDto insertQuestionDto) {
        System.out.println(insertQuestionDto);
        examService.insertTitle(insertQuestionDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    /**
     * 添加题目的答案
     */
    @PostMapping(value = "/insertTitleAnswer")
    public Result insertTitleAnswer(@RequestBody List<AnswerDto> answerDto) {

        System.out.println(answerDto);
        examService.insertTitleAnswer(answerDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    /**
     * 渲染考试数据 ， 返回题目集合,(填充答题卡）
     */
    @GetMapping("/needQuestion/{id}")
    public Result paperDetail(@PathVariable(value = "id") Integer id) {
        Map<String, Object> map = examService.paperDetail(id);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }


//    /**
//     * 渲染考试数据 ， 返回题目集合222
//     */
//    @GetMapping("/getExamQuestionById/{id}")
//    public Result paperDetail2(@PathVariable(value = "id")Integer id){
//        List<Question> list = examService.paperDetail2(id);
//        return Result.build(list , ResultCodeEnum.SUCCESS);
//    }

    /**
     * 获取考试时间
     */
    @GetMapping("/getTime/{id}")
    public Result getTime(@PathVariable(value = "id") Integer id) {
        Integer time = examService.getTime(id);
        return Result.build(time, ResultCodeEnum.SUCCESS);
    }


    /**
     * 获取题目答案，（填充当前题目内容 以及 答案信息）
     */
    @GetMapping("/quDetail")
    public Result quDetail(PaperQuQueryDTO paperQuQueryDTO) {
        PaperQuVo paperQuVo = examService.quDetail(paperQuQueryDTO.getPaperId(), paperQuQueryDTO.getQuId());
        return Result.build(paperQuVo, ResultCodeEnum.SUCCESS);
    }


    /**
     * 保存答案
     */
    @PostMapping("/fillAnswer")
    public Result fillAnswer(@RequestBody PaperAnswerDto paperAnswerDto) {
        examService.fillAnswer(paperAnswerDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    /**
     * 交卷
     */
    @GetMapping("/goHome")
    public Result goHome(GoHomeDto goHomeDto) {
        examService.goHome(goHomeDto);

        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    /**
     * 生成考试时要添加对应的 答题卡表 和 题目状态表
     */
    @GetMapping("/addCard/{examName}/{quId}")
    public Result addCard(@PathVariable(value = "examName") String examName, @PathVariable(value = "quId") Integer quId) {
        examService.addCard(examName, quId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    /**
     * 成绩详情
     */
    @GetMapping("/recordData/{recordId}/{paperId}")
    public Result recordData(@PathVariable(value = "recordId") Integer recordId, @PathVariable(value = "paperId") Integer paperId) {

        List<RecordVo> recordVos = examService.recordData(recordId, paperId);
        return Result.build(recordVos, ResultCodeEnum.SUCCESS);
    }


    /**
     * 个人主页
     */
    @GetMapping("/mySelf")
    public Result mySelf() {
        SysUser sysUser = examService.getMySelf();
        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/updatePassword")
    public Result updatePassword(upPassword upPassword){
        examService.updatePassword(upPassword);
        return  Result.build(null , ResultCodeEnum.SUCCESS);
    }


}



