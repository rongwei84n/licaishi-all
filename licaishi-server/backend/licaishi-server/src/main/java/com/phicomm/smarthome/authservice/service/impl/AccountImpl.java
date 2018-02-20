package com.phicomm.smarthome.authservice.service.impl;

import com.phicomm.smarthome.authservice.dao.AccountMapper;
import com.phicomm.smarthome.authservice.model.dao.AccountModel;
import com.phicomm.smarthome.authservice.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountImpl implements AccountService {
    @Autowired
    AccountMapper mapper;

    @Override
    public AccountModel login(String userName, String pwd) {
        return mapper.login(userName, pwd);
    }

    @Override
    public AccountModel loginPhone(String phone, String pwd) {
        return mapper.loginPhone(phone, pwd);
    }

    @Override
    public int register(AccountModel model) {
        return mapper.register(model);
    }

    @Override
    public AccountModel queryMaxUid() {
        return mapper.queryMaxUid();
    }

    @Override
    public AccountModel queryByUserPhone(String phone) {
        return mapper.queryByUserPhone(phone);
    }

    @Override
    public AccountModel queryByUid(String uid) {
        return mapper.queryByUid(uid);
    }

    @Override
    public int updateAccount(AccountModel model) {
        return mapper.updateAccount(model);
    }
}
