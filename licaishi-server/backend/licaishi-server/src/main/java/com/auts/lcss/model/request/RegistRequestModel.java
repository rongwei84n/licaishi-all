package com.auts.lcss.model.request;

public class RegistRequestModel extends BaseRequestModel {

    private String authorizationcode;

    private Data data;

    private String mailaddress;

    private String password;

    private String phonenumber;

    private String registersource;

    private String username;

    private String verificationcode;

    public String getAuthorizationcode() {
        return authorizationcode;
    }

    public void setAuthorizationcode(String authorizationcode) {
        this.authorizationcode = authorizationcode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
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

    public String getRegistersource() {
        return registersource;
    }

    public void setRegistersource(String registersource) {
        this.registersource = registersource;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVerificationcode() {
        return verificationcode;
    }

    public void setVerificationcode(String verificationcode) {
        this.verificationcode = verificationcode;
    }

    private class Data {
        private String address;

        private String age;

        private String nickname;

        private String realname;

        private String sex;

        private String zipcode;

        private String zone;

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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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
    }

    @Override
    public String toString() {
        return "RegistRequestModel [authorizationcode=" + authorizationcode + ", data=" + data + ", mailaddress="
                + mailaddress + ", password=" + password + ", phonenumber=" + phonenumber + ", registersource="
                + registersource + ", username=" + username + ", verificationcode=" + verificationcode + "]";
    }
}
