package com.auts.lcs.service;

import com.auts.lcs.model.dao.TokenModel;

public interface TokenService {

    TokenModel getByUid(String uid);

    TokenModel getByToken(String token);

    int insertToken(TokenModel model);

    int updateToken(TokenModel model);
}
