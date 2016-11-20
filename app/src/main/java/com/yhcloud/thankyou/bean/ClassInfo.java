package com.yhcloud.thankyou.bean;

import java.io.Serializable;

/**
 * Created by leig on 2016/11/19.
 */

public class ClassInfo implements Serializable {


    /**
     * ClassId : 161234
     * ClassName : 五年级阳环班
     * ClassLogo : RES20161011/RES201610111143425523.jpg
     * Status : 1
     * SchoolName : 阳环学校
     * SchoolId : 5068
     */

    private String ClassId;
    private String ClassName;
    private String ClassLogo;
    private int Status;
    private String SchoolName;
    private String SchoolId;
    private boolean selected;

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String ClassId) {
        this.ClassId = ClassId;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }

    public String getClassLogo() {
        return ClassLogo;
    }

    public void setClassLogo(String ClassLogo) {
        this.ClassLogo = ClassLogo;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String SchoolName) {
        this.SchoolName = SchoolName;
    }

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String SchoolId) {
        this.SchoolId = SchoolId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
