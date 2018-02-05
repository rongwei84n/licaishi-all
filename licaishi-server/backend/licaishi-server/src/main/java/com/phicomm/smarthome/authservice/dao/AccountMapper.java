package com.phicomm.smarthome.authservice.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.phicomm.smarthome.authservice.model.dao.AccountModel;

/**
 * 产品表方法入口
 * @author huangrongwei
 *
 */
public interface AccountMapper {
//    /**
//     * 查询所有的生效的device
//     * @return 查询结果
//     */
//    @Select("select * from ph_device where status=0")
//    @Results({ @Result(property = "id", column = "id"), @Result(property = "uid", column = "uid"),
//            @Result(property = "fid", column = "fid"), @Result(property = "ridDeviceidId", column = "rid_deviceid_id"),
//            @Result(property = "rid", column = "rid"), @Result(property = "model", column = "model"),
//            @Result(property = "hardwareVersion", column = "hardware_version"),
//            @Result(property = "romVersion", column = "rom_version"),
//            @Result(property = "deviceId", column = "device_id"), @Result(property = "name", column = "name"),
//            @Result(property = "pic", column = "pic"), @Result(property = "position", column = "position"),
//            @Result(property = "createTime", column = "create_time"),
//            @Result(property = "updateTime", column = "update_time"), @Result(property = "status", column = "status"),
//            @Result(property = "deviceType", column = "device_type") })
//    public List<DeviceDaoModel> queryDevices();

    @Select("select * from tbl_user where user_name=#{userName} and passwd=#{pwd} and status=0 limit 1")
    AccountModel login(@Param("userName") String userName, @Param("pwd") String pwd);

    @Select("select * from tbl_user where phone=#{phone} and passwd=#{pwd} and status=0 limit 1")
    AccountModel loginPhone(@Param("phone") String phone, @Param("pwd") String pwd);
}
