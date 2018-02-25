package com.auts.backstage.service;

import com.auts.backstage.model.dao.TokenModel;


public interface TokenService {

    TokenModel getByUid(String uid);

    TokenModel getByToken(String token);

    int insertToken(TokenModel model);

    int updateToken(TokenModel model);
}
