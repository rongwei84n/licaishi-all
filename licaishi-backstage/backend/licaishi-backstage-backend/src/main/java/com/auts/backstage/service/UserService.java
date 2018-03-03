package com.auts.backstage.service;

import com.auts.backstage.model.dao.AccountModel;

public interface UserService {

	AccountModel queryLoginUser(String username, String password);

}