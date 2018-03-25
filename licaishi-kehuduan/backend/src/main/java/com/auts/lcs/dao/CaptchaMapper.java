package com.auts.lcs.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.auts.lcs.model.dao.CaptchaModel;


public interface CaptchaMapper {

    @Insert("insert into tbl_captcha (phone_no, captcha_code, send_time) "
            + "values (#{cm.phoneNo}, #{cm.captchaCode},#{cm.sendTime})")
    int insert(@Param("cm") CaptchaModel cm);


    @Select("select * from tbl_captcha where phone_no=#{phoneNo} limit 1")
    @Results({
        @Result(property = "phoneNo", column = "phone_no"),
        @Result(property = "captchaCode", column = "captcha_code"),
        @Result(property = "sendTime", column = "send_time")
    })
    CaptchaModel queryByPhoneNo(@Param("phoneNo") String phoneNo);

    @Update("update tbl_captcha set captcha_code = #{model.captchaCode}, send_time=#{model.sendTime} where phone_no=#{model.phoneNo}")
    int updateCaptcha(@Param("model") CaptchaModel model);

    @Delete("delete from tbl_captcha where phone_no=#{phoneNo}")
    int delCaptcha(@Param("phoneNo") String phoneNo);
}
