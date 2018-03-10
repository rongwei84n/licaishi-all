package com.auts.backstage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.auts.backstage.model.dao.order.OrderModel;

public interface OrderMapper {
	
    @Select("<script>"
    	+ "SELECT "
    		+ "a.*,"
    		+ "c.name financer,"
    		+ "c.phone financer_tel,"
    		+ "d.name customer,"
    		+ "d.phone customer_tel,"
    		+ "e.p_short_name pro_short_nam,"
    		+ "e.p_full_name product,"
    		+ "e.i_full_name inst"
    	+ " FROM "
    		+ "tbl_order a "
    		+ "LEFT JOIN tbl_financer c ON c.uid = a.financer_uid "
    		+ "LEFT JOIN tbl_customer d ON d.uid = a.customer_uid "
    		+ "LEFT JOIN "
    			+ "(SELECT aa.p_id,aa.p_short_name,aa.p_full_name,bb.i_full_name "
    			+ "FROM Product aa,Institution bb WHERE aa.p_invest_owner_id = bb.i_id) e "
    		+ "ON e.p_id = a.product_id "
    	+ "WHERE a.status = #{status} "
    	+ "<if test='startDate != null'>"
		+ 	" and #{startDate} &lt;= a.order_date "
		+ "</if>"
		+ "<if test='endDate != null'>"
		+ 	" and #{endDate} &gt;= a.order_date "
		+ "</if>"
    	+ "</script>")
    @Results({
    	@Result(property = "uid", column = "uid"),@Result(property = "orderNo", column = "order_no"),
    	@Result(property = "amount", column = "amount"),@Result(property = "orderDate", column = "order_date"),
    	@Result(property = "latestPayDate", column = "latest_pay_date"),@Result(property = "financerUid", column = "financer_uid"),
    	@Result(property = "customerUid", column = "customer_uid"),@Result(property = "productId", column = "product_id"),
    	@Result(property = "comRatio", column = "commission_ratio"),@Result(property = "commission", column = "commission"),
    	@Result(property = "proRatio", column = "profit_ratio"),@Result(property = "profit", column = "profit"),
    	@Result(property = "status", column = "status"),@Result(property = "voucherStatus", column = "voucher_status"),
    	@Result(property = "voucherPath", column = "voucher_path"),@Result(property = "contractStatus", column = "contract_status"),
    	@Result(property = "issuingBank", column = "issuing_bank"),@Result(property = "cardNo", column = "card_no"),
    	@Result(property = "createtime", column = "create_time"),@Result(property = "updatetime", column = "update_time"),
    	@Result(property = "financer", column = "financer"),@Result(property = "financerTel", column = "financer_tel"),
    	@Result(property = "customer", column = "customer"),@Result(property = "customerTel", column = "customer_tel"),
    	@Result(property = "proShortNam", column = "pro_short_nam"),@Result(property = "product", column = "product"),
    	@Result(property = "inst", column = "inst")
    })
	List<OrderModel> queryOrders(@Param("status") String status, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Select("<script>"
        	+ "SELECT count(*) FROM tbl_order a "
        	+ "WHERE a.status = #{status} "
        	+ "<if test='startDate != null'>"
    		+ 	" and #{startDate} &lt;= a.order_date "
    		+ "</if>"
    		+ "<if test='endDate != null'>"
    		+ 	" and #{endDate} &gt;= a.order_date "
    		+ "</if>"
        	+ "</script>")
	int queryOrdersCnt(@Param("status") String status, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Update("update tbl_order set status = '120', update_time= NOW() where uid=#{uid}")
	void orderSettle(@Param("uid") String uid);

    @Update("update tbl_order set status = '999', update_time= NOW() where uid=#{uid}")
	void orderFailure(@Param("uid") String uid);

    @Update("update tbl_order set contract_status = '1', update_time= NOW() where uid=#{uid}")
	void orderContract(String uid);

    @Update("update tbl_order set status = '130', update_time= NOW() where uid=#{uid}")
	void orderSettled(String uid);
   
}
