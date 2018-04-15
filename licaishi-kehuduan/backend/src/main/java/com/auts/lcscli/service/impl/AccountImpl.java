package com.auts.lcscli.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auts.lcscli.dao.AccountMapper;
import com.auts.lcscli.dao.CustomerMapper;
import com.auts.lcscli.dao.FinancerMapper;
import com.auts.lcscli.model.dao.AccountModel;
import com.auts.lcscli.model.dao.CustomerModel;
import com.auts.lcscli.service.AccountService;

@Service
public class AccountImpl implements AccountService {
    @Autowired
    AccountMapper mapper;
    @Autowired
    FinancerMapper financerMapper;

    @Autowired
    CustomerMapper customerMapper;

    @Override
    public AccountModel login(String userName, String pwd) {
        return mapper.login(userName, pwd);
    }

    @Override
    public AccountModel loginPhone(String phone, String pwd) {
        return mapper.loginPhone(phone, pwd);
    }

    @Override
    @Transactional
    public int register(AccountModel model, int financerId) {
        mapper.register(model);

        //生成理财师信息
//        FinancerModel financer = new FinancerModel();
//    	financer.setPhone(model.getPhone());
//    	financer.setUserId(Integer.parseInt(model.getUid()));
//    	financer.setName("");
//    	financerMapper.addFinancer(financer);

        //添加生成理财师的客户信息 tbl_customer
        CustomerModel customer = new CustomerModel(model);
        customer.setFinancerId(financerId);
        customerMapper.addCustomer(customer);
    	return 1;
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
