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
			+ "status, voucher_status,voucher_path,contract_status, issuing_bank,card_no,create_time, update_time) "
            + "values (#{or.orderNo},#{or.amount},#{or.orderDate},#{or.latestPayDate},#{or.financerUid},#{or.customerUid}, "
            + "#{or.productId},#{or.comRatio},#{or.commission}, #{or.proRatio},#{or.profit},"
            + "#{or.status}, #{or.voucherStatus},#{or.voucherPath},#{or.contractStatus},#{or.issueBank},#{or.cardNo}, sysdate(), sysdate())")
	int saveOrder(@Param("or") OrderModel or);

	@Update("update tbl_order set voucher_path = #{or.voucherPath}, voucher_status=#{or.voucherStatus} where order_no=#{or.orderNo}")
	int updateVoucher(@Param("or") OrderModel or);

	@Update("update tbl_order set status = 99, update_time= NOW() where order_no=#{orderNo}")
	int cancelOrder(@Param("orderNo") String orderNo);

    @Select("<script>"
    		+ "select count(*) num from tbl_order "
    		+ "<where>"
    		+ "<if test='status !=null '>"
    		+ "  and status = #{status} "
    		+ "</if> "
    		+ " and financer_uid=#{financerID} "
    		+ "</where></script>")
//	@Select("select count(*) num from tbl_order where status= #{status} limit 1")
    int queryOrderCountByStatus(@Param("status") String status, @Param("financerID") String financerID);


	@Select("select count(*) num from tbl_order where customer_uid= #{customerId} limit 1")
    int queryOrderCountByCustomerId(@Param("customerId") String customerId);

	//理财师只有待结佣和已结佣的交易才能有佣金
	@Select("select count(*) num from tbl_order where financer_uid= #{financerId}  and status in ('02','03') limit 1")
    int queryOrderCountByFinancerId(@Param("financerId") String financerId);

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

    @Select("<script>"
    		+ "select * from tbl_order "
    		+ " where financer_uid =#{financerID} "
			+ "<if test='status !=null '>"
			+ " and status = #{status} "
			+ "</if> "
			+ " limit #{startIndex}, #{pageSize}"
			+ "</script>")
//    @Select("select * from tbl_order")
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
    List<OrderModel> queryOrders(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("status") String status, @Param("financerID") String financerID);

    @Select("<script>"
    		+ "select * from tbl_order where customer_uid= #{customerId} "
    		+ "<if test='status !=null '>"
			+ " 	and status = #{status} "
			+ "</if> "
    		+ "</script>")
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
    List<OrderModel> queryOrdersByCustomerId(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("customerId") String customerId, @Param("status") String status);

    @Select("select * from tbl_order where financer_uid= #{financerId}  and status in ('02','03')")
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
    List<OrderModel> queryOrdersByFinancerId(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("financerId") String financerId);

    //计算
	@Select("<script>"
			+ "select sum(commission) commisson from tbl_order where financer_uid= #{financerId} and status in "
			+ "<foreach item='item' index='index' collection='statusList' open='(' separator=',' close=')'>" + "#{item}"
			+ "</foreach>" + "</script>")
    String queryCommissinByFinancerId(@Param("financerId") String financerId, @Param("statusList") List<String> statusList);

}
