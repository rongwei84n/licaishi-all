package com.auts.lcscli.model.response;

import java.util.Map;

public class AllInfoCountResponse {
	private double orderCommissionCount; //累计佣金，单位元
	private Map<String, Integer> statusMap;
	
	public double getOrderCommissionCount() {
		return orderCommissionCount;
	}
	public void setOrderCommissionCount(double orderCommissionCount) {
		this.orderCommissionCount = orderCommissionCount;
	}
	public Map<String, Integer> getStatusMap() {
		return statusMap;
	}
	public void setStatusMap(Map<String, Integer> statusMap) {
		this.statusMap = statusMap;
	}
	

}
