package com.phicomm.smarthome.authservice.model.request;

public class LoginRequestModel extends BaseRequestModel {

    private String authorizationcode;

    private String mailaddress;

    private String password;

    private String phonenumber;

    private String username;

    public String getAuthorizationcode() {
        return authorizationcode;
    }

    public void setAuthorizationcode(String authorizationcode) {
        this.authorizationcode = authorizationcode;
    }

    public String getMailaddress() {
        return mailaddress;
    }

    public void setMailaddress(String mailaddress) {
        this.mailaddress = mailaddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
