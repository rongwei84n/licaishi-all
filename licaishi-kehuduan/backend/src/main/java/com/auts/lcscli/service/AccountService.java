package com.auts.lcscli.service;

import com.auts.lcscli.model.dao.AccountModel;

public interface AccountService {

    AccountModel queryMaxUid();

    AccountModel queryByUid(String uid);

    AccountModel login(String userName, String pwd);

    AccountModel loginPhone(String phone, String pwd);

    int updateAccount(AccountModel model);

    int register(AccountModel model, int financerId);

    AccountModel queryByUserPhone(String phone);
}
