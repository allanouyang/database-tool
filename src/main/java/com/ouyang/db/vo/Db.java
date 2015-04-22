package com.ouyang.db.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 数据库对应所有表和字段信息
 * @author Ouyang
 *
 */
public class Db implements Serializable {

	private List<DbTable> tables;
	private Map<String, DbTable> mapTable;
	public List<DbTable> getTables() {
		return tables;
	}
	public void setTables(List<DbTable> tables) {
		this.tables = tables;
	}
	public Map<String, DbTable> getMapTable() {
		return mapTable;
	}
	public void setMapTable(Map<String, DbTable> mapTable) {
		this.mapTable = mapTable;
	}
//	public static void main(String[] args) {
//		List<Table> goalTables = new ArrayList<Table>();
//		Map<String, Table> mapTable = new HashMap<String, Table>();
//		for(Table tb: goalTables){
//			Table tb2 = mapTable.get(tb.getName());
//			if(tb2 != null){
//				com
//			}
//		}
//	}
}
