package com.ouyang.db.vo;

import java.io.Serializable;

/**
 * 数据库对应字段信息
 * @author Ouyang
 *
 */
public class DbColumn implements Serializable {

	private String name;		
	private String dataType;
	private String maxLength;
	private String isPK;
	private String isNull;
	private String comment;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	public String getIsPK() {
		return isPK;
	}
	public void setIsPK(String isPK) {
		this.isPK = isPK;
	}
	public String getIsNull() {
		return isNull;
	}
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
