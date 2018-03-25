package com.auts.lcs.model.response;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Data<T> {

	@JSONField(name = "list")
    @JsonProperty("list")
	private List<T> list; //数据集
	
	@JSONField(name = "pager")
    @JsonProperty("pager")
	private Pager pager; //分页
	
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
	
}
