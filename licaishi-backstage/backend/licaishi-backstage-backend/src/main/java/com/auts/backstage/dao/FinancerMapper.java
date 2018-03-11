package com.auts.backstage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.auts.backstage.model.dao.FinancerModel;

public interface FinancerMapper {
	
	@Select("<script>"
			+ "select * from tbl_financer "
			+ "<if test='nameSearch != \"\"'>"
			+ 	"where name like concat('%',#{nameSearch},'%')"
			+ "</if>"
			+ "order by uid"
		  + "</script>")
    List<FinancerModel> queryFinancerList(@Param("nameSearch") String nameSearch);

	@Insert("insert into tbl_financer (name,phone,email,address,createtime,updatetime,sex,birthday) values "
			+ "(#{financer.name},#{financer.phone},#{financer.email},#{financer.address},#{financer.createtime},#{financer.updatetime},#{financer.sex},#{financer.birthday})")
	void addFinancer(@Param("financer") FinancerModel financer);

	@Delete("delete from tbl_financer where uid = #{uid}")
	void delFinancer(@Param("uid") String uid);

	@Delete("<script>"
			  + "delete from tbl_financer where uid in "
			  + "<foreach item='item' index='index' collection='uids' open='(' separator=',' close=')'>"
			  + "#{item}"
			  + "</foreach>"	
		  + "</script>")
	void btrvFinancer(@Param("uids") List<Integer> uids);

	@Update("update tbl_financer set name=#{financer.name},sex=#{financer.sex},birthday=#{financer.birthday},phone=#{financer.phone},email=#{financer.email},address=#{financer.address}"
			+ " where uid=#{financer.uid}")
	void editFinancer(@Param("financer") FinancerModel financer);

	@Select("<script>"
			+ "select count(*) from tbl_financer "
			+ "<if test='nameSearch != \"\"'>"
			+ 	"where name like concat('%',#{nameSearch},'%')"
			+ "</if>"
			+ "order by uid"
		  + "</script>")
	int queryFinancerCount(@Param("nameSearch") String nameSearch);
}