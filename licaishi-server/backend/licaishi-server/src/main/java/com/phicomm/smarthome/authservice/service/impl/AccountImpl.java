package com.phicomm.smarthome.authservice.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.authservice.dao.AccountMapper;
import com.phicomm.smarthome.authservice.model.dao.AccountModel;
import com.phicomm.smarthome.authservice.service.AccountService;

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
}
