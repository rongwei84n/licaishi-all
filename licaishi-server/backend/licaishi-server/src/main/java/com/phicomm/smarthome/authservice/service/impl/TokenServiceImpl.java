package com.phicomm.smarthome.authservice.service.impl;

import com.phicomm.smarthome.authservice.dao.TokenMapper;
import com.phicomm.smarthome.authservice.model.dao.TokenModel;
import com.phicomm.smarthome.authservice.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    TokenMapper mapper;

    @Override
    public TokenModel getByToken(String token) {
        return mapper.getByToken(token);
    }

    @Override
    public TokenModel getByUid(String uid) {
        return mapper.getByUid(uid);
    }

    @Override
    public int insertToken(TokenModel model) {
        return mapper.insertToken(model);
    }

    @Override
    public int updateToken(TokenModel model) {
        return mapper.updateToken(model);
    }
}
