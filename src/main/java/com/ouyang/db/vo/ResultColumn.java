package com.ouyang.db.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 对比结果信息对应字段
 * @author Ouyang
 *
 */
public class ResultColumn implements Serializable {
	private String name;	
	private String comment;	
//	private String dataType;
//	private String maxLength;
//	private String isPK;
//	private String isNull;
	private List<ResultProperty> resultPropertys;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<ResultProperty> getResultPropertys() {
		return resultPropertys;
	}
	public void setResultPropertys(List<ResultProperty> resultPropertys) {
		this.resultPropertys = resultPropertys;
	}
	
}
