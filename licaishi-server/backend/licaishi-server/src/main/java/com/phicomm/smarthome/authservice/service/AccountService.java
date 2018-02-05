package com.phicomm.smarthome.authservice.service;

import com.phicomm.smarthome.authservice.model.dao.AccountModel;

public interface AccountService {
    AccountModel login(String userName, String pwd);
    AccountModel loginPhone(String phone, String pwd);
}
