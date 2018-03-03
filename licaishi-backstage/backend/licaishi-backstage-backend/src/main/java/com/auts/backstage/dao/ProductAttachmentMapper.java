package com.auts.backstage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.auts.backstage.model.dao.product.ProductAttachmentModel;

public interface ProductAttachmentMapper {

    @Select("select * from ProductAttachment where pa_product_code=#{pCode}")
    @Results({
    	@Result(property = "id", column = "pa_id"), 
    	@Result(property = "paProductCode", column = "pa_product_code"), 
    	@Result(property = "paFileName", column = "pa_file_name"),
    	@Result(property = "paFilePath", column = "pa_file_path"),
    	@Result(property = "paFileType", column = "pa_file_type"), 
    	@Result(property = "paFileUploadTime", column = "pa_file_upload_time")
    })
    List<ProductAttachmentModel> queryProductAttachmentByPCode(@Param("pCode") String pCode);
    
    @Insert("insert into ProductAttachment (pa_id, pa_product_code, pa_file_name,"
    		+ "pa_file_path, pa_file_type, pa_file_upload_time) "
            + "values (#{pa.id}, #{pa.paProductCode},#{pa.paFileName}, "
            + "#{pa.paFilePath}, #{pa.paFileType},#{pa.paFileUploadTime})")
    int savaProductAttachment(ProductAttachmentModel pa); 
}
