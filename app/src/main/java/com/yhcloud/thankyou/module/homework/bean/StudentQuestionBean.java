package com.yhcloud.thankyou.module.homework.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/12.
 */

public class StudentQuestionBean implements Serializable {

    /**
     * QusetionId : 825
     * score : 16
     * HomeworkId : 439
     * resultResultStatus :
     * AnswerPic : []
     * QuestionTitle : 题干6
     * QuestionTitleName : 下面说法没有错的是（ ）
     * ResourceType : 0
     * AnswerContent : ["B"]
     * IsRight : 2
     * url : /edu/m17/M1722P01/M1722P01001/Id/825
     * QuestionContent : ["A: 《女娲补天》和《夸父追日》都是童话故事。《女娲补天》和《夸父追日》都是童话故事。《女娲补天》和《夸父追日》都是童话故事。","B: \u201c130万个地球才能抵得上一个太阳。\u201d运用了打比方的说明方法。\u201c130万个地球才能抵得上一个太阳。\u201d运用了打比方的说明方法。\u201c130万个地球才能抵得上一个太阳。\u201d运用了打比方的说明方法。","B: \u201c他把零钱用光了。\u201d与\u201c教室光线充足。\u201d中的\u201c光\u201d字意思不一样。\u201c他把零钱用光了。\u201d与\u201c教室光线充足。\u201d中的\u201c光\u201d字意思不一样。\u201c他把零钱用光了。\u201d与\u201c教室光线充足。\u201d中的\u201c光\u201d字意思不一样。","B: 《学会查\u201c无字词典\u201d》中的\u201c无字词典\u201d是指生活里的学问。 《学会查\u201c无字词典\u201d》中的\u201c无字词典\u201d是指生活里的学问。 《学会查\u201c无字词典\u201d》中的\u201c无字词典\u201d是指生活里的学问。 "]
     * correctAnswer : ["A"]
     */

    private String QusetionId;
    private String score;
    private String HomeworkId;
    private String resultResultStatus;
    private String QuestionTitle;
    private String QuestionTitleName;
    private String ResourceType;
    private String IsRight;
    private String url;
    //主观题图片
    private ArrayList<String> AnswerPic;
    //学生答案
    private ArrayList<String> AnswerContent;
    //选择及填空题选项
    private ArrayList<String> QuestionContent;
    //正确答案
    private ArrayList<String> correctAnswer;

    public String getQusetionId() {
        return QusetionId;
    }

    public void setQusetionId(String QusetionId) {
        this.QusetionId = QusetionId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getHomeworkId() {
        return HomeworkId;
    }

    public void setHomeworkId(String HomeworkId) {
        this.HomeworkId = HomeworkId;
    }

    public String getResultResultStatus() {
        return resultResultStatus;
    }

    public void setResultResultStatus(String resultResultStatus) {
        this.resultResultStatus = resultResultStatus;
    }

    public String getQuestionTitle() {
        return QuestionTitle;
    }

    public void setQuestionTitle(String QuestionTitle) {
        this.QuestionTitle = QuestionTitle;
    }

    public String getQuestionTitleName() {
        return QuestionTitleName;
    }

    public void setQuestionTitleName(String QuestionTitleName) {
        this.QuestionTitleName = QuestionTitleName;
    }

    public String getResourceType() {
        return ResourceType;
    }

    public void setResourceType(String ResourceType) {
        this.ResourceType = ResourceType;
    }

    public String getIsRight() {
        return IsRight;
    }

    public void setIsRight(String IsRight) {
        this.IsRight = IsRight;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<String> getAnswerPic() {
        return AnswerPic;
    }

    public void setAnswerPic(ArrayList<String> answerPic) {
        AnswerPic = answerPic;
    }

    public ArrayList<String> getAnswerContent() {
        return AnswerContent;
    }

    public void setAnswerContent(ArrayList<String> answerContent) {
        AnswerContent = answerContent;
    }

    public ArrayList<String> getQuestionContent() {
        return QuestionContent;
    }

    public void setQuestionContent(ArrayList<String> questionContent) {
        QuestionContent = questionContent;
    }

    public ArrayList<String> getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(ArrayList<String> correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
