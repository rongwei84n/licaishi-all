package com.auts.backstage.model.dao;

/**
 * 账户相关数据库Model.
 * @author huangrongwei
 *
 */
public class AccountModel {

    private String uid;

    private String user_name;

    private String real_name;

    private String phone;

    private String passwd;

    private String email;

    private int sex;

    private String remark;

    private int role;

    private int status;

    private long create_time;

    private long update_time;

    private String workstudio;

    private String avtr;//头像

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public String getWorkstudio() {
        return workstudio;
    }

    public void setWorkstudio(String workstudio) {
        this.workstudio = workstudio;
    }

    public String getAvtr() {
        return avtr;
    }

    public void setAvtr(String avtr) {
        this.avtr = avtr;
    }

    @Override
    public String toString() {
        return "AccountModel [uid=" + uid + ", user_name=" + user_name + ", real_name=" + real_name + ", phone=" + phone
                + ", passwd=" + passwd + ", email=" + email + ", sex=" + sex + ", remark=" + remark + ", role=" + role
                + ", status=" + status + ", create_time=" + create_time + ", update_time=" + update_time
                + ", workstudio=" + workstudio + ", avtr=" + avtr + "]";
    }
}
