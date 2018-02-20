package com.phicomm.smarthome.authservice.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.phicomm.smarthome.authservice.model.dao.TokenModel;

/**
 * token dao.
 */
public interface TokenMapper {

    @Select("select * from tbl_tokens where token=#{token} and status=0 limit 1")
    TokenModel getByToken(@Param("token") String token);

    @Select("select * from tbl_tokens where uid=#{uid} and status=0 limit 1")
    TokenModel getByUid(@Param("uid") String uid);

    @Insert("insert into tbl_tokens (uid, access_token, ack_timeout, refresh_token, rfs_timeout, status, create_time, update_time) "
            + "values (#{model.uid}, #{model.access_token},#{model.ack_timeout},#{model.refresh_token},#{model.rfs_timeout},#{model.status},#{model.create_time},#{model.update_time})")
    int insertToken(@Param("model") TokenModel model);

    @Update("update tbl_tokens set access_token = #{model.access_token}, ack_timeout=#{model.ack_timeout}, refresh_token=#{model.refresh_token}, rfs_timeout=#{model.rfs_timeout}, status=#{model.status}, update_time=#{model.update_time} where uid=#{model.uid}")
    public int updateToken(@Param("model") TokenModel model);
}
