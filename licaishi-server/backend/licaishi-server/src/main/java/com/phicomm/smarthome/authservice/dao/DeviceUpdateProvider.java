package com.phicomm.smarthome.authservice.dao;

import org.apache.ibatis.jdbc.SQL;

import com.phicomm.smarthome.authservice.model.dao.DeviceDaoModel;


/**
 * @author qiangbin.wei
 *
 *         2017年8月7日
 */
public class DeviceUpdateProvider {
    /**
     * update device的构造方法，可以根据具体条件构造sql语句
     * @param device 设备实体对象
     * @return sql语句
     */
    public String updateDevice(final DeviceDaoModel device) {
        return new SQL() {
            {
                UPDATE("ph_device");
                if (device.getName() != null) {
                    SET("name = #{name}");
                }
                if (device.getPic() != null) {
                    SET("pic = #{pic}");
                }
                if (device.getPosition() != 0) {
                    SET("position = #{position}");
                }

                SET("status = #{status}");
                SET("update_time = #{updateTime}");
                WHERE("device_id = #{deviceId}");
            }
        } .toString();
    }
}
