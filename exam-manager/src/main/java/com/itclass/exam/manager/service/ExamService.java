package com.itclass.exam.manager.service;

import com.github.pagehelper.PageInfo;
import com.itclass.exam.model.dto.exam.*;
import com.itclass.exam.model.entity.exam1.*;
import com.itclass.exam.model.entity.exam1.Record;
import com.itclass.exam.model.entity.system.SysUser;
import com.itclass.exam.model.vo.exam.PaperQuVo;
import com.itclass.exam.model.vo.exam.QuestionBankVo;
import com.itclass.exam.model.vo.exam.RecordVo;

import java.util.List;
import java.util.Map;

public interface ExamService {

    PageInfo<Exam> findByPage(ExamDto examDto, Integer pageNum, Integer pageSize);

    //    考试成绩记录
    PageInfo<Record> findByPageGrade(GradeDto gradeDto, Integer pageNum, Integer pageSize);

    //查询题库
    PageInfo<QuestionBankVo> questionBankFind(QuestionBankDto questionBankDto, Integer pageNum, Integer pageSize);

    //添加题库
    void insertBank(QuestionBank questionBank);

    //删除题库
    void deleteByName(String bankName);

    //查询所有题库
    List<QuestionBank> getAllBank();

    //==================================试题=====================
    PageInfo<Question> findByPageQusetion(QuestionDto questionDto, Integer pageNum, Integer pageSiz);

    Map<String, Object> findAllBanks(Integer questionId);

    void doAssignBank(AssginBankDto assginBankDto);

    void deleteQuestionById(Integer questionId);

    PageInfo<Exam> findByPageManage(ExamTeacherDto examTeacherDto, Integer pageNum, Integer pageSize);

    void deleteExamByName(String examName);

    List<Question> questionNoPage(QuestionDto questionDto);

    void addExamName(Exam exam);


    void addExamQuestion(InsertExamDto insertExamDto);

    void insertTitle(InsertQuestionDto insertQuestionDto);


    void insertTitleAnswer(List<AnswerDto> answerDto);

    Map<String, Object> paperDetail(Integer id);

    List<Question> paperDetail2(Integer id);

    Integer getTime(Integer id);

    PaperQuVo quDetail(Integer paperId, Integer quId);

    void fillAnswer(PaperAnswerDto paperAnswerDto);


    void goHome(GoHomeDto goHomeDto);

    void addCard(String examName , Integer quId);

    List<RecordVo> recordData(Integer recordId,Integer paperId);

    SysUser getMySelf();

    void updatePassword(upPassword upPassword);
}
