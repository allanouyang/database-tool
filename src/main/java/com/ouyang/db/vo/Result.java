package com.ouyang.db.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 对比结果信息对象
 * @author Ouyang
 *
 */
public class Result implements Serializable {

	private List<ResultTable> moreResultTables;
	private List<ResultTable> lessResultTables;
	private List<ResultTable> modifyResultTables;
	
	public List<ResultTable> getMoreResultTables() {
		return moreResultTables;
	}
	public void setMoreResultTables(List<ResultTable> moreResultTables) {
		this.moreResultTables = moreResultTables;
	}
	public List<ResultTable> getLessResultTables() {
		return lessResultTables;
	}
	public void setLessResultTables(List<ResultTable> lessResultTables) {
		this.lessResultTables = lessResultTables;
	}
	public List<ResultTable> getModifyResultTables() {
		return modifyResultTables;
	}
	public void setModifyResultTables(List<ResultTable> modifyResultTables) {
		this.modifyResultTables = modifyResultTables;
	}
	
}
