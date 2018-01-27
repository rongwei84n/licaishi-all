package com.phicomm.smarthome.authservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.authservice.model.dao.DeviceDaoModel;
import com.phicomm.smarthome.authservice.redis.DevicesRedisImpl;
import com.phicomm.smarthome.authservice.service.BindDeviceWithAccountService;

@Service
public class BindDeviceWithAccountImpl implements BindDeviceWithAccountService {
    @Autowired
    DevicesRedisImpl deviceRedis;

    @Override
    public List<DeviceDaoModel> queryDeviceByUid(String uid) {
        return deviceRedis.queryDeviceByUid(uid);
    }

    /**
     * 根据mac查询，比如绑定的时候查看此mac是否已经被绑定过 如果此设备已经被人绑定过，那么就返回提示已经绑定，请先解绑
    */
    @Override
    public List<DeviceDaoModel> queryDevicesByDeviceMac(String deviceId) {
        return deviceRedis.queryDevicesByDeviceMac(deviceId);
    }

    @Override
    public List<DeviceDaoModel> queryDevices() {
        return deviceRedis.queryDevices();
    }
}
