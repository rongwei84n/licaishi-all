package com.auts.lcs.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.auts.lcs.consts.Const;
import com.auts.lcs.controller.account.AccountController;
import com.auts.lcs.model.dao.AccountModel;
import com.auts.lcs.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 获取账号详情的返回类.
 *
 */
public class AccountDetailResponseModel extends AccountBaseResponseModel {

    public AccountDetailResponseModel() {}

    public AccountDetailResponseModel(AccountModel model) {
        error = String.valueOf(Const.ErrorCode.Account.OK);
        tokenStatus = String.valueOf(Const.ErrorCode.Account.TOKEN_OK);

        data = new Data();
        data.setAccountname(model.getPhone());
        data.setAddress("");
        if (StringUtil.isNullOrEmpty(model.getAvtr())) {
            data.setImg("");
        } else {
            data.setImg(AccountController.AVATAR_URL_PREFIX + model.getAvtr());
        }
        data.setAge("");
        data.setBirthday("");
        data.setJob("");
        data.setMailaddress(model.getEmail());
        data.setNickname(model.getReal_name());
        data.setPhonenumber(model.getPhone());
        data.setRealname(model.getReal_name());
        data.setRegister_time(String.valueOf(model.getCreate_time()));
        data.setSex(String.valueOf(model.getSex()));
        data.setUid(model.getUid());
        data.setZipcode("");
        data.setZone("");
        data.setWorkstudio(model.getWorkstudio());
    }

    private String message;

    private String reason;

    @JSONField(name = "token_status")
    @JsonProperty("token_status")
    private String tokenStatus;

    private Data data;

    private static class Data {
        private String accountname;

        private String address;

        private String age;

        private String birthday;

        private String img;

        private String job;

        private String mailaddress;

        private String nickname;

        private String phonenumber;

        private String realname;

        private String register_time;

        private String sex;

        private String uid;

        private String zipcode;

        private String zone;

        private String workstudio;

        public String getWorkstudio() {
            return workstudio;
        }

        public void setWorkstudio(String workstudio) {
            this.workstudio = workstudio;
        }

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

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
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

        public String getRegister_time() {
            return register_time;
        }

        public void setRegister_time(String register_time) {
            this.register_time = register_time;
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

        @Override
        public String toString() {
            return "Data [accountname=" + accountname + ", address=" + address + ", age=" + age + ", birthday="
                    + birthday + ", img=" + img + ", job=" + job + ", mailaddress=" + mailaddress + ", nickname="
                    + nickname + ", phonenumber=" + phonenumber + ", realname=" + realname + ", register_time="
                    + register_time + ", sex=" + sex + ", uid=" + uid + ", zipcode=" + zipcode + ", zone=" + zone + "]";
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AccountDetailResponseModel [message=" + message + ", reason=" + reason + ", tokenStatus=" + tokenStatus
                + ", data=" + data + "]";
    }
}
