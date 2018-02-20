package com.phicomm.smarthome.authservice.model.request;

/**
 * 找回密码 重新设置密码界面request model.
 * @author huangrongwei
 *
 */
public class ForgetPasswordRequestModel {

    private String authorizationcode;

    private String mailaddress;

    private String newpassword;

    private String phonenumber;

    private String verificationcode;

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

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getVerificationcode() {
        return verificationcode;
    }

    public void setVerificationcode(String verificationcode) {
        this.verificationcode = verificationcode;
    }

    @Override
    public String toString() {
        return "ForgetPasswordRequestModel [authorizationcode=" + authorizationcode + ", mailaddress=" + mailaddress
                + ", newpassword=" + newpassword + ", phonenumber=" + phonenumber + ", verificationcode="
                + verificationcode + "]";
    }
}
