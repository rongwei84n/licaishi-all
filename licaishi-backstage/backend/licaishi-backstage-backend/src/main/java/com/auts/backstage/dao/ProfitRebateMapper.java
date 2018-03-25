package com.auts.backstage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.auts.backstage.model.dao.product.ProfitRebateModel;

/**
 * 利润收益比例
 * 
 * @author lb
 *
 */
public interface ProfitRebateMapper {


    @Select("select * from ProfitRebate where pr_product_code=#{pCode}")
    @Results({
    	@Result(property = "id", column = "pr_id"), 
    	@Result(property = "prProductCode", column = "pr_product_code"), 
    	@Result(property = "prStartAmount", column = "pr_start_amount"),
    	@Result(property = "prEndAmount", column = "pr_end_amount"), 
    	@Result(property = "prAmountDisplay", column = "pr_amount_display"), 
    	@Result(property = "prExpectSnnualRevenue", column = "pr_expect_annual_revenue"), 
    	@Result(property = "prCommission", column = "pr_commission")
    })
    List<ProfitRebateModel> queryProfitRebateByPCode(@Param("pCode") String pCode);
    
    @Insert("insert into ProfitRebate (pr_product_code, pr_start_amount,"
    		+ "pr_end_amount, pr_amount_display, pr_expect_annual_revenue, pr_commission,pr_create_time,pr_update_time) "
            + "values (#{pr.prProductCode},#{pr.prStartAmount}, "
            + "#{pr.prEndAmount}, #{pr.prAmountDisplay},#{pr.prExpectAnnualRevenue},#{pr.prCommission},"
            + "#{pr.prCreateTime},#{pr.prUpdateTime})")
    int savaProfitRebate(@Param("pr") ProfitRebateModel pr);
    
    
	@Delete("delete from ProfitRebate where pr_product_code=#{pCode}")
	int delProfitRebateByPCode(@Param("pCode") String pCode);
}
