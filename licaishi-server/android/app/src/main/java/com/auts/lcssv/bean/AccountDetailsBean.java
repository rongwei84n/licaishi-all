package com.auts.lcssv.bean;

import java.io.Serializable;

/**
 * 账户详情
 *
 * @author xiaolei.yang
 * @date 2017/7/24
 */

public class AccountDetailsBean implements Serializable {
    private static final long serialVersionUID = 8787346763438374907L;

    private String accountname; //	账户名 如：user1
    private String address; //	地址 如：上海市松江区文吉路99号
    private String age; //	年龄	如：19
    private String img; //	图片地址	如：https://mall.phicomm.com/Uploads/image/userPhotos/5817031b71979.jpg
    private String mailaddress; //	邮箱地址	如：user1@phicomm.com
    private String nickname; //	昵称	如：三
    private String phonenumber; //	电话号码	如：12345678901
    private String realname; //	真实姓名	如：张三
    private String sex; //	性别	1代表男 ，2代表女
    private String uid; //	用户id	如：1001
    private String zipcode; //	邮编	如：201600
    private String zone; //	区域	如：松江区
    private String workstudio;

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMailaddress() {
        return mailaddress;
    }

    public void setMailaddress(String mailaddress) {
        this.mailaddress = mailaddress;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getWorkstudio() {
        return workstudio;
    }

    public void setWorkstudio(String workstudio) {
        this.workstudio = workstudio;
    }

    @Override
    public String toString() {
        return "AccountDetailsBean{" +
                "accountname='" + accountname + '\'' +
                ", address='" + address + '\'' +
                ", age='" + age + '\'' +
                ", img='" + img + '\'' +
                ", mailaddress='" + mailaddress + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", realname='" + realname + '\'' +
                ", sex='" + sex + '\'' +
                ", uid='" + uid + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", zone='" + zone + '\'' +
                ", workstudio='" + workstudio + '\'' +
                '}';
    }
}
