package com.auts.lcs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import com.auts.lcs.model.dao.product.ProductModel;


/**
 * 产品表方法入口
 * @author huangrongwei
 *
 */
public interface ProductsMapper {

    @Insert("insert into Product (p_id, p_code, p_short_name,p_full_name,p_type,p_expect_annual_revenue,p_sale_status"
    		+ "p_due_time, p_sale_date_start, p_all_issuing_scale, p_min_amount,p_payment_interest_type, "
    		+" p_invest_type, p_size_ratio_type, p_invest_owner_id) "
            + "values (#{pm.id}, #{pm.pCode},#{pm.pShortName}, #{pm.pFullName},#{pm.pType},#{pm.pExpectAnnualRevenue},#{pm.pSaleStatus},"
            + "#{pm.pDulTime}, #{pm.pSaleStartTime},#{pm.pAllIssuingScale},#{pm.pMinAmount}, #{pm.pPaymentInterestType},"
            +" #{pm.pInvestType}, #{pm.pSizeRatioType},#{pm.pInvestOwnerId})")
    @Options(useGeneratedKeys = true, keyProperty = "p_id")
    int savaProduct(ProductModel pm);

    @UpdateProvider(type = ProductSqlProvider.class, method = "updateSql")
    int updateProduct(@Param("product") ProductModel pm);

//    @Select("select count(*) num from Product where p_type = #{type} limit 1 ")
//	@Select("select count(*) num from Product where p_type= #{type} limit 1")
    @Select("<script>"
    		+ "select count(*) num from Product where p_type= #{type} "
    		+ "<if test='pInvestType !=null and pInvestType !=\"00\"'>"
    		+ "  and p_invest_type = #{pInvestType} "
    		+ "</if> "
    		+ "<if test='pPaymentInterestType !=null and pPaymentInterestType !=\"00\" '>"
    		+ "  and p_payment_interest_type = #{pPaymentInterestType} "
    		+ "</if> "
    		+ "<if test='pSizeRatioType !=null and pSizeRatioType !=\"00\" '>"
    		+ "  and p_size_ratio_type = #{pSizeRatioType} "
    		+ "</if> "
    		+ "<if test='minimumAmount !=null and minimumAmount !=\"00\" '>"
    		+ "  and p_min_amount >= #{minimumAmount} "
    		+ "</if> "
    		+ "<if test='dueTime !=null and dueTime !=\"00\" '>"
    		+    "<choose>"
    		+      	"<when test='dueTime ==\"01\"'>"
    		+			"and p_due_time &lt;= 11"
    		+      	"</when>"
    		+      	"<when test='dueTime ==\"02\"'>"
    		+			"and p_due_time = 12"
    		+      	"</when>"
    		+      	"<when test='dueTime ==\"03\"'>"
    		+			"and 13&gt;= p_due_time &lt;=23"
    		+      	"</when>"
    		+      	"<when test='dueTime ==\"04\"'>"
    		+			"and p_due_time = 24"
    		+      	"</when>"
    		+      	"<when test='dueTime ==\"05\"'>"
    		+			"and p_due_time &gt;  24"
    		+      	"</when>"
    		+    "</choose>"
    		+ "</if> "
    		+ "<if test='annualRevenue !=null and annualRevenue !=\"00\" '>"
    		+    "<choose>"
    		+      	"<when test='annualRevenue ==\"01\"'>"
    		+			"and p_expect_annual_revenue + 0  &lt; '7' + 0 "
    		+      	"</when>"
    		+      	"<when test='annualRevenue ==\"02\"'>"
    		+			" and '7' + 0 &gt;= p_expect_annual_revenue + 0 "
 		    + 			" and p_expect_annual_revenue + 0 &lt; '8' + 0 "
    		+      	"</when>"
    		+      	"<when test='annualRevenue ==\"03\"'>"
    		+			" and '8' + 0 &gt;= p_expect_annual_revenue + 0  "
    		+ 			" and p_expect_annual_revenue + 0 &lt; '9' + 0 "
    		+      	"</when>"
    		+      	"<when test='annualRevenue ==\"04\"'>"
    		+			" and '9' + 0 &gt;= p_expect_annual_revenue + 0  "
    		+ 			" and p_expect_annual_revenue + 0 &lt; '10' + 0 "
    		+      	"</when>"
    		+      	"<when test='annualRevenue ==\"05\"'>"
    		+			"and p_expect_annual_revenue + 0 &gt;= '10' + 0 "
    		+      	"</when>"
    		+      	"<when test='annualRevenue ==\"06\"'>"
    		+			"and p_expect_annual_revenue = '浮动' "
    		+      	"</when>"
    		+    "</choose>"
    		+ "</if> "
    		+ "<if test='pCommission !=null and pCommission !=\"00\" '>"
    		+    "<choose>"
    		+      	"<when test='pCommission ==\"01\"'>"
    		+			"and p_commission + 0 &lt; '1' + 0 "
    		+      	"</when>"
    		+      	"<when test='pCommission ==\"02\"'>"
    		+			"and '1' + 0 &gt;= p_commission + 0 "
    		+ 			" and p_commission + 0 &lt; '3' + 0 "
    		+      	"</when>"
    		+      	"<when test='pCommission ==\"03\"'>"
    		+			"and p_commission + '0 &gt;= '3' + 0 "
    		+      	"</when>"
    		+    "</choose>"
    		+ "</if> "
    		+ "<if test='saleStatus !=null and saleStatus !=\"00\" '>"
    		+ "  and p_sale_status = #{saleStatus} "
    		+ "</if> "
    		+ "<if test='pRabateProfitParameter !=null and pRabateProfitParameter ==\"true\"'>"
    		+ "  order by p_commission + '0' desc "
    		+ "</if> "
    		+ "<if test='pAnnualRevenueExpectParameter !=null and pAnnualRevenueExpectParameter ==\"true\"'>"
    		+ "  order by p_expect_annual_revenue + '0' desc "
    		+ "</if> "
			+"</script>")
    int queryCountByPType(@Param("type")String type,
    		@Param("pInvestType")String pInvestType, @Param("pPaymentInterestType")String pPaymentInterestType, @Param("pSizeRatioType")String pSizeRatioType,
    		@Param("minimumAmount")String minimumAmount, @Param("dueTime")String dueTime, @Param("annualRevenue")String annualRevenue,
    		@Param("saleStatus")String saleStatus, @Param("pRabateProfitParameter")String pRabateProfitParameter,
    		@Param("pAnnualRevenueExpectParameter")String pAnnualRevenueExpectParameter,@Param("pCommission")String pCommission);

