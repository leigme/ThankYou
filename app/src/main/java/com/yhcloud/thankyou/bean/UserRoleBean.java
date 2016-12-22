package com.yhcloud.thankyou.bean;

/**
 * Created by Administrator on 2016/12/22.
 */

public class UserRoleBean {
    /**
     * RoleId : 1006
     * RoleCode : deansOffice
     * RoleName : 教务处
     */

    private String RoleId;
    private String RoleCode;
    private String RoleName;

    public String getRoleId() {
        return RoleId;
    }

    public void setRoleId(String RoleId) {
        this.RoleId = RoleId;
    }

    public String getRoleCode() {
        return RoleCode;
    }

    public void setRoleCode(String RoleCode) {
        this.RoleCode = RoleCode;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String RoleName) {
        this.RoleName = RoleName;
    }
}
