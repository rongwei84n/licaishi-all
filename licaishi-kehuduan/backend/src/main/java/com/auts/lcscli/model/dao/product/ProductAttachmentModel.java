package com.auts.lcscli.model.dao.product;

import java.util.Date;

import com.auts.lcscli.model.common.BaseDaoModel;

public class ProductAttachmentModel extends BaseDaoModel {

	private String paProductCode;
    private String paFileName; //1.简版推介-大通阳明221号.pdf
    private String paFilePath; ///upload/productFile/20180126/productFile_1516936754979.pdf
    private String paFileType; //application/pdf
    private Date paFileUploadTime; //2018-01-26 11:19:38
    
	public String getPaProductCode() {
		return paProductCode;
	}
	public void setPaProductCode(String paProductCode) {
		this.paProductCode = paProductCode;
	}
	public String getPaFileName() {
		return paFileName;
	}
	public void setPaFileName(String paFileName) {
		this.paFileName = paFileName;
	}
	public String getPaFilePath() {
		return paFilePath;
	}
	public void setPaFilePath(String paFilePath) {
		this.paFilePath = paFilePath;
	}
	public String getPaFileType() {
		return paFileType;
	}
	public void setPaFileType(String paFileType) {
		this.paFileType = paFileType;
	}
	public Date getPaFileUploadTime() {
		return paFileUploadTime;
	}
	public void setPaFileUploadTime(Date paFileUploadTime) {
		this.paFileUploadTime = paFileUploadTime;
	}
    
}