	@Select("select count(*) num from Product limit 1")
    int queryAllCount();

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

    @Select("select * from Product where p_id=#{pid} limit 1")
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
    ProductModel queryProductByPid(@Param("pid") String pid);

    @Select("<script>"
    		+ "select * from Product where p_type= #{type} "
    		+ "<if test='pInvestType !=null and pInvestType !=\"00\"'>"
    		+ "  and p_invest_type = #{pInvestType} "
    		+ "</if> "
    		+ "<if test='pPaymentInterestType !=null and pPaymentInterestType !=\"00\" '>"
    		+ "  and p_payment_interest_type = #{pPaymentInterestType} "
    		+ "</if> "
    		+ "<if test='pSizeRatioType !=null and pSizeRatioType !=\"00\" '>"
    		+ "  and p_size_ratio_type = #{pSizeRatioType} "
    		+ "</if> "
    		+ "<if test='minimumAmount !=null and minimumAmount !=\"00\" '>"
    		+ "  and p_min_amount >= #{minimumAmount} "
    		+ "</if> "
    		+ "<if test='dueTime !=null and dueTime !=\"00\" '>"
    		+    "<choose>"
    		+      	"<when test='dueTime ==\"01\"'>"
    		+			"and p_due_time &lt;= 11"
    		+      	"</when>"
    		+      	"<when test='dueTime ==\"02\"'>"
    		+			"and p_due_time = 12"
    		+      	"</when>"
    		+      	"<when test='dueTime ==\"03\"'>"
    		+			"and 13&gt;= p_due_time &lt;=23"
    		+      	"</when>"
    		+      	"<when test='dueTime ==\"04\"'>"
    		+			"and p_due_time = 24"
    		+      	"</when>"
    		+      	"<when test='dueTime ==\"05\"'>"
    		+			"and p_due_time &gt;  24"
    		+      	"</when>"
    		+    "</choose>"
    		+ "</if> "
    		+ "<if test='annualRevenue !=null and annualRevenue !=\"00\" '>"
    		+    "<choose>"
    		+      	"<when test='annualRevenue ==\"01\"'>"
    		+			"and p_expect_annual_revenue + 0  &lt; '7' + 0 "
    		+      	"</when>"
    		+      	"<when test='annualRevenue ==\"02\"'>"
    		+			" and '7' + 0 &gt;= p_expect_annual_revenue + 0 "
 		    + 			" and p_expect_annual_revenue + 0 &lt; '8' + 0 "
    		+      	"</when>"
    		+      	"<when test='annualRevenue ==\"03\"'>"
    		+			" and '8' + 0 &gt;= p_expect_annual_revenue + 0  "
    		+ 			" and p_expect_annual_revenue + 0 &lt; '9' + 0 "
    		+      	"</when>"
    		+      	"<when test='annualRevenue ==\"04\"'>"
    		+			" and '9' + 0 &gt;= p_expect_annual_revenue + 0  "
    		+ 			" and p_expect_annual_revenue + 0 &lt; '10' + 0 "
    		+      	"</when>"
    		+      	"<when test='annualRevenue ==\"05\"'>"
    		+			"and p_expect_annual_revenue + 0 &gt;= '10' + 0 "
    		+      	"</when>"
    		+      	"<when test='annualRevenue ==\"06\"'>"
    		+			"and p_expect_annual_revenue = '浮动' "
    		+      	"</when>"
    		+    "</choose>"
    		+ "</if> "
    		+ "<if test='pCommission !=null and pCommission !=\"00\" '>"
    		+    "<choose>"
    		+      	"<when test='pCommission ==\"01\"'>"
    		+			"and p_commission + 0 &lt; '1' + 0 "
    		+      	"</when>"
    		+      	"<when test='pCommission ==\"02\"'>"
    		+			"and '1' + 0 &gt;= p_commission + 0 "
    		+ 			" and p_commission + 0 &lt; '3' + 0 "
    		+      	"</when>"
    		+      	"<when test='pCommission ==\"03\"'>"
    		+			"and p_commission + '0 &gt;= '3' + 0 "
    		+      	"</when>"
    		+    "</choose>"
    		+ "</if> "
    		+ "<if test='saleStatus !=null and saleStatus !=\"00\" '>"
    		+ "  and p_sale_status = #{saleStatus} "
    		+ "</if> "
    		+ "<if test='pRabateProfitParameter !=null and pRabateProfitParameter ==\"true\"'>"
    		+ "  order by p_commission + '0' desc "
    		+ "</if> "
    		+ "<if test='pAnnualRevenueExpectParameter !=null and pAnnualRevenueExpectParameter ==\"true\"'>"
    		+ "  order by p_expect_annual_revenue + '0' desc "
    		+ "</if> "
    		+ "limit #{startIndex}, #{pageSize}"
			+"</script>")
    @Results({
    	@Result(property = "id", column = "p_id"), @Result(property = "pCode", column = "p_code"),
    	@Result(property = "pShortName", column = "p_short_name"), @Result(property = "pFullName", column = "p_full_name"),
    	@Result(property = "pType", column = "p_type"), @Result(property = "pExpectAnnualRevenue", column = "p_expect_annual_revenue"),
    	@Result(property = "pSaleStatus", column = "p_sale_status"), @Result(property = "pDulTime", column = "p_due_time"),
    	@Result(property = "pSaleStartTime", column = "p_sale_date_start"), @Result(property = "pAllIssuingScale", column = "p_all_issuing_scale"),
    	@Result(property = "pMinAmount", column = "p_min_amount"), @Result(property = "pPaymentInterestType", column = "p_payment_interest_type"),
    	@Result(property = "pInvestType", column = "p_invest_type"), @Result(property = "pSizeRatioType", column = "p_size_ratio_type"),
    	@Result(property = "pInvestOwnerId", column = "p_invest_owner_id"), @Result(property = "pAllSubscriptionAmount", column = "p_all_subscription_amount"),
		@Result(property = "pLatestPayNum", column = "p_latest_Pay_Num"), @Result(property = "pRgxz", column = "p_rgxz"),
    	@Result(property = "pRexiao", column = "p_rexiao"), @Result(property = "pTuijian", column = "p_tuijian"),
    	@Result(property = "pCommission", column = "p_commission")
    })
    List<ProductModel> queryProductsByType(@Param("startIndex")int startIndex, @Param("pageSize") int pageSize, @Param("type")String type,
    		@Param("pInvestType")String pInvestType, @Param("pPaymentInterestType")String pPaymentInterestType, @Param("pSizeRatioType")String pSizeRatioType,
    		@Param("minimumAmount")String minimumAmount, @Param("dueTime")String dueTime, @Param("annualRevenue")String annualRevenue,
    		@Param("saleStatus")String saleStatus, @Param("pRabateProfitParameter")String pRabateProfitParameter,
    		@Param("pAnnualRevenueExpectParameter")String pAnnualRevenueExpectParameter,@Param("pCommission")String pCommission);

