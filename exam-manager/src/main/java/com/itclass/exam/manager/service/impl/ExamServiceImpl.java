package com.itclass.exam.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itclass.exam.manager.mapper.ExamMapper;
import com.itclass.exam.manager.service.ExamService;
import com.itclass.exam.model.dto.exam.*;
import com.itclass.exam.model.entity.exam1.*;
import com.itclass.exam.model.entity.exam1.Record;
import com.itclass.exam.model.entity.system.SysRole;
import com.itclass.exam.model.entity.system.SysUser;
import com.itclass.exam.model.vo.exam.*;
import com.itclass.exam.util.AuthContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: 徐泰森
 * @create: 2024-04-07 23:32
 **/
@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamMapper examMapper;


    /**
     * 查找考试
     */
    @Override
    public PageInfo<Exam> findByPage(ExamDto examDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Exam> list = examMapper.findByPage(examDto);
        PageInfo<Exam> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    /**
     * 删除题库
     *
     * @param bankName
     */
    @Override
    public void deleteByName(String bankName) {
        examMapper.deleteByName(bankName);
    }

    /**
     * 查询所有题库
     *
     * @return
     */
    @Override
    public List<QuestionBank> getAllBank() {
        List<QuestionBank> questionBankList = examMapper.getAllBank();
        return questionBankList;
    }


    /**
     * 查询题库
     */
    @Override
    public PageInfo<QuestionBankVo> questionBankFind(QuestionBankDto questionBankDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<QuestionBankVo> list = new ArrayList<>();//创建vo集合

        List<QuestionBank> qbs = examMapper.questionBankFind(questionBankDto);//得到题库的集合
        for (QuestionBank questionBank : qbs) { //遍历题库

            QuestionBankVo qBV = new QuestionBankVo();//创建vo对象
            qBV.setQuestionBank(questionBank);
            qBV.setBankName(questionBank.getBankName());

            //设置单选、多选...题目数量
            Integer singleChoice = examMapper.findSingleChoice(qBV.getBankName());
            qBV.setSingleChoice(singleChoice);

            Integer multipleChoice = examMapper.findmultipleChoice(qBV.getBankName());
            qBV.setMultipleChoice(multipleChoice);

            Integer judge = examMapper.findjudge(qBV.getBankName());
            qBV.setJudge(judge);

            Integer shortAnswer = examMapper.findshortAnswer(qBV.getBankName());
            qBV.setShortAnswer(shortAnswer);

            list.add(qBV);
        }

        PageInfo<QuestionBankVo> pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(examMapper.selectTotal(questionBankDto));
        return pageInfo;
    }

    /**
     * 添加题库
     *
     * @param questionBank
     */
    @Override
    public void insertBank(QuestionBank questionBank) {
        examMapper.insertBank(questionBank);
    }

    /**
     * 获取考试题目的答案
     */
    @Override
    public PaperQuVo quDetail(Integer paperId, Integer quId) {
        PaperQuVo paperQuVo = new PaperQuVo();

        paperQuVo.setContent(examMapper.findPaperConcentById(quId));
        paperQuVo.setQuType(examMapper.findPaperQuTypeById(quId));


        List<AnswerVo> answerVos = examMapper.findAnswer(quId);

        //修改是否选中
        for (AnswerVo item : answerVos) {
            item.setChecked(examMapper.ifChecked(item.getId(), paperId, quId));
        }

        paperQuVo.setAnswerList(answerVos);
        return paperQuVo;
    }


    /**
     * 保存已选择的答案
     *
     * @param paperAnswerDto
     */
    @Override
    public void fillAnswer(PaperAnswerDto paperAnswerDto) {

        //未作答
        if (CollectionUtils.isEmpty(paperAnswerDto.getAnswers()) && StringUtils.isBlank(paperAnswerDto.getAnswer())) {
            return;
        }

        //查询答案集合
        List<AnswerVo> list = examMapper.findAnswer(paperAnswerDto.getQuId());

        //是否正确
        boolean right = true;

        //更新选中的答案
        for (AnswerVo item : list) {

            if (paperAnswerDto.getAnswers().contains(String.valueOf(item.getId()))) {
                item.setChecked(true);
                examMapper.setAnswerCheckedToTrue(paperAnswerDto.getPaperId(), paperAnswerDto.getQuId(), item.getId());
            } else {
                item.setChecked(false);
                examMapper.setAnswerCheckedToFalse(paperAnswerDto.getPaperId(), paperAnswerDto.getQuId(), item.getId());
            }

            //更改答题卡是否正确, 有一个对不上就是错的
            if (item.getIsRight() == true && item.getIsRight().equals(item.getChecked())) {
                examMapper.setQuIsRight(paperAnswerDto.getPaperId(), paperAnswerDto.getQuId());//答题卡设为正确
                Integer score = examMapper.getPaQuScore(paperAnswerDto.getPaperId(), paperAnswerDto.getQuId());//获取题目分值
                examMapper.setPaQuScore(paperAnswerDto.getPaperId(), paperAnswerDto.getQuId(), score);//给回答正确的题目赋分
            }

        }


        //更改题目的答题状态---已答
        examMapper.setQuIsAnswerd(paperAnswerDto.getPaperId(), paperAnswerDto.getQuId());
    }


    /**
     * 交卷
     */
    @Override
    public void goHome(GoHomeDto goHomeDto) {
//交卷分两步 1.添加考试成绩记录到成绩表中  2.往两张成绩记录表中添加具体成绩数据   3.清空答题卡表 和题目状态表的数据
        Exam exam = examMapper.getExam(goHomeDto.getPaperId());

        //转换时间类型
        String examTime = goHomeDto.getExamTime();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sf.parse(examTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //1.往成绩表中添加考试记录
        Record record = new Record();

        record.setUserId(AuthContextUtil.get().getId().intValue());
        record.setExamId(goHomeDto.getPaperId());
        record.setExamTime(date);
        record.setLogicScore(examMapper.getActualScore(goHomeDto.getPaperId()));//逻辑得分
        record.setPassScore(exam.getPassScore());
        record.setTotalScore(exam.getTotalScore());

        examMapper.insertRecord(record);


        //2.往两张成绩记录表中添加具体成绩数据(成绩题目状态）
        //先查询考试有哪些题目
        List<Integer> integers = examMapper.getQuIdListBypaId(goHomeDto.getPaperId());

        //根据考试时间获取成绩id
        Integer idByTime = examMapper.getRecordIdByTime(date);

        //遍历题目id集合,填入讯息，生成数据库表数据
        for (Integer item : integers) {
            RecordQuState recordQuState = new RecordQuState();

            recordQuState.setPaperId(goHomeDto.getPaperId());
            recordQuState.setRecordId(idByTime);
            recordQuState.setIsRight(examMapper.getIsRightById(goHomeDto.getPaperId(), item));
            recordQuState.setQuId(item);
            recordQuState.setQuContent(examMapper.findPaperConcentById(item));
            examMapper.insertRecordQu(recordQuState);
        }

        //根据考试id查询出对应答案id集合
        List<Integer> anList = examMapper.getAnswerListByPaId(goHomeDto.getPaperId());

        for (Integer item : anList) {
            RecordAnState recordAnState = new RecordAnState();

            recordAnState.setPaperId(goHomeDto.getPaperId());
            recordAnState.setRecordId(idByTime);
            recordAnState.setAnContent(examMapper.getAnContentById(item));
            recordAnState.setAnswerId(item);
            recordAnState.setIsChecked(examMapper.getAnIsChecked(goHomeDto.getPaperId(), item));
            recordAnState.setIsTrue(examMapper.getAnIsTrue(item));

            examMapper.insertRecordAn(recordAnState);
        }


        // 3.清空答题卡表 和题目状态表的数据
        examMapper.updataPaperAnswers(goHomeDto.getPaperId());
        examMapper.updataPaperQuState(goHomeDto.getPaperId());
    }


    /**
     * 更新密码
     * @param upPassword
     */
    @Override
    public void updatePassword(upPassword upPassword) {
        Long userId = AuthContextUtil.get().getId();

        //加密密码
        String md5_password = DigestUtils.md5DigestAsHex(upPassword.getNewPassTwo().getBytes());

        examMapper.updatePassword(userId , md5_password);
    }

    /**
     * 获取个人信息主页
     * @return
     */
    @Override
    public SysUser getMySelf() {
        Long userId = AuthContextUtil.get().getId();
        SysUser sysUser = examMapper.getMySelf(userId);
        return sysUser;
    }


    /**
     * 考试时要添加对应的 答题卡表 和 题目状态表
     */
    @Override
    public void addCard(String examName, Integer quId) {

        //-----添加答题卡表字段-------//
        //1.先获取考试id
        Integer examId = examMapper.getExamId(examName);
        //2.开始添加
        examMapper.addPaperAnswers(examId, quId);


        //-----------添加题目状态表字段---------//
        //1.获取答案列表
        List<Integer> list = examMapper.getAnswerList(quId);
        //2.遍历答案列表，依次填入表字段
        for (Integer i : list) {
            examMapper.addPaperQuState(quId, examId, i);
        }

    }


    /**
     * 获取成绩详情
     */
    @Override
    public List<RecordVo> recordData(Integer recordId, Integer paperId) {
//        List<RecordVo> recordVoList = examMapper.getQuContent(paperId);

//        //1.拿到题目内容列表
//        List<Integer> quInteger = examMapper.findQuestionId(paperId);
//        List<String> quContentList = new ArrayList<>();
//        for (Integer integer : quInteger) {
//            quContentList.add(examMapper.getQuContent(integer));
//        }
//        String[] strings = new String[quContentList.size()];//题目内容列表
//        for (int i = 0; i < quContentList.size(); i++) {
//            strings[i] = quContentList.get(i);
//        }
//
//        //2.把题目内容放到对象的属性里
//        int index = 0;
//        for (RecordVo item : recordVoList) {
//            item.setQuContent(strings[index]);
//            index++;
//        }
//
//
//        //3.设置对象的答题人属性
//        for (RecordVo item : recordVoList) {
//            item.setUserName(AuthContextUtil.get().getUserName());
//        }

        List<RecordVo> recordVoList = new ArrayList<>();

        //获取成绩题目状态集合 、答案状态集合
        List<RecordQuState> recordQuStates = examMapper.getRecordQuState(recordId);
//        List<RecordAnState> recordAnStates = examMapper.getRecordAnState(recordId);

        //遍历集合填入题目信息，
        for (RecordQuState item : recordQuStates) {
            RecordVo recordVo = new RecordVo();
            recordVo.setId(item.getQuId());
            recordVo.setUserName(AuthContextUtil.get().getUserName());
            recordVo.setQuContent(item.getQuContent());
            recordVo.setIsRight(item.getIsRight());
            recordVoList.add(recordVo);
        }

        //遍历集合，存入答案信息
        for (RecordVo item : recordVoList) {

            //根据题目id获取答案id列表
            List<Integer> integer = examMapper.getAnIdByQuId(item.getId());
            List<RecordAnVo> recordAnVoList = new ArrayList<>();

            for (Integer anId : integer) {
                RecordAnVo recordAnVo = examMapper.getRecordAnVo(recordId, anId);
                recordAnVoList.add(recordAnVo);
            }
            item.setAnswerVos(recordAnVoList);

        }

        return recordVoList;
    }

    //考试成绩记录
    @Override
    public PageInfo<Record> findByPageGrade(GradeDto gradeDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Record> list = examMapper.findByPageGrade(gradeDto);

        for (Record item : list) {
            item.setExamName(examMapper.setExamName(item.getExamId()));
        }

        PageInfo<Record> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 查询题目
     */
    @Override
    public PageInfo<Question> findByPageQusetion(QuestionDto questionDto, Integer pageNum, Integer pageSiz) {
        PageHelper.startPage(pageNum, pageSiz);
        List<Question> list = examMapper.findByPageQusetion(questionDto);
        PageInfo<Question> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 查询题库and已分配的题库
     */
    @Override
    public Map<String, Object> findAllBanks(Integer questionId) {
        //1 查询所有题库
        List<QuestionBank> allBankList = examMapper.getAllBank();

        //2 分配过的题库列表 根据uid查询出分配的题库
        String ing = examMapper.findBankIng(questionId);

        List<Integer> bankIng = new ArrayList<>();
        String[] strings = ing.split(",");

        for (String s : strings) {
            int i = Integer.parseInt(s);
            bankIng.add(i);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("allBankList", allBankList);
        map.put("bankIng", bankIng);

        return map;
    }


    /**
     * 无分页查询试题
     */
    @Override
    public List<Question> questionNoPage(QuestionDto questionDto) {
        List<Question> questionList = examMapper.questionNoPage(questionDto);
        return questionList;
    }

    /**
     * 添加考试
     *
     * @param exam
     */
    @Override
    public void addExamName(Exam exam) {
        //判断是否重复
        String examName = exam.getExamName();

        examMapper.addExamName(exam);
    }


    /**
     * 添加题目
     */
    @Override
    public void insertTitle(InsertQuestionDto insertQuestionDto) {
//        Question question = new Question();
        insertQuestionDto.setCreatePerson(AuthContextUtil.get().getUserName());
        List<String> quBankId = insertQuestionDto.getQuBankId();
        String join = StringUtils.join(quBankId, ",");


        examMapper.insertTitle(insertQuestionDto, join);

        //查出题库名
        List<String> nameList = new ArrayList<>();
        for (String string : quBankId) {
            int i = Integer.parseInt(string);
            String name = examMapper.byNameForId(i);
            nameList.add(name);
        }
        String join1 = StringUtils.join(nameList, ",");
        Integer id = examMapper.getQuId(insertQuestionDto.getQuContent());
        examMapper.doAssignBankName(Long.valueOf(id), join1);
    }


    /**
     * 添加题目的的答案
     */
    @Override
    public void insertTitleAnswer(List<AnswerDto> answerDto) {

        for (AnswerDto answerDto1 : answerDto) {
            AnswerVo answerVo = new AnswerVo();
            answerVo.setContent(answerDto1.getContent());
            answerVo.setQuId(examMapper.getQuId(answerDto1.getName()));
            answerVo.setIsRight(Boolean.valueOf(answerDto1.getIsTrue()));
            examMapper.insertAnswer(answerVo);
        }
//        int index = 0;
//        for (AnswerDto answerDto1 : answerDto) {//遍历集合
//
//            //如果是正确答案,添加正确答案索引
//            if (answerDto1.getIsTrue().equals("true")) {
//                if (answer.getTrueOption() == null) {
//                    answer.setTrueOption(String.valueOf(index));
//                } else {
//                    answer.setTrueOption(answer.getTrueOption() + "," + index);
//                }
//            }
//
//            //添加答案内容
//            if (answer.getAllOption() == null) {
//                answer.setAllOption(answerDto1.getContent());
//            } else {
//                answer.setAllOption(answer.getAllOption() + "," + answerDto1.getContent());
//            }
//
//            //设置quId
//            if (index == 0) {
//
//                answer.setQuestionId(examMapper.getQuId(answerDto1.getName()));
//            }
//
//
//            //设置答案解析
//            if (answer.getAnalysis() == null) {
//                answer.setAnalysis(answerDto1.getAnalysis());
//            } else {
//                answer.setAnalysis(answer.getAnalysis() + "," + answerDto1.getAnalysis());
//            }
//
//            index++;
//        }


    }


    /**
     * 渲染考试数据 ， 返回题目集合
     *
     * @param id
     */
    @Override
    public Map<String, Object> paperDetail(Integer id) {

        //单选题集合
        int index = 0;
        List<Question> radioList = examMapper.finRadioList(id);
        for (Question question : radioList) {

            //设置答题卡答题状态
            publicDemo(question, id, question.getId());

            question.setSort(index);
            index++;
        }

        //多选题集合
        List<Question> multiList = examMapper.findMultiList(id);
        for (Question question : multiList) {

            //调用公共方法
            publicDemo(question, id, question.getId());

            question.setSort(index);
            index++;
        }

        //判断题集合
        List<Question> judgeList = examMapper.findJudgeList(id);
        for (Question question : judgeList) {

            //设置答题卡答题状态
            publicDemo(question, id, question.getId());

            question.setSort(index);
            index++;
        }

        //简答题集合
        List<Question> shortAnswer = examMapper.findShortAnswer(id);
        for (Question question : shortAnswer) {

            //设置答题卡答题状态
            publicDemo(question, id, question.getId());

            question.setSort(index);
            index++;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("radioList", radioList);
        map.put("multiList", multiList);
        map.put("judgeList", judgeList);
        map.put("shortAnswer", shortAnswer);
        return map;
    }


    @Override
    public List<Question> paperDetail2(Integer id) {
        List<Question> list = examMapper.paperDetail2(id);
        return list;
    }

    /**
     * 添加考试的题目
     */
    @Override
    public void addExamQuestion(InsertExamDto insertExamDto) {
        String examName = insertExamDto.getExamName();
        Integer id = examMapper.getExamId(examName);
        examMapper.addExamQuestion(insertExamDto, id);
    }


    /**
     * 获取考试时长
     */
    @Override
    public Integer getTime(Integer id) {
        Integer time = examMapper.getTime(id);
        return time;
    }

    /**
     * 分配题库
     */
    @Override
    public void doAssignBank(AssginBankDto assginBankDto) {
        //1 根据id删除题目之前分配的数据
        examMapper.deleteById(assginBankDto.getId());

        //2 重新分配新数据
        List<String> stringList = assginBankDto.getBanksIdList();
        String join = StringUtils.join(stringList, ",");
        examMapper.doAssign(assginBankDto.getId(), join);

        List<String> nameList = new ArrayList<>();
        for (String string : stringList) {
            int i = Integer.parseInt(string);
            String name = examMapper.byNameForId(i);
            nameList.add(name);
        }
        String join1 = StringUtils.join(nameList, ",");
        examMapper.doAssignBankName(assginBankDto.getId(), join1);

    }


    /**
     * 删除题目
     *
     * @param questionId
     */
    @Override
    public void deleteQuestionById(Integer questionId) {
        examMapper.deleteQuestionById(questionId);
    }


    /**
     * 考试管理接口（教师）
     */
    @Override
    public PageInfo<Exam> findByPageManage(ExamTeacherDto examTeacherDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Exam> list = examMapper.findByPageManage(examTeacherDto);
        PageInfo<Exam> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 删除考试
     */
    @Override
    public void deleteExamByName(String examName) {
        examMapper.deleteExamByName(examName);
    }


    /**
     * 公共方法
     */
    private void publicDemo(Question question, Integer id, Long id1) {
        Question q1 = examMapper.selectQu(id, id1);
        question.setActualScore(q1.getActualScore());
        question.setAnswered(q1.getAnswered());
        question.setIsRight(q1.getIsRight());
        question.setAnswer(q1.getAnswer());

    }

}