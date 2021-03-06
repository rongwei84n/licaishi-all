package com.auts.lcs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.auts.lcs.model.dao.product.ProfitRebateModel;

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
    	@Result(property = "prExpectAnnualRevenue", column = "pr_expect_annual_revenue"),
    	@Result(property = "prCommission", column = "pr_commission")
    })
    List<ProfitRebateModel> queryProfitRebateByPCode(@Param("pCode") String pCode);

    @Insert("insert into ProfitRebate (pr_id, pr_product_code, pr_start_amount,"
    		+ "pr_end_amount, pr_amount_display, pr_expect_annual_revenue, pr_commission) "
            + "values (#{pr.id}, #{pr.prProductCode},#{pr.prStartAmount}, "
            + "#{pr.prEndAmount}, #{pr.prAmountDisplay},#{pr.prExpectSnnualRevenue},#{pr.prCommission})")
    int savaProfitRebate(ProfitRebateModel pr);
}
