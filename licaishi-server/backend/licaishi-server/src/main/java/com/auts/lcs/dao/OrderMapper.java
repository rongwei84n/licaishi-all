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
	@Insert("insert into tbl_order (id, order_no, amount, order_date, latest_pay_date, customer_name, customer_card_id, "
			+ "customer_phone_num, product_id, product_code, product_type, product_name, commission_ratio, commission, "
			+ "pay_status, photo_status,contract_status, uid) "
            + "values (#{or.id}, #{or.orderNo},#{or.amount},#{or.orderDate},#{or.latestPayDate},#{or.customerName},#{or.customerCardId}, "
            + "#{or.customerPhoneNum}, #{or.productId},#{or.productCode},#{or.productType},#{or.productName},#{or.commissionRatio},#{or.commission}, "
            + "#{or.payStatus}, #{or.photoStatus},#{or.contractStatus},#{or.uid})")
	int saveOrder(OrderModel or);
	
	@Update("update tbl_order set pay_status = 999, update_time= NOW() where order_no=#{orderNo}")
	int cancelOrder(@Param("orderNo") String orderNo);

//    @Select("<script>select count(*) num from tbl_order where uid = #{uid} <if test=\"payStatus !=null \">pay_status = #{payStatus} </if> </script>")
	@Select("select count(*) num from Product where pType= #{payStatus} limit 1")
    int queryOrderCountByStatus(@Param("payStatus") String payStatus, @Param("uid") String uid);
    
    @Select("select * from tbl_order where order_no=#{orderNo} limit 1")
    @Results({
    	@Result(property = "id", column = "id"), @Result(property = "orderNo", column = "order_no"),
    	@Result(property = "amount", column = "amount"), @Result(property = "orderDate", column = "order_date"),
    	@Result(property = "latestPayDate", column = "latest_pay_date"), @Result(property = "customerName", column = "customer_name"),
    	@Result(property = "customerCardId", column = "customer_card_id"), @Result(property = "customerPhoneNum", column = "customer_phone_num"),
    	@Result(property = "productId", column = "product_id"), @Result(property = "productCode", column = "product_code"),
    	@Result(property = "productType", column = "product_type"), @Result(property = "productName", column = "product_name"),
    	@Result(property = "commissionRatio", column = "commission_ratio"), @Result(property = "commission", column = "commission"),
    	@Result(property = "payStatus", column = "pay_status"), @Result(property = "photoStatus", column = "photo_status"),
		@Result(property = "contractStatus", column = "contract_status")
    })
    OrderModel queryOrderByOrderNo(@Param("orderNo") String orderNo);

//    @Select("<script>select * from tbl_order where  <if test=\"uid !=null \">uid = #{uid} </if> <if test=\"payStatus !=null \">pay_status = #{payStatus} </if> limit #{startIndex} #{pageSize}")
    @Select("select * from tbl_order")
    @Results({
    	@Result(property = "id", column = "id"), @Result(property = "orderNo", column = "order_no"),
    	@Result(property = "amount", column = "amount"), @Result(property = "orderDate", column = "order_date"),
    	@Result(property = "latestPayDate", column = "latest_pay_date"), @Result(property = "customerName", column = "customer_name"),
    	@Result(property = "customerCardId", column = "customer_card_id"), @Result(property = "customerPhoneNum", column = "customer_phone_num"),
    	@Result(property = "productId", column = "product_id"), @Result(property = "productCode", column = "product_code"),
    	@Result(property = "productType", column = "product_type"), @Result(property = "productName", column = "product_name"),
    	@Result(property = "commissionRatio", column = "commission_ratio"), @Result(property = "commission", column = "commission"),
    	@Result(property = "payStatus", column = "pay_status"), @Result(property = "photoStatus", column = "photo_status"),
		@Result(property = "contractStatus", column = "contract_status")
    })
    List<OrderModel> queryOrders(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("payStatus") String status, @Param("uid") String uid);
   
}
