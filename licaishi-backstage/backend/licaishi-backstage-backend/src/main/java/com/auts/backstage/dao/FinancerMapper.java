package com.auts.backstage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.auts.backstage.model.dao.FinancerModel;

public interface FinancerMapper {
	
	@Select("select * from tbl_financer order by uid")
    List<FinancerModel> queryFinancerList();
}