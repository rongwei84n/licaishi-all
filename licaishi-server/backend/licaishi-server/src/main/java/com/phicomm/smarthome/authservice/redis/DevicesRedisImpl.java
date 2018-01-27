package com.phicomm.smarthome.authservice.redis;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.authservice.dao.DevicesMapper;
import com.phicomm.smarthome.authservice.model.dao.DeviceDaoModel;

/**
 *
 * @author huangrongwei
 *
 */
@Service
public class DevicesRedisImpl {

    @Autowired
    DevicesMapper mapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<DeviceDaoModel> queryDeviceByUid(String uid) {
        return mapper.queryDevicesByUid(uid);
    }

    /**
     * 根据mac查询，比如绑定的时候查看此mac是否已经被绑定过 如果此设备已经被人绑定过，那么就返回提示已经绑定，请先解绑
     */
    public List<DeviceDaoModel> queryDevicesByDeviceMac(String deviceId) {
        return mapper.queryDevicesByDeviceMac(deviceId);
    }

    public List<DeviceDaoModel> queryDevices() {
        return mapper.queryDevices();
    }
}
