package com.auts.lcscli.model.common;

/**
 * 数据库映射基类
 * @author rongwei.huang
 *
 */
public class BaseDaoModel {
    //自增长id
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
