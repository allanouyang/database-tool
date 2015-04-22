package com.ouyang.db.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库对应表信息，包括字段信息
 * @author Ouyang
 *
 */
public class DbTable implements Serializable {

	private String name;
	private String comment;
	private int columnCount;
	private List<DbColumn> columns = new ArrayList<DbColumn>();
	private Map<String, DbColumn> mapColumn = new HashMap<String, DbColumn>();
	
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
	public int getColumnCount() {
		this.columnCount = columns.size();
		return this.columnCount;
	}
	public List<DbColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<DbColumn> columns) {
		this.columns = columns;
	}
	public Map<String, DbColumn> getMapColumn() {
		return mapColumn;
	}
	public void setMapColumn(Map<String, DbColumn> mapColumn) {
		this.mapColumn = mapColumn;
	}
//	public void addColumn(DbColumn column){
//		this.columns.add(column);
//		this.mapColumn.put(column.getName(), column);
//	}
	
}
