package com.phicomm.smarthome.authservice.service;

import java.util.List;

import com.phicomm.smarthome.authservice.model.dao.DeviceDaoModel;

public interface BindDeviceWithAccountService {
    List<DeviceDaoModel> queryDeviceByUid(String uid);

    List<DeviceDaoModel> queryDevicesByDeviceMac(String deviceMac);

    List<DeviceDaoModel> queryDevices();
}
