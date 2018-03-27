package com.auts.backstage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.auts.backstage.model.dao.product.ProductModel;


/**
 * 产品表方法入口
 * @author huangrongwei
 *
 */
public interface ProductsMapper {
	
    @Insert("insert into Product (p_code, p_short_name,p_full_name,p_type,p_expect_annual_revenue,p_sale_status,"
    		+ "p_due_time, p_sale_date_start, p_all_issuing_scale, p_min_amount,p_payment_interest_type, "
    		+" p_invest_type, p_size_ratio_type, p_invest_owner_id,p_recruitment_summary, p_all_subscription_amount,p_latest_Pay_Num,"
    		+ "p_cpys,p_mjzh,p_fxkz,p_hkly,p_zjyt,p_rzf,p_dbf, p_tgjg, p_rgxz, "
    		+ "p_rexiao, p_tuijian, p_commission, p_create_time,p_update_time) "
            + "values (#{pm.pCode},#{pm.pShortName}, #{pm.pFullName},#{pm.pType},#{pm.pExpectAnnualRevenue},#{pm.pSaleStatus},"
            + "#{pm.pDulTime}, #{pm.pSaleStartTime},#{pm.pAllIssuingScale},#{pm.pMinAmount}, #{pm.pPaymentInterestType},"
            +" #{pm.pInvestType}, #{pm.pSizeRatioType},#{pm.pInvestOwnerId},#{pm.pRecruitmentSummary},#{pm.pAllSubscriptionAmount},#{pm.latestPayNum},"
            +" #{pm.pCpys}, #{pm.pMjzh},#{pm.pFxkz}, #{pm.pHkly},#{pm.pZjyt}, #{pm.pRzf},#{pm.pDbf},#{pm.pTgjg},#{pm.pRgxz}, "
            + "#{pm.pRexiao},#{pm.pTuijian},#{pm.pCommission},#{pm.createTime},#{pm.updateTime})")
    int savaProduct(@Param("pm") ProductModel pm);
    
    @Update("update Product set p_short_name=#{pm.pShortName}, p_full_name=#{pm.pFullName}, p_expect_annual_revenue=#{pm.pExpectAnnualRevenue}, "
    		+ "p_sale_status=#{pm.pSaleStatus}, p_due_time=#{pm.pDulTime}, p_sale_date_start=#{pm.pSaleStartTime}, p_all_issuing_scale=#{pm.pAllIssuingScale}, "
    		+ "p_payment_interest_type=#{pm.pPaymentInterestType}, p_invest_type=#{pm.pInvestType}, p_size_ratio_type=#{pm.pSizeRatioType}, "
    		+ "p_invest_owner_id=#{pm.pInvestOwnerId}, p_recruitment_summary=#{pm.pRecruitmentSummary}, p_latest_Pay_Num=#{pm.latestPayNum}, "
    		+ "p_cpys=#{pm.pCpys}, p_mjzh=#{pm.pMjzh}, p_fxkz=#{pm.pFxkz}, p_hkly=#{pm.pHkly}, p_zjyt=#{pm.pZjyt}, p_rzf=#{pm.pRzf}, p_dbf=#{pm.pDbf},"
    		+ "p_tgjg=#{pm.pTgjg}, p_rgxz=#{pm.pRgxz}, p_rexiao=#{pm.pRexiao}, p_tuijian=#{pm.pTuijian}, p_commission=#{pm.pCommission}, p_update_time=#{pm.updateTime} "
    		+ " where p_id = #{pm.id}")
    int updateProduct(@Param("pm") ProductModel pm);
    
	
	@Select("select count(*) num from Product where p_type= #{pType}")
    int queryCountByPType(@Param("pType") String pType);
	
    @Select("select * from Product where p_code=#{pCode} limit 1")
    @Results({
    	@Result(property = "id", column = "p_id"), @Result(property = "pCode", column = "p_code"),
    	@Result(property = "pShortName", column = "p_short_name"), @Result(property = "pFullName", column = "p_full_name"),
    	@Result(property = "pType", column = "p_type"), @Result(property = "pExpectAnnualRevenue", column = "p_expect_annual_revenue"),
    	@Result(property = "pSaleStatus", column = "p_sale_status"), @Result(property = "pDulTime", column = "p_due_time"),
    	@Result(property = "pSaleStartTime", column = "p_sale_date_start"), @Result(property = "pAllIssuingScale", column = "p_all_issuing_scale"),
    	@Result(property = "pMinAmount", column = "p_min_amount"), @Result(property = "pPaymentInterestType", column = "p_payment_interest_type"),
    	@Result(property = "pInvestType", column = "p_invest_type"), @Result(property = "pSizeRatioType", column = "p_size_ratio_type"),
    	@Result(property = "pInvestOwnerId", column = "p_invest_owner_id"), @Result(property = "pIssuingScale", column = "p_issuing_scale"),
    	@Result(property = "pExpectSaleAmount", column = "p_expect_sale_amount"), @Result(property = "pAllSubscriptionAmount", column = "p_all_subscription_amount"),
    	@Result(property = "pRecruitmentSummary", column = "p_recruitment_summary"), @Result(property = "pCpys", column = "p_cpys"),
    	@Result(property = "pMjzh", column = "p_mjzh"), @Result(property = "pFxkz", column = "p_fxkz"),
    	@Result(property = "pHkly", column = "p_hkly"), @Result(property = "pZjyt", column = "p_zjyt"),
    	@Result(property = "pRzf", column = "p_rzf"), @Result(property = "pDbf", column = "p_dbf"),
		@Result(property = "pLatestPayNum", column = "p_latest_Pay_Num"), @Result(property = "pRgxz", column = "p_rgxz"),
    	@Result(property = "pRexiao", column = "p_rexiao"), @Result(property = "pTuijian", column = "p_tuijian"),
    	@Result(property = "pCommission", column = "p_commission")
    })
    ProductModel queryProductByPCode(@Param("pCode") String pCode);

