package com.auts.lcscli.dao;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.auts.lcscli.model.dao.product.ProductModel;

/**
 * Product表动态SQL生成器
 * 
 * @author li.bing
 * @date 2018年3月2日
 */
public class ProductSqlProvider{
	private final static String TABLE_NAME = "Product";
	
	public String updateSql(Map<String, Object> parameter) {
		ProductModel pm = (ProductModel) parameter.get("product");
		return new SQL(){
			{
				UPDATE(TABLE_NAME);
				if(pm.getpCode() != null) {
					SET("pCode = #{pCode}");
				}
			    
			    WHERE("ID = #{id}");
			}
		}.toString();
	}
}
