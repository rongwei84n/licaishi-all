package com.auts.lcs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auts.lcs.dao.TokenMapper;
import com.auts.lcs.model.dao.TokenModel;
import com.auts.lcs.service.TokenService;

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
