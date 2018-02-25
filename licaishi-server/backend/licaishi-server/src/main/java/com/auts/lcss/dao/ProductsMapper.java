package com.auts.lcss.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.auts.lcss.model.dao.ProductModel;


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
    ProductModel queryProducts(@Param("deviceId") String deviceId);

    /**
     */
    @Select("select * from Product where p_id= 1 limit 1")
    @Results({
    	@Result(property = "id", column = "p_id"), @Result(property = "pCode", column = "p_code"),
    	@Result(property = "pShortName", column = "p_short_name"), @Result(property = "pFullName", column = "p_full_name"),
    	@Result(property = "pType", column = "p_type"), @Result(property = "pExpectAnnualRevenue", column = "p_expect_annual_revenue"),
    	@Result(property = "pSaleStatus", column = "p_sale_status"), @Result(property = "pDulTime", column = "p_due_time"),
    	@Result(property = "pSaleStartTime", column = "p_sale_date_start"), @Result(property = "pAllIssuingScale", column = "p_all_issuing_scale"),
    	@Result(property = "pMinAmount", column = "p_min_amount"), @Result(property = "pPaymentInterestType", column = "p_payment_interest_type"),
    	@Result(property = "pInvestType", column = "p_invest_type"), @Result(property = "pSizeRatioType", column = "p_size_ratio_type"),
    	@Result(property = "pInvestOwnerId", column = "p_invest_owner_id")
    })
    List<ProductModel> queryHotProducts();
}
