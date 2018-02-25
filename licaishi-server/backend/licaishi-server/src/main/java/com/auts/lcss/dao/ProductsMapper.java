package com.auts.lcss.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.auts.lcss.model.dao.HotProductModel;

/**
 * 产品表方法入口
 * @author huangrongwei
 *
 */
public interface ProductsMapper {
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

    /**
     */
    @Select("select * from ph_device where device_id=#{deviceId} and status=0 limit 1")
    HotProductModel queryProducts(@Param("deviceId") String deviceId);

    /**
     */
    @Select("select * from ph_device where device_id=#{deviceId} and status=0 limit 1")
    List<HotProductModel> queryHotProducts();
}
