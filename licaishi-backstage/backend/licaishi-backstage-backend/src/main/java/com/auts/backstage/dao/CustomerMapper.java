package com.auts.backstage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.auts.backstage.model.dao.CustomerModel;

/**
 * @author ranshao2017
 */
public interface CustomerMapper {

	@Insert("insert into tbl_customer (name,phone,email,address,createtime,updatetime,sex,birthday,userId,financerId) values "
			+ "(#{customer.name},#{customer.phone},#{customer.email},#{customer.address},#{customer.createtime},#{customer.updatetime},#{customer.sex},#{customer.birthday},#{customer.userId},#{customer.financerId})")
	void addCustomer(@Param("customer") CustomerModel customer);

	@Select("<script>"
			+ "select c.*,f.name as financer,u.status from tbl_customer c "
			+ "LEFT JOIN tbl_financer f ON c.financerId = f.uid "
			+ "left join tbl_user u on u.uid = c.userId "
			+ "<if test='nameSearch != \"\"'>"
			+ 	"where c.name like concat('%',#{nameSearch},'%')"
			+ "</if>"
			+ "order by c.uid"
		  + "</script>")
	List<CustomerModel> queryFinancerList(@Param("nameSearch") String nameSearch);

	@Select("<script>"
			+ "select count(*) from tbl_customer "
			+ "<if test='nameSearch != \"\"'>"
			+ 	"where name like concat('%',#{nameSearch},'%')"
			+ "</if>"
		  + "</script>")
	int queryFinancerCount(@Param("nameSearch") String nameSearch);

	@Update("update tbl_customer set name=#{customer.name},sex=#{customer.sex},birthday=#{customer.birthday},phone=#{customer.phone},email=#{customer.email},address=#{customer.address},financerId=#{customer.financerId}"
			+ " where uid=#{customer.uid}")
	void editFinancer(@Param("customer") CustomerModel customer);

	@Select("select * from tbl_customer where uid = #{uid}")
	CustomerModel queryCustomer(@Param("uid") String uid);

	@Delete("delete from tbl_customer where uid = #{uid}")
	void delCustomer(@Param("uid") String uid);

	@Delete("<script>"
			  + "delete from tbl_customer where uid in "
			  + "<foreach item='item' index='index' collection='uids' open='(' separator=',' close=')'>"
			  + "#{item}"
			  + "</foreach>"	
		  + "</script>")
	void btrvCustomer(@Param("uids") List<Integer> uids);

}