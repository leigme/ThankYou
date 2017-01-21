package com.yhcloud.thankyou.module.homework.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */

public class TeacherQuestionBean implements Serializable {

    /**
     * Id : 925
     * homeworkId : 470
     * Title : asdfasdf
     * Content : asdfasdfdas
     * CreatedBy : 3237
     * CreateTime : 2017-01-08 11:28:19
     * QuestionStatus : 0
     * QuestionType : 1
     * Score : 16
     * ChapterId : 73198
     * QuestionIndex : 1
     * questionPic : []
     * url : /edu/m17/M1722P01/M1722P01001/Id/925
     * correctAnswer : ["asdfasdf"]
     * QuestionExtend : [{"Id":"2167","QuestionId":"925","ExtendType":"3","PropertyName":"其他题型答案","PropertyValue":"asdfasdf","PropertyType":"4"}]
     */

    private String Id;
    private String homeworkId;
    private String Title;
    private String Content;
    private String CreatedBy;
    private String CreateTime;
    private String QuestionStatus;
    private String QuestionType;
    private String Score;
    private String ChapterId;
    private String QuestionIndex;
    private String url;
    private List<String> questionPic;
    private List<String> correctAnswer;
    private List<QuestionExtendBean> QuestionExtend;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getQuestionStatus() {
        return QuestionStatus;
    }

    public void setQuestionStatus(String QuestionStatus) {
        this.QuestionStatus = QuestionStatus;
    }

    public String getQuestionType() {
        return QuestionType;
    }

    public void setQuestionType(String QuestionType) {
        this.QuestionType = QuestionType;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String Score) {
        this.Score = Score;
    }

    public String getChapterId() {
        return ChapterId;
    }

    public void setChapterId(String ChapterId) {
        this.ChapterId = ChapterId;
    }

    public String getQuestionIndex() {
        return QuestionIndex;
    }

    public void setQuestionIndex(String QuestionIndex) {
        this.QuestionIndex = QuestionIndex;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getQuestionPic() {
        return questionPic;
    }

    public void setQuestionPic(List<String> questionPic) {
        this.questionPic = questionPic;
    }

    public List<String> getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(List<String> correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<QuestionExtendBean> getQuestionExtend() {
        return QuestionExtend;
    }

    public void setQuestionExtend(List<QuestionExtendBean> QuestionExtend) {
        this.QuestionExtend = QuestionExtend;
    }

    public static class QuestionExtendBean {
        /**
         * Id : 2167
         * QuestionId : 925
         * ExtendType : 3
         * PropertyName : 其他题型答案
         * PropertyValue : asdfasdf
         * PropertyType : 4
         */

        private String Id;
        private String QuestionId;
        private String ExtendType;
        private String PropertyName;
        private String PropertyValue;
        private String PropertyType;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getQuestionId() {
            return QuestionId;
        }

        public void setQuestionId(String QuestionId) {
            this.QuestionId = QuestionId;
        }

        public String getExtendType() {
            return ExtendType;
        }

        public void setExtendType(String ExtendType) {
            this.ExtendType = ExtendType;
        }

        public String getPropertyName() {
            return PropertyName;
        }

        public void setPropertyName(String PropertyName) {
            this.PropertyName = PropertyName;
        }

        public String getPropertyValue() {
            return PropertyValue;
        }

        public void setPropertyValue(String PropertyValue) {
            this.PropertyValue = PropertyValue;
        }

        public String getPropertyType() {
            return PropertyType;
        }

        public void setPropertyType(String PropertyType) {
            this.PropertyType = PropertyType;
        }
    }
}