    @Select("select * from Product limit #{startIndex}, #{pageSize}")
    @Results({
    	@Result(property = "id", column = "p_id"), @Result(property = "pCode", column = "p_code"),
    	@Result(property = "pShortName", column = "p_short_name"), @Result(property = "pFullName", column = "p_full_name"),
    	@Result(property = "pType", column = "p_type"), @Result(property = "pExpectAnnualRevenue", column = "p_expect_annual_revenue"),
    	@Result(property = "pSaleStatus", column = "p_sale_status"), @Result(property = "pDulTime", column = "p_due_time"),
    	@Result(property = "pSaleStartTime", column = "p_sale_date_start"), @Result(property = "pAllIssuingScale", column = "p_all_issuing_scale"),
    	@Result(property = "pMinAmount", column = "p_min_amount"), @Result(property = "pPaymentInterestType", column = "p_payment_interest_type"),
    	@Result(property = "pInvestType", column = "p_invest_type"), @Result(property = "pSizeRatioType", column = "p_size_ratio_type"),
    	@Result(property = "pInvestOwnerId", column = "p_invest_owner_id"), @Result(property = "pAllSubscriptionAmount", column = "p_all_subscription_amount"),
		@Result(property = "pLatestPayNum", column = "p_latest_Pay_Num"), @Result(property = "pRgxz", column = "p_rgxz"),
    	@Result(property = "pRexiao", column = "p_rexiao"), @Result(property = "pTuijian", column = "p_tuijian"),
    	@Result(property = "pCommission", column = "p_commission")
    })
    List<ProductModel> queryAllProducts(@Param("startIndex")int startIndex, @Param("pageSize")int pageSize);


