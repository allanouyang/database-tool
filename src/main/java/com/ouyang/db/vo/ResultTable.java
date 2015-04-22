package com.ouyang.db.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 对比结果信息对应表对象
 * @author Ouyang
 *
 */
public class ResultTable implements Serializable {

	private String name;
	private String comment;
	private List<ResultColumn> moreResultColumns;
	private List<ResultColumn> lessResultColumns;
	private List<ResultColumn> modifyResultColumns;
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
	public List<ResultColumn> getMoreResultColumns() {
		return moreResultColumns;
	}
	public void setMoreResultColumns(List<ResultColumn> moreResultColumns) {
		this.moreResultColumns = moreResultColumns;
	}
	public List<ResultColumn> getLessResultColumns() {
		return lessResultColumns;
	}
	public void setLessResultColumns(List<ResultColumn> lessResultColumns) {
		this.lessResultColumns = lessResultColumns;
	}
	public List<ResultColumn> getModifyResultColumns() {
		return modifyResultColumns;
	}
	public void setModifyResultColumns(List<ResultColumn> modifyResultColumns) {
		this.modifyResultColumns = modifyResultColumns;
	}
	public List<ResultProperty> getResultPropertys() {
		return resultPropertys;
	}
	public void setResultPropertys(List<ResultProperty> resultPropertys) {
		this.resultPropertys = resultPropertys;
	}
	
}
