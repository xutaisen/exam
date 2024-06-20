package com.itclass.exam.manager.mapper;

import com.itclass.exam.model.dto.exam.*;
import com.itclass.exam.model.entity.exam1.*;
import com.itclass.exam.model.entity.exam1.Record;
import com.itclass.exam.model.entity.system.SysUser;
import com.itclass.exam.model.vo.exam.AnswerVo;
import com.itclass.exam.model.vo.exam.QuestionBankVo;
import com.itclass.exam.model.vo.exam.RecordAnVo;
import com.itclass.exam.model.vo.exam.RecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ExamMapper {

    List<Exam> findByPage(ExamDto examDto);

    List<Record> findByPageGrade(GradeDto gradeDto);


    //查询题库
    List<QuestionBank> questionBankFind(QuestionBankDto questionBankDto);

    //单、多、判断、简答题数量
    Integer findSingleChoice(String bankName);
    Integer findmultipleChoice(String bankName);
    Integer findjudge(String bankName);
    Integer findshortAnswer(String bankName);


    //添加题库
    void insertBank(QuestionBank questionBank);

    //重新赋值total
    long selectTotal(QuestionBankDto questionBankDto);


    //删除题库
    void deleteByName(String bankName);

    //查询所有题库
    List<QuestionBank> getAllBank();

    //查询题目
    List<Question> findByPageQusetion(QuestionDto questionDto);

    String findBankIng(Integer questionId);

    void deleteById(Long id);


    void doAssign(Long id, String s);


    void deleteQuestionById(Integer questionId);

    List<Exam> findByPageManage(ExamTeacherDto examTeacherDto);

    void deleteExamByName(String examName);


    //根据id查询题库名字
    String byNameForId(int i);

    void doAssignBankName(Long id, String join1);

    //无条件查询试题-->
    List<Question> questionNoPage(QuestionDto questionDto);

    //把题库信息写入题目表
    void addExamName(Exam exam);

    //根据考试名称查询对应考试id
    Integer getExamId(String examName);

    //给考试添加题目
    void addExamQuestion(@Param("insertExamDto") InsertExamDto insertExamDto, @Param("id") Integer id);

    //添加试题
    void insertTitle(@Param("insertQuestionDto") InsertQuestionDto insertQuestionDto , @Param("bankId") String bankId);

    //根据题目内容查询题目id
    Integer getQuId(String quContent);

    void insertAnswer(@Param("answerVo") AnswerVo answerVo);

    //根据考试id查询题目集合
    List<Question> finRadioList(Integer id);
    List<Question> findMultiList(Integer id);
    List<Question> findJudgeList(Integer id);
    List<Question> findShortAnswer(Integer id);

    List<Question> paperDetail2(Integer id);

    Integer getTime(Integer id);

    //根据题目id查询题目内容
    String findPaperConcentById(Integer quId);

    //根据题目id查询题目类型
    Integer findPaperQuTypeById(Integer quId);

    //根据题目id查询答案
    List<AnswerVo> findAnswer(Integer quId);

    ////设置答题卡答题状态
    Question selectQu(Integer id, Long id1);

    //查询答案是否选中
    Boolean ifChecked(Integer id, Integer paperId, Integer quId);

    //修改答案是否选中
    void setAnswerCheckedToTrue(Integer paperId, Integer quId, Integer id);
    void setAnswerCheckedToFalse(Integer paperId, Integer quId, Integer id);


    //更改题目的答题状态---已答
    void setQuIsAnswerd(Integer paperId, Integer quId);

    //更改答题卡是否正确, 有一个对不上就是错的
    void setQuIsRight(Integer paperId, Integer quId);


    //获取题目分值
    Integer getPaQuScore(Integer paperId, Integer quId);

    //给回答正确的题目赋分
    void setPaQuScore(Integer paperId, Integer quId, Integer score);


    String setExamName(Integer examId);


    Exam getExam(Integer paperId);

    //往成绩表中添加考试记录
    void insertRecord(@Param("record") Record record);

    //查询考试得分
    Integer getActualScore(Integer paperId);

    //添加答题卡表字段
    void addPaperAnswers(Integer examId, Integer quId);

    //1.获取答案列表
    List<Integer> getAnswerList(Integer quId);

    //添加题目状态表字段
    void addPaperQuState(Integer quId, Integer examId, Integer i);

    // 2.清空答题卡表 和题目状态表的数据
    void updataPaperAnswers(Integer paperId);
    void updataPaperQuState(Integer paperId);

    //通过考试id查询出题目id集合
    List<Integer> getQuIdListBypaId(Integer paperId);

    //通过时间获取成绩id
    Integer getRecordIdByTime(Date date);

    //通过id获取题目是否回答正确
    Boolean getIsRightById(Integer paperId, Integer item);

    //根据考试id查询出对应答案id集合
    List<Integer> getAnswerListByPaId(Integer paperId);


    String getAnContentById(Integer item);

    Boolean getAnIsChecked(Integer paperId, Integer item);
    Boolean getAnIsTrue(Integer item);

    void insertRecordQu(@Param("recordQuState") RecordQuState recordQuState);
    void insertRecordAn(@Param("recordAnState") RecordAnState recordAnState);

    List<RecordQuState> getRecordQuState(Integer recordId);

    List<Integer> getAnIdByQuId(Integer id);

    RecordAnVo getRecordAnVo(Integer recordId, Integer anId);

    SysUser getMySelf(Long userId);

    //更新密码
    void updatePassword(Long userId, String md5_password);
}
