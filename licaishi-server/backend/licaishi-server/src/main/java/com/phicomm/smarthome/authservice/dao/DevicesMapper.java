package com.phicomm.smarthome.authservice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.phicomm.smarthome.authservice.model.dao.DeviceDaoModel;

/**
 * ph_device表方法入口
 * @author huangrongwei
 *
 */
public interface DevicesMapper {
    /**
     * 根据uid查询device表
     * @param uid 用户 id
     * @return 查询结果
     */
    @Select("select * from ph_device where uid=#{uid} and status=0")
    @Results({ @Result(property = "id", column = "id"), @Result(property = "uid", column = "uid"),
            @Result(property = "fid", column = "fid"), @Result(property = "ridDeviceidId", column = "rid_deviceid_id"),
            @Result(property = "rid", column = "rid"), @Result(property = "model", column = "model"),
            @Result(property = "hardwareVersion", column = "hardware_version"),
            @Result(property = "romVersion", column = "rom_version"),
            @Result(property = "deviceId", column = "device_id"), @Result(property = "name", column = "name"),
            @Result(property = "pic", column = "pic"), @Result(property = "position", column = "position"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"), @Result(property = "status", column = "status"),
            @Result(property = "deviceType", column = "device_type") })
    public List<DeviceDaoModel> queryDevicesByUid(@Param("uid") String uid);

    /**
     * 根据mac地址/device id查询device表
     * @param deviceId 设备device id
     * @return 查询结果
     */
    @Select("select * from ph_device where device_id=#{deviceId} and status=0")
    @Results({ @Result(property = "id", column = "id"), @Result(property = "uid", column = "uid"),
            @Result(property = "fid", column = "fid"), @Result(property = "ridDeviceidId", column = "rid_deviceid_id"),
            @Result(property = "rid", column = "rid"), @Result(property = "model", column = "model"),
            @Result(property = "hardwareVersion", column = "hardware_version"),
            @Result(property = "romVersion", column = "rom_version"),
            @Result(property = "deviceId", column = "device_id"), @Result(property = "name", column = "name"),
            @Result(property = "pic", column = "pic"), @Result(property = "position", column = "position"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "deviceType", column = "device_type"),
            @Result(property = "taskRemind", column = "task_remind"),
            @Result(property = "onlineStatus", column = "online_status")
    })
    public List<DeviceDaoModel> queryDevicesByDeviceMac(@Param("deviceId") String deviceId);

    /**
     * 查询所有的生效的device
     * @return 查询结果
     */
    @Select("select * from ph_device where status=0")
    @Results({ @Result(property = "id", column = "id"), @Result(property = "uid", column = "uid"),
            @Result(property = "fid", column = "fid"), @Result(property = "ridDeviceidId", column = "rid_deviceid_id"),
            @Result(property = "rid", column = "rid"), @Result(property = "model", column = "model"),
            @Result(property = "hardwareVersion", column = "hardware_version"),
            @Result(property = "romVersion", column = "rom_version"),
            @Result(property = "deviceId", column = "device_id"), @Result(property = "name", column = "name"),
            @Result(property = "pic", column = "pic"), @Result(property = "position", column = "position"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"), @Result(property = "status", column = "status"),
            @Result(property = "deviceType", column = "device_type") })
    public List<DeviceDaoModel> queryDevices();

    /**
     * 根据device id查询device 表
     * @param deviceId device id
     * @return 查询结果
     */
    @Select("select * from ph_device where device_id=#{deviceId} and status=0 limit 1")
    DeviceDaoModel queryDeviceBindUid(@Param("deviceId") String deviceId);
}
