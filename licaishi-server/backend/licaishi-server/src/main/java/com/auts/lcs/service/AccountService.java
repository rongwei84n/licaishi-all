package com.auts.lcs.service;

import com.auts.lcs.model.dao.AccountModel;

public interface AccountService {

    AccountModel queryMaxUid();

    AccountModel queryByUid(String uid);

    AccountModel login(String userName, String pwd);

    AccountModel loginPhone(String phone, String pwd);

    int updateAccount(AccountModel model);

    int register(AccountModel model);

    AccountModel queryByUserPhone(String phone);
}
