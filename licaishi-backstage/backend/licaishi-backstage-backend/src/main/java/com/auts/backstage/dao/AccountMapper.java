package com.auts.backstage.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.auts.backstage.model.dao.AccountModel;

/**
 * 账号mapper
 */
public interface AccountMapper {

    @Select("select * from tbl_user where user_name=#{userName} and passwd=#{pwd} and status=0 limit 1")
    AccountModel login(@Param("userName") String userName, @Param("pwd") String pwd);
    
    @Insert("insert into tbl_user (user_name, real_name, passwd, role, status, create_time, update_time) "
            + "values (#{ac.user_name},#{ac.real_name},#{ac.passwd},#{ac.role},#{ac.status},#{ac.create_time},#{ac.update_time})")
    @Options(useGeneratedKeys = true, keyProperty = "ac.uid")
    void addAccount(@Param("ac") AccountModel ac);

    @Select("select * from tbl_user where phone=#{phone} and passwd=#{pwd} and status=0 limit 1")
    AccountModel loginPhone(@Param("phone") String phone, @Param("pwd") String pwd);

    @Insert("insert into tbl_user (uid, user_name, real_name, phone, passwd, email, sex, remark, role, status, create_time, update_time) "
            + "values (#{ac.uid}, #{ac.user_name},#{ac.real_name},#{ac.phone},#{ac.passwd},#{ac.email},#{ac.sex},#{ac.remark},#{ac.role},#{ac.status},#{ac.create_time},#{ac.update_time})")
    int register(@Param("ac") AccountModel ac);

    @Select("select * from tbl_user where status=0 order by uid desc limit 1")
    AccountModel queryMaxUid();

    @Select("select * from tbl_user where status=0 and uid=#{uid} limit 1")
    AccountModel queryByUid(@Param("uid") String uid);

    @Select("select * from tbl_user where phone=#{phone} limit 1")
    AccountModel queryByUserPhone(@Param("phone") String phone);

    @Update("update tbl_user set user_name = #{model.user_name}, real_name=#{model.real_name}, phone=#{model.phone}, passwd=#{model.passwd}, email=#{model.email}, sex=#{model.sex}, remark=#{model.remark}, role=#{model.role}, status=#{model.status}, create_time=#{model.create_time}, update_time=#{model.update_time}, workstudio=#{model.workstudio}, avtr=#{model.avtr} where uid=#{model.uid}")
    int updateAccount(@Param("model") AccountModel model);
    
    @Update("update tbl_user set user_name = #{phone}, real_name=#{realName} where uid=#{uid}")
    int updateAccountByFinancer(@Param("phone") String phone, @Param("realName") String realName, @Param("uid") int uid);
    
    @Delete("delete from tbl_user where uid = #{uid}")
    void deleteAccount(@Param("uid") int uid);

    @Update("update tbl_user set user_name = #{phone}, real_name=#{realName} where uid=#{uid}")
	void updateAccountByCustomer(@Param("phone") String phone, @Param("realName") String name, @Param("uid") int uid);

    @Update("update tbl_user set status = -1 where uid=#{userId}")
	void handelCancel(@Param("userId") int userId);

    @Update("update tbl_user set status = 0 where uid=#{userId}")
	void handleNormal(@Param("userId") int userId);
    
    @Select("select * from tbl_user where uid=#{uid}")
    AccountModel queryModelByUid(@Param("uid") int uid);
    
}
