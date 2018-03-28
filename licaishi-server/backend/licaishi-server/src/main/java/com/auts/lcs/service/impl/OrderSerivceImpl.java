package com.auts.lcs.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auts.lcs.dao.OrderMapper;
import com.auts.lcs.dao.ProductsMapper;
import com.auts.lcs.model.dao.order.OrderModel;
import com.auts.lcs.model.dao.product.ProductModel;
import com.auts.lcs.service.OrderService;

@Service
public class OrderSerivceImpl implements OrderService {
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	ProductsMapper productsMapper;

	@Override
	public List<OrderModel> queryOrders(int pageNo, int pageSize, String status, String uid) {
		int startIndex = (pageNo - 1) * pageSize;
		//00 01 02 03 99
		if("00".equals(status)) {
			status = null;
		}
		return orderMapper.queryOrders(startIndex, pageSize, status, uid);
	}

	@Override
	public OrderModel queryOrderByOrderNo(String orderNo) {
		return orderMapper.queryOrderByOrderNo(orderNo);
	}

	@Override
	@Transactional
	public int saveOrder(OrderModel om) {
		int resutl = udpateProductAmount(om);
		if(resutl > 0) {
			resutl = orderMapper.saveOrder(om);
		}
		return resutl;
	}
	
	/**
	 * 并发处理方案
	 * 
	 * @param om
	 * @return
	 */
	private int  udpateProductAmount(OrderModel om) {
    	String pid = om.getProductId();
    	ProductModel prodcut = productsMapper.queryProductByPid(pid);
    	BigDecimal newAllSubscriptionAmount = new BigDecimal(prodcut.getpAllSubscriptionAmount()).add(om.getAmount());
    	return productsMapper.updateProductAmount(newAllSubscriptionAmount.toString(), pid, prodcut.getpAllSubscriptionAmount());
    }

	@Override
    public int updateVoucher(OrderModel om) {
        return orderMapper.updateVoucher(om);
    }

	@Override
	public int cancelOrder(String orderNo) {
		return orderMapper.cancelOrder(orderNo);
	}

	@Override
	public int queryOrderCountByStatus(String status, String uid) {
		//00 01 02 03 99
		if("00".equals(status)) {
			status = null;
		}
		return orderMapper.queryOrderCountByStatus(status, uid);
	}

	@Override
	public List<OrderModel> queryOrdersByCustomerId(int pageNo, int pageSize, String customerId, String status) {
		int startIndex = (pageNo - 1) * pageSize;
		//00 01 02 03 99
		if("00".equals(status)) {
			status = null;
		}
		return orderMapper.queryOrdersByCustomerId(startIndex, pageSize, customerId, status);
	}

	@Override
	public int queryOrderCountByCustomerId(String customerId) {
		return orderMapper.queryOrderCountByCustomerId(customerId);
	}

	@Override
	public int queryOrderCountByFinancerId(String financerId) {
		return orderMapper.queryOrderCountByFinancerId(financerId);

	}

	@Override
	public List<OrderModel> queryOrdersByFinancerId(int pageNo, int pageSize, String financerId) {
		int startIndex = (pageNo - 1) * pageSize;
		return orderMapper.queryOrdersByFinancerId(startIndex, pageSize, financerId);
	}

	@Override
	public String queryCommissinByFinancerId(String financerId, List<String> statusList) {
		return orderMapper.queryCommissinByFinancerId(financerId, statusList);
	}


}