    /**
     */
//    @Select("select * from Product where p_type= #{pType}")
    @Select("<script>"
    		+ "select * from Product "
    		+ "<if test='pType !=null and pType !=\"00\" '>"
    		+ "  where p_type= #{pType}"
    		+ "</if> "
			+"</script>")
    @Results({
    	@Result(property = "id", column = "p_id"), @Result(property = "pCode", column = "p_code"),
    	@Result(property = "pShortName", column = "p_short_name"), @Result(property = "pFullName", column = "p_full_name"),
    	@Result(property = "pType", column = "p_type"), @Result(property = "pExpectAnnualRevenue", column = "p_expect_annual_revenue"),
    	@Result(property = "pSaleStatus", column = "p_sale_status"), @Result(property = "pDulTime", column = "p_due_time"),
    	@Result(property = "pSaleStartTime", column = "p_sale_date_start"), @Result(property = "pAllIssuingScale", column = "p_all_issuing_scale"),
    	@Result(property = "pMinAmount", column = "p_min_amount"), @Result(property = "pPaymentInterestType", column = "p_payment_interest_type"),
    	@Result(property = "pInvestType", column = "p_invest_type"), @Result(property = "pSizeRatioType", column = "p_size_ratio_type"),
    	@Result(property = "pInvestOwnerId", column = "p_invest_owner_id"), @Result(property = "pIssuingScale", column = "p_issuing_scale"),
    	@Result(property = "pExpectSaleAmount", column = "p_expect_sale_amount"), @Result(property = "pAllSubscriptionAmount", column = "p_all_subscription_amount"),
    	@Result(property = "pRecruitmentSummary", column = "p_recruitment_summary"), @Result(property = "pCpys", column = "p_cpys"),
    	@Result(property = "pMjzh", column = "p_mjzh"), @Result(property = "pFxkz", column = "p_fxkz"),
    	@Result(property = "pHkly", column = "p_hkly"), @Result(property = "pZjyt", column = "p_zjyt"),
    	@Result(property = "pRzf", column = "p_rzf"), @Result(property = "pDbf", column = "p_dbf"),
		@Result(property = "pLatestPayNum", column = "p_latest_Pay_Num"), @Result(property = "pRgxz", column = "p_rgxz"),
    	@Result(property = "pRexiao", column = "p_rexiao"), @Result(property = "pTuijian", column = "p_tuijian"),
    	@Result(property = "pCommission", column = "p_commission")
    })
    List<ProductModel> queryProductList(@Param("pType") String pType);
    
    @Select("select * from Product where 1= 1 limit 4")
    @Results({
    	@Result(property = "id", column = "p_id"), @Result(property = "pCode", column = "p_code"),
    	@Result(property = "pShortName", column = "p_short_name"), @Result(property = "pFullName", column = "p_full_name"),
    	@Result(property = "pType", column = "p_type"), @Result(property = "pExpectAnnualRevenue", column = "p_expect_annual_revenue"),
    	@Result(property = "pSaleStatus", column = "p_sale_status"), @Result(property = "pDulTime", column = "p_due_time"),
    	@Result(property = "pSaleStartTime", column = "p_sale_date_start"), @Result(property = "pAllIssuingScale", column = "p_all_issuing_scale"),
    	@Result(property = "pMinAmount", column = "p_min_amount"), @Result(property = "pPaymentInterestType", column = "p_payment_interest_type"),
    	@Result(property = "pInvestType", column = "p_invest_type"), @Result(property = "pSizeRatioType", column = "p_size_ratio_type"),
    	@Result(property = "pInvestOwnerId", column = "p_invest_owner_id"), @Result(property = "pIssuingScale", column = "p_issuing_scale"),
    	@Result(property = "pExpectSaleAmount", column = "p_expect_sale_amount"), @Result(property = "pAllSubscriptionAmount", column = "p_all_subscription_amount"),
    	@Result(property = "pRecruitmentSummary", column = "p_recruitment_summary"), @Result(property = "pCpys", column = "p_cpys"),
    	@Result(property = "pMjzh", column = "p_mjzh"), @Result(property = "pFxkz", column = "p_fxkz"),
    	@Result(property = "pHkly", column = "p_hkly"), @Result(property = "pZjyt", column = "p_zjyt"),
    	@Result(property = "pRzf", column = "p_rzf"), @Result(property = "pDbf", column = "p_dbf"),
		@Result(property = "pLatestPayNum", column = "p_latest_Pay_Num"), @Result(property = "pRgxz", column = "p_rgxz"),
    	@Result(property = "pRexiao", column = "p_rexiao"), @Result(property = "pTuijian", column = "p_tuijian"),
    	@Result(property = "pCommission", column = "p_commission")
    })
    List<ProductModel> queryRecommendProducts(String recommendype);
}
