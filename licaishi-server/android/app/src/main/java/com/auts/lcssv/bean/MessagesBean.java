package com.auts.lcssv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 消息提醒
 *
 * Created by weiming.zeng on 2017/8/21.
 */

public class MessagesBean implements Serializable {
    private int has_next_page;
    private int total_nums;
    private int page;
    private int page_size;
    private long latest_timestamp;
    private List<MessageBean> messages;



    public int getHas_next_page() {
        return has_next_page;
    }

    public void setHas_next_page(int has_next_page) {
        this.has_next_page = has_next_page;
    }

    public int getTotal_nums() {
        return total_nums;
    }

    public void setTotal_nums(int total_nums) {
        this.total_nums = total_nums;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public long getLatest_timestamp() {
        return latest_timestamp;
    }

    public void setLatest_timestamp(long latest_timestamp) {
        this.latest_timestamp = latest_timestamp;
    }

    public List<MessageBean> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageBean> messages) {
        this.messages = messages;
    }
}
