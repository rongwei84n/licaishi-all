package com.auts.lcs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.auts.lcs.model.dao.order.OrderModel;

public interface OrderMapper {
	
	//create_time, update_time, create_user, update_user
	@Insert("insert into tbl_order (order_no, amount, order_date, latest_pay_date, financer_uid, customer_uid, "
			+ "product_id, commission_ratio, commission,profit_ratio,profit, "
			+ "status, voucher_status,voucher_path,contract_status, issuing_bank,card_no,create_time, update_time,) "
            + "values (#{or.orderNo},#{or.amount},#{or.orderDate},#{or.latestPayDate},#{or.financerUid},#{or.customerUid}, "
            + "#{or.productId},#{or.comRatio},#{or.commission}, #{or.proRatio},#{or.profit},"
            + "#{or.status}, #{or.voucherStatus},#{or.voucherPath},#{or.contractStatus},#{or.issueBank},#{or.cardNo}, sysdate(), sysdate())")
	int saveOrder(OrderModel or);
	
	@Update("update tbl_order set status = 999, update_time= NOW() where order_no=#{orderNo}")
	int cancelOrder(@Param("orderNo") String orderNo);

    @Select("<script>"
    		+ "select count(*) num from tbl_order "
    		+ "<where>"
    		+ "<if test='status !=null '>"
    		+ "  status = #{status} "
    		+ "</if> "
    		+ "</where></script>")
//	@Select("select count(*) num from tbl_order where status= #{status} limit 1")
    int queryOrderCountByStatus(@Param("status") String status, @Param("uid") String uid);
    
    @Select("select * from tbl_order where order_no=#{orderNo} limit 1")
    @Results({
    	@Result(property = "id", column = "uid"), @Result(property = "orderNo", column = "order_no"),
    	@Result(property = "amount", column = "amount"), @Result(property = "orderDate", column = "order_date"),
    	@Result(property = "latestPayDate", column = "latest_pay_date"), @Result(property = "financerUid", column = "financer_uid"),
    	@Result(property = "customerUid", column = "customer_uid"), @Result(property = "productId", column = "product_id"), 
    	@Result(property = "comRatio", column = "commission_ratio"), @Result(property = "commission", column = "commission"),
    	@Result(property = "proRatio", column = "profit_ratio"), @Result(property = "profit", column = "profit"),
    	@Result(property = "status", column = "status"), @Result(property = "voucherStatus", column = "voucher_status"),
    	@Result(property = "voucherPath", column = "voucher_path"), @Result(property = "contractStatus", column = "contract_status"),
    	@Result(property = "issueBank", column = "issuing_bank"), @Result(property = "cardNo", column = "card_no"),
    	@Result(property = "createTime", column = "create_time"), @Result(property = "updateTime", column = "update_time")
    })
    OrderModel queryOrderByOrderNo(@Param("orderNo") String orderNo);

//    @Select("<script>select * from tbl_order "
////    		+ "where  <if test=\"uid !=null \">uid = #{uid} </if>"
//    		+ "<where>"
//			+ "<if test='payStatus !=null '>"
//			+ " pay_status = #{payStatus} "
//			+ "</if> "
//			+ "limit #{startIndex}, #{pageSize}"
//			+ "</where></script>")
    @Select("select * from tbl_order")
    @Results({
    	@Result(property = "id", column = "uid"), @Result(property = "orderNo", column = "order_no"),
    	@Result(property = "amount", column = "amount"), @Result(property = "orderDate", column = "order_date"),
    	@Result(property = "latestPayDate", column = "latest_pay_date"), @Result(property = "financerUid", column = "financer_uid"),
    	@Result(property = "customerUid", column = "customer_uid"), @Result(property = "productId", column = "product_id"), 
    	@Result(property = "comRatio", column = "commission_ratio"), @Result(property = "commission", column = "commission"),
    	@Result(property = "proRatio", column = "profit_ratio"), @Result(property = "profit", column = "profit"),
    	@Result(property = "status", column = "status"), @Result(property = "voucherStatus", column = "voucher_status"),
    	@Result(property = "voucherPath", column = "voucher_path"), @Result(property = "contractStatus", column = "contract_status"),
    	@Result(property = "issueBank", column = "issuing_bank"), @Result(property = "cardNo", column = "card_no"),
    	@Result(property = "createTime", column = "create_time"), @Result(property = "updateTime", column = "update_time")
    })
    List<OrderModel> queryOrders(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("payStatus") String status, @Param("uid") String uid);
   
}
