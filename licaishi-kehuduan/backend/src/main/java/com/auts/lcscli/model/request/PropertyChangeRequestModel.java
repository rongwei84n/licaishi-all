package com.auts.lcscli.model.request;

/**
 *
 * 修改账号公用属性的请求类.
 * @author huangrongwei
 *
 */
public class PropertyChangeRequestModel extends BaseRequestModel {

    private String address;

    private String age;

    private String birthday;

    private String height;

    private String job;

    private String nickname;

    private String realname;

    private String sex;

    private String weight;

    private String zipcode;

    private String zone;

    private String workstudio;

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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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
        return "PropertyChangeRequestModel [address=" + address + ", age=" + age + ", birthday=" + birthday
                + ", height=" + height + ", job=" + job + ", nickname=" + nickname + ", realname=" + realname + ", sex="
                + sex + ", weight=" + weight + ", zipcode=" + zipcode + ", zone=" + zone + ", workstudio=" + workstudio
                + "]";
    }
}
