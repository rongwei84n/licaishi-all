package com.auts.lcscli.bean;

import java.io.Serializable;

/**
 * 账户详情
 *
 * @author xiaolei.yang
 * @date 2017/7/24
 */

public class AccountBean extends FxResponse implements Serializable {
    private static final long serialVersionUID = -8308296481729872103L;

    private AccountDetailsBean data;

    public AccountDetailsBean getData() {
        return data;
    }

    public void setData(AccountDetailsBean data) {
        this.data = data;
    }
}
