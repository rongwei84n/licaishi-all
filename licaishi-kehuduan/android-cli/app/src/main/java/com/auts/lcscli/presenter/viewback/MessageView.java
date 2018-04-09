package com.auts.lcscli.presenter.viewback;

import com.auts.lcscli.bean.MessagesBean;

/**
 * Created by weiming.zeng on 2017/8/25.
 */

public class MessageView {

    public void getMessageSuccess(MessagesBean data) {}

    public void getMessageError(String msg) {}

    public void checkMessageError(String msg) {}

    public void checkMessageSuccess(String result) {}
}
