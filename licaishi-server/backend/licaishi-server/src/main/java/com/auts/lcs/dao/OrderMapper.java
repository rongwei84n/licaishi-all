package com.auts.lcs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.auts.lcs.model.dao.order.OrderModel;

public interface OrderMapper {

    /**
     */
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

    /**
     */
    @Select("select * from tbl_order where 1= 1 limit 100")
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
    List<OrderModel> queryOrders(int pageNo, int pageSize, String type);
   
}
