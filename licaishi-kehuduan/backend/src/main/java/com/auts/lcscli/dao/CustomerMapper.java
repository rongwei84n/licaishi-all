package com.auts.lcscli.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.auts.lcscli.model.dao.CustomerModel;


public interface CustomerMapper {
	
    @Select("select count(*) num from tbl_customer where financerId = #{financerId} limit 1 ")
    int queryCustomerCountByFuid(@Param("financerId") String financerId);
	
	@Select("select * from tbl_customer where financerId = #{financerId} limit #{startIndex}, #{pageSize}")
    @Results({
    	@Result(property = "uid", column = "uid"), @Result(property = "userId", column = "userId"),
    	@Result(property = "financerId", column = "financerId"),
    	@Result(property = "name", column = "name"), @Result(property = "email", column = "email"),
    	@Result(property = "address", column = "address"), @Result(property = "createtime", column = "createtime"),
    	@Result(property = "updatetime", column = "updatetime"), @Result(property = "phone", column = "phone"),
    	@Result(property = "sex", column = "sex"),@Result(property = "birthday", column = "birthday")
    })
    List<CustomerModel> queryCustomerByFUID(@Param("startIndex")int startIndex, @Param("pageSize")int pageSize, @Param("financerId") String financerId);
	
	@Select("select * from tbl_customer where uid = #{uid}")
    @Results({
    	@Result(property = "uid", column = "uid"), @Result(property = "userId", column = "userId"),
    	@Result(property = "financerId", column = "financerId"),
    	@Result(property = "name", column = "name"), @Result(property = "email", column = "email"),
    	@Result(property = "address", column = "address"), @Result(property = "createtime", column = "createtime"),
    	@Result(property = "updatetime", column = "updatetime"), @Result(property = "phone", column = "phone"),
    	@Result(property = "sex", column = "sex"),@Result(property = "birthday", column = "birthday")
    })
	CustomerModel queryCustomerByUid(@Param("uid") String uid);
	
	@Select("select * from tbl_customer where financerId = #{financerId}")
    @Results({
    	@Result(property = "uid", column = "uid"), @Result(property = "userId", column = "userId"),
    	@Result(property = "financerId", column = "financerId"),
    	@Result(property = "name", column = "name"), @Result(property = "email", column = "email"),
    	@Result(property = "address", column = "address"), @Result(property = "createtime", column = "createtime"),
    	@Result(property = "updatetime", column = "updatetime"), @Result(property = "phone", column = "phone"),
    	@Result(property = "sex", column = "sex"),@Result(property = "birthday", column = "birthday")
    })
    List<CustomerModel> queryCustomerForOrder(@Param("financerId") String financerId);
	
	@Insert("insert into tbl_customer (userId,name,phone,email,address,sex,birthday, createtime,updatetime,financerId) values "
			+ "(#{customer.userId},#{customer.name},#{customer.phone},#{customer.email},#{customer.address},"
			+ "#{customer.sex},#{customer.birthday},#{customer.createtime},#{customer.updatetime},#{customer.financerId})")
	void addCustomer(@Param("customer") CustomerModel customer);

	@Delete("delete from tbl_customer where uid = #{uid}")
	void delCustomer(@Param("uid") String uid);

//	@Update("update tbl_financer set name=#{financer.name},sex=#{financer.sex},birthday=#{financer.birthday},phone=#{financer.phone},email=#{financer.email},address=#{financer.address}"
//			+ " where uid=#{financer.uid}")
//	void editFinancer(@Param("financer") FinancerModel financer);

//	@Select("<script>"
//			+ "select count(*) from tbl_financer "
//			+ "<if test='nameSearch != null'>"
//			+ 	"where name like concat('%',#{nameSearch},'%')"
//			+ "</if>"
//			+ "order by uid"
//		  + "</script>")
//	int queryFinancerCount(@Param("nameSearch") String nameSearch);
}