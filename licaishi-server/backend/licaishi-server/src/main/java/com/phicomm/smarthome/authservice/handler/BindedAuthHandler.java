package com.phicomm.smarthome.authservice.handler;

import com.phicomm.smarthome.authservice.model.dao.DeviceDaoModel;
import com.phicomm.smarthome.authservice.redis.DevicesRedisImpl;
import com.phicomm.smarthome.authservice.util.MyListUtils;
import com.phicomm.smarthome.cache.Cache;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BindedAuthHandler {

    private static final Logger LOGGER = LogManager.getLogger(BindedAuthHandler.class);

    public static BindedAuthHandler instance;

    @Autowired
    DevicesRedisImpl devicesRedisImpl;

    @Autowired
    private Cache cache;

    @PostConstruct
    public void init() {
        instance = this;
    }

    /**
     * called when init
     * @see #BaseMqttTopicsInitlization.start
     * @param callback
     */
    public void onStart() {
    }

    public String authDeviceIdAndUid(String deviceId) {
      //根据deviceid查询uid，如果uid和clientid相同，那么返回true,否则返回false;
        //为了性能，查询的时候需要加分布式缓存-redis
        Object cachedObj = cache.get(deviceId);
        LOGGER.debug("cached obj [{}] deviceId [{}]", cachedObj, deviceId);
        if (cachedObj != null) {
            return String.valueOf(cachedObj);
        }
        List<DeviceDaoModel> devices = null;
        try {
            devices = devicesRedisImpl.queryDevicesByDeviceMac(deviceId);
        } catch (Exception e) {
            LOGGER.error("auth publish message error ", e);
            return null;
        }

        if (MyListUtils.isEmpty(devices)) {
            LOGGER.debug("query no devices");
            return "";
        }
        String savedUid =  devices.get(0).getUid();
        LOGGER.debug("savedUid [{}] ", savedUid);
        cache.put(deviceId, savedUid);
        return savedUid;
    }
}
