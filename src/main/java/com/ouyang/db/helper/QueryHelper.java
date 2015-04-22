package com.ouyang.db.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import com.ouyang.db.vo.Db;
import com.ouyang.db.vo.DbColumn;
import com.ouyang.db.vo.DbTable;

/**
 * 处理查询和包装信息类
 * @author Ouyang
 *
 */
public class QueryHelper {

	private static final String TABLE_SQL = "";
	private static final String COLUMN_SQL = "";
	public static Db getDbInfoFromDb(Connection conn, String instanceName) throws Exception {
		
		Db db = null;		//获取db tables
		//获取db columns 
		Map<String, DbTable> mapTable = db.getMapTable();
		PreparedStatement ps = conn.prepareStatement(COLUMN_SQL);
		ps.setFetchSize(100);
		
//		ps.setString(1, "");
		ResultSet rs = ps.executeQuery();
		String tempName = null;
		while(rs.next()){
			tempName = rs.getString("table_name");
			DbTable table = mapTable.get(tempName);
			List<DbColumn> columns = table.getColumns();
//			DbColumn column = tranferColumnResultSet(rs);
//			columns.add(column);
		}
//		wrap
		return null;
	}
}