    @Select("select * from Product where 1= 1 limit 4")
    @Results({
    	@Result(property = "id", column = "p_id"), @Result(property = "pCode", column = "p_code"),
    	@Result(property = "pShortName", column = "p_short_name"), @Result(property = "pFullName", column = "p_full_name"),
    	@Result(property = "pType", column = "p_type"), @Result(property = "pExpectAnnualRevenue", column = "p_expect_annual_revenue"),
    	@Result(property = "pSaleStatus", column = "p_sale_status"), @Result(property = "pDulTime", column = "p_due_time"),
    	@Result(property = "pSaleStartTime", column = "p_sale_date_start"), @Result(property = "pAllIssuingScale", column = "p_all_issuing_scale"),
    	@Result(property = "pMinAmount", column = "p_min_amount"), @Result(property = "pPaymentInterestType", column = "p_payment_interest_type"),
    	@Result(property = "pInvestType", column = "p_invest_type"), @Result(property = "pSizeRatioType", column = "p_size_ratio_type"),
    	@Result(property = "pInvestOwnerId", column = "p_invest_owner_id"), @Result(property = "pAllSubscriptionAmount", column = "p_all_subscription_amount"),
		@Result(property = "pLatestPayNum", column = "p_latest_Pay_Num"), @Result(property = "pRgxz", column = "p_rgxz"),
    	@Result(property = "pRexiao", column = "p_rexiao"), @Result(property = "pTuijian", column = "p_tuijian"),
    	@Result(property = "pCommission", column = "p_commission")
    })
    List<ProductModel> queryRecommendProducts(String recommendype);
}
