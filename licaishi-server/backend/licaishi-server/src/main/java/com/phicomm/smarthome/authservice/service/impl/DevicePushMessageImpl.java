package com.phicomm.smarthome.authservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.authservice.dao.DevicePushMessageMapper;
import com.phicomm.smarthome.authservice.service.DevicePushMessageService;
import com.phicomm.smarthome.model.MessagePushDaoModel;

@Service
public class DevicePushMessageImpl implements DevicePushMessageService {

    @Autowired
    DevicePushMessageMapper mapper;

    /**
     * 插入一条数据到消息通知表中
     */
    @Override
    public int insert(MessagePushDaoModel model) {
        return mapper.insert(model);
    }
}
