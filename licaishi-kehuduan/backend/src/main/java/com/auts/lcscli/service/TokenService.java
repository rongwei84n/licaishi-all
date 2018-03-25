package com.auts.lcscli.service;

import com.auts.lcscli.model.dao.TokenModel;

public interface TokenService {

    TokenModel getByUid(String uid);

    TokenModel getByToken(String token);

    int insertToken(TokenModel model);

    int updateToken(TokenModel model);
}
