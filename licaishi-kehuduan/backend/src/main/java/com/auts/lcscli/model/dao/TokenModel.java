package com.auts.lcscli.model.dao;

public class TokenModel {

    private String uid;

    private String access_token;

    private long ack_timeout;

    private String refresh_token;

    private long rfs_timeout;

    private int status;

    private long create_time;

    private long update_time;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getAck_timeout() {
        return ack_timeout;
    }

    public void setAck_timeout(long ack_timeout) {
        this.ack_timeout = ack_timeout;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public long getRfs_timeout() {
        return rfs_timeout;
    }

    public void setRfs_timeout(long rfs_timeout) {
        this.rfs_timeout = rfs_timeout;
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

    @Override
    public String toString() {
        return "TokenModel [uid=" + uid + ", access_token=" + access_token + ", ack_timeout=" + ack_timeout
                + ", refresh_token=" + refresh_token + ", rfs_timeout=" + rfs_timeout + ", status=" + status
                + ", create_time=" + create_time + ", update_time=" + update_time + "]";
    }
}
