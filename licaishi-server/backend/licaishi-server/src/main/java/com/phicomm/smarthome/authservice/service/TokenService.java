package com.phicomm.smarthome.authservice.service;

import com.phicomm.smarthome.authservice.model.dao.TokenModel;

public interface TokenService {

    TokenModel getByUid(String uid);

    TokenModel getByToken(String token);

    int insertToken(TokenModel model);

    int updateToken(TokenModel model);
}
