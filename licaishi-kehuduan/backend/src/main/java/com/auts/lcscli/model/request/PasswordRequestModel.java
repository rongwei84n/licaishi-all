package com.auts.lcscli.model.request;

/**
 * 修改密码 request model.
 * @author huangrongwei
 */
public class PasswordRequestModel extends BaseRequestModel {

    private String newpassword;

    private String oldpassword;

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    @Override
    public String toString() {
        return "PasswordRequestModel [newpassword=" + newpassword + ", oldpassword=" + oldpassword + "]";
    }
}
