package com.auts.lcssv.event;

/**
 * 修改昵称
 * Created by xiaolei.yang on 2017/7/24.
 */

public class ChangeNicknameEvent {
    private String nickname;

    public ChangeNicknameEvent(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